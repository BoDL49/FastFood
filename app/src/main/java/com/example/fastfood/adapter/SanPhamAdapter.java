package com.example.fastfood.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfood.ChiTietSanPhamActivity;
import com.example.fastfood.R;
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

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private Activity context;
    private ArrayList<SanPham> arr_SanPham;
    private DatabaseReference mDatabase, databaseReference;


    public SanPhamAdapter(Activity context, ArrayList<SanPham> arr_SanPham) {
        this.context = context;
        this.arr_SanPham = arr_SanPham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View viewSanPham = layoutInflater.inflate(R.layout.layout_sanpham, parent, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return new ViewHolder(viewSanPham);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sp = arr_SanPham.get(position);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("list_category").child("categoryName").child("hinh");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String imageUrl = snapshot.getValue(String.class);

                Picasso.get().load(sp.getHinh())
                        .error(R.drawable.img_default) // hình ảnh sẽ được hiển thị nếu có lỗi xảy ra
                        .into(holder.ivHinh);;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.txtTenSp.setText(sp.getTensp());
        holder.txtMoTa.setText(sp.getMota());
        String priceInVnd = format.format(sp.getDongia());
        holder.txtDonGia.setText(String.valueOf(priceInVnd));

        

        // Thêm sự kiện click vào itemView
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo intent để chuyển sang ChiTietSanPhamActivity và truyền productId
                Intent intent = new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("id", sp.getId());
                intent.putExtra("loaisp", sp.getLoaisp());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arr_SanPham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivHinh;
        TextView txtTenSp, txtMoTa, txtDonGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinh = itemView.findViewById(R.id.ivHinh);
            txtTenSp = itemView.findViewById(R.id.txtTenSP);
            txtMoTa = itemView.findViewById(R.id.txtMoTa);
            txtDonGia = itemView.findViewById(R.id.txtDonGia);
        }
    }

}
