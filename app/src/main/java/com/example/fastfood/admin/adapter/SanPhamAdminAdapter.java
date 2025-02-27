package com.example.fastfood.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfood.R;
import com.example.fastfood.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SanPhamAdminAdapter extends RecyclerView.Adapter<SanPhamAdminAdapter.SanPhamViewHolder> {
    private List<SanPham> mListSP;
    private ICickItemListener mListener;
    public interface ICickItemListener {
        void onClickUpdateItem(SanPham sanPham);
        void onClickDeleteItem(SanPham sanPham);
    }

    public SanPhamAdminAdapter(List<SanPham> mListSP, ICickItemListener mListener) {
        this.mListSP = mListSP;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public SanPhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem_admin, parent, false);
        return new SanPhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewHolder holder, int position) {
        SanPham sp = mListSP.get(position);

        // Load hình ảnh từ URL sử dụng Picasso
        Picasso.get().load(sp.getHinh())
                .error(R.drawable.img_default) // Hình ảnh sẽ được hiển thị nếu có lỗi xảy ra
                .into(holder.imgHinhSP);

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.txtTenSP.setText(sp.getTensp());
        holder.txtMoTaSP.setText(sp.getMota());
        String priceInVnd = format.format(sp.getDongia());
        holder.txtGiaSP.setText(priceInVnd);

        // Bắt sự kiện khi người dùng nhấn vào nút chỉnh sửa
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickUpdateItem(sp);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickDeleteItem(sp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListSP.size();
    }

    public class SanPhamViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgHinhSP;
        Button btnEdit, btnDelete;
        TextView txtTenSP, txtMoTaSP, txtGiaSP;

        public SanPhamViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhSP = itemView.findViewById(R.id.imgHinhSP);
            txtTenSP = itemView.findViewById(R.id.txtTenSP);
            txtMoTaSP = itemView.findViewById(R.id.txtMoTaSP);
            txtGiaSP = itemView.findViewById(R.id.txtGiaSP);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
