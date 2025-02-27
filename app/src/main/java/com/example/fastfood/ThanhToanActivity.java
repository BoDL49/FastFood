package com.example.fastfood;

import static com.example.fastfood.fragment.GioHangFragment.mangGioHang;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfood.model.Order;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThanhToanActivity extends AppCompatActivity {

    EditText edtNameOrder, edtPhoneOrder, edtAddressOrder;
    TextView btnCreateOrder, txtPriceOrder, btnCancelOrder;

    String tongtien;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);
        addControls();

        addEvents();
    }

    private void addEvents() {
        btnCreateOrder.setOnClickListener(v -> {
            // Check if the cart is empty
            if (mangGioHang.isEmpty()) {
                Toast.makeText(ThanhToanActivity.this, "Giỏ hàng của bạn đang trống!", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = edtNameOrder.getText().toString().trim();
            String phone = edtPhoneOrder.getText().toString().trim();
            String address = edtAddressOrder.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(ThanhToanActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }
            else{

                xulydatHang();
            }


        });

        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void xulydatHang() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Order");

            String name = edtNameOrder.getText().toString().trim();
            String phone = edtPhoneOrder.getText().toString().trim();
            String address = edtAddressOrder.getText().toString().trim();
            Order order = new Order(name, phone, address, tongtien);

            // Push the new Order object to the Firebase database
            myRef.push().setValue(order);

            // Clear the cart after placing the order
            mangGioHang.clear();

            // Show a success message to the user
            Toast.makeText(ThanhToanActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
            finish();

    }

    private void addControls() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tongtien = extras.getString("tongtien");

        }

        edtNameOrder = findViewById(R.id.edt_name_order);
        edtPhoneOrder = findViewById(R.id.edt_phone_order);
        edtAddressOrder = findViewById(R.id.edt_address_order);
        btnCreateOrder = findViewById(R.id.tv_create_order);
        txtPriceOrder = findViewById(R.id.tv_price_order);
        btnCancelOrder = findViewById(R.id.tv_cancel_order);

        txtPriceOrder.setText(tongtien);


    }
}