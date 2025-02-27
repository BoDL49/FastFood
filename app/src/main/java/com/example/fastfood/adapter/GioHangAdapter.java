package com.example.fastfood.adapter;

import static com.example.fastfood.fragment.GioHangFragment.mangGioHang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fastfood.R;
import com.example.fastfood.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GioHang> arrGioHang;

    private TextView txtTongTien;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrGioHang, TextView txtTongTien) {
        this.context = context;
        this.arrGioHang = arrGioHang;
        this.txtTongTien = txtTongTien;
    }


    @Override
    public int getCount() {
        return arrGioHang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrGioHang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txtTenGioHang, txtGiaGioHang;
        ImageView ivHinhGioHang;
        Button btnDelete, btnAmount;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_giohang, null);

            viewHolder.txtTenGioHang = convertView.findViewById(R.id.tv_food_name_cart);
            viewHolder.txtGiaGioHang = convertView.findViewById(R.id.tv_food_price_cart);
            viewHolder.ivHinhGioHang = convertView.findViewById(R.id.img_food_cart);
            viewHolder.btnAmount = convertView.findViewById(R.id.btn_count);
            viewHolder.btnDelete = convertView.findViewById(R.id.btn_delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GioHang gioHang = arrGioHang.get(position);

        viewHolder.txtTenGioHang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaGioHang.setText(decimalFormat.format(gioHang.getGiasp()) + " Đ");

        if (gioHang.getHinhDrawable() != null) {
            Glide.with(context).load(gioHang.getHinh()).into(viewHolder.ivHinhGioHang);
            Picasso.get().load(gioHang.getHinh())
                    .placeholder(R.drawable.rise)
                    .error(R.drawable.img_default)
                    .into(viewHolder.ivHinhGioHang);
        } else {
            Picasso.get().load(gioHang.getHinh())
                    .placeholder(R.drawable.rise)
                    .error(R.drawable.img_default)
                    .into(viewHolder.ivHinhGioHang);
            Glide.with(context).load(gioHang.getHinh()).into(viewHolder.ivHinhGioHang);
        }

        viewHolder.btnAmount.setText(String.valueOf(gioHang.getSoluongsp()));

        // Xử lý sự kiện khi nhấn vào nút xóa
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa mục tại vị trí được nhấn
                deleteItem(position);
            }
        });

        return convertView;
    }

    // Phương thức để xóa một mục khỏi danh sách giỏ hàng
    public void deleteItem(int position) {
        arrGioHang.remove(position);
        notifyDataSetChanged();
        updateTotalPrice();
    }

    public long tinhTongGia() {
        long tongTien = 0;
        for (GioHang gioHang : arrGioHang) {
            tongTien += gioHang.getGiasp();
        }
        return tongTien;
    }

    // Phương thức để cập nhật tổng giá tiền trên giao diện
    public void updateTotalPrice() {
        if (txtTongTien != null && mangGioHang != null) {
            long tongtien = tinhTongGia();
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txtTongTien.setText(decimalFormat.format(tongtien) + " Đ");
        }
    }

}
