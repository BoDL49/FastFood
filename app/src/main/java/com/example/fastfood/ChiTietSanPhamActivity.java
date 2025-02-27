package com.example.fastfood;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfood.fragment.GioHangFragment;
import com.example.fastfood.model.GioHang;
import com.example.fastfood.model.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    TextView txtTitle, txtPrice, txtDes;
    EditText editAmount;
    ImageView ivHinh;
    Button btnAddToCart;

    int productId;
    String loaisp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        addControls();
        getProductDetails(productId, loaisp);
        eventButton();
    }

    private void eventButton() {

    }

    private void getProductDetails(int productId, String loaisp) {
        String productIdString = String.valueOf(productId);
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("list_category").child(loaisp).child(productIdString);

        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                    displayProductDetails(sanPham);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình truy vấn Firebase
            }
        });
    }

    private void displayProductDetails(SanPham sanPham) {
        txtTitle.setText(sanPham.getTensp());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String priceInVnd = format.format(sanPham.getDongia());
        txtPrice.setText(priceInVnd);
        Picasso.get().load(sanPham.getHinh()).into(ivHinh); // Ensure the URL is valid
        txtDes.setText(sanPham.getMota());
        editAmount.setText("1"); // Mặc định số lượng là 1 khi mới mở màn hình chi tiết sản phẩm


        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GioHangFragment.mangGioHang == null) {
                    GioHangFragment.mangGioHang = new ArrayList<>();
                }

                String priceString = txtPrice.getText().toString().replace(".", "").replace("₫", "").replaceAll("\\s+","");
                int price = Integer.parseInt(priceString);

                int soluong = Integer.parseInt(editAmount.getText().toString());
                int giamoi = soluong * price;
//                GioHangFragment.mangGioHang.add(new GioHang(productId, txtTitle.getText().toString(), giamoi, ivHinh.getDrawable().toString(), soluong));

                boolean exists = false;
                for (GioHang item : GioHangFragment.mangGioHang) {
                    if (item.getIdsp() == productId) {
                        item.setSoluongsp(item.getSoluongsp() + soluong);
                        item.setGiasp(item.getSoluongsp() * price);
                        exists = true;
                        break;
                    }
                }

                if (!exists) {
                    GioHangFragment.mangGioHang.add(new GioHang(productId, txtTitle.getText().toString(), giamoi, sanPham.getHinh(), soluong));
                    finish();
                }
                finish();
            }
        });
    }

    private void addControls() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            productId = extras.getInt("id");
            loaisp = extras.getString("loaisp");
        }

        txtTitle = findViewById(R.id.txtTitle);
        txtPrice = findViewById(R.id.txtPrice);
        ivHinh = findViewById(R.id.ivHinh);
        editAmount = findViewById(R.id.edtAmount);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        txtDes = findViewById(R.id.txtDes);
    }
}
