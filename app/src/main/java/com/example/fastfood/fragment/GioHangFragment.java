package com.example.fastfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.fastfood.R;
import com.example.fastfood.ThanhToanActivity;
import com.example.fastfood.adapter.GioHangAdapter;
import com.example.fastfood.model.GioHang;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangFragment extends Fragment {
    ListView lvGioHang;
    TextView txtTongTien, txtEmptyCart;
    GioHangAdapter gioHangAdapter;
    Button btnOrderCart;

    public static ArrayList<GioHang> mangGioHang;

    public GioHangFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);

        lvGioHang = view.findViewById(R.id.lv_food_cart);
        txtTongTien = view.findViewById(R.id.tv_total_price);
        btnOrderCart = view.findViewById(R.id.btn_order_cart);
        xulymuahang();
        txtEmptyCart = view.findViewById(R.id.tv_empty_cart);
        if (mangGioHang == null) {
            mangGioHang = new ArrayList<>();
        }
        gioHangAdapter = new GioHangAdapter(getActivity(), mangGioHang, txtTongTien);
        lvGioHang.setAdapter(gioHangAdapter);

        return view;
    }

    private void xulymuahang() {

    btnOrderCart.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // Check if the cart is empty
        if (mangGioHang.isEmpty()) {
            Toast.makeText(getActivity(), "Giỏ hàng bạn đang trống, hãy thêm sản phẩm", Toast.LENGTH_SHORT).show();
        } else {
            // Proceed to the checkout process
            checkout();
        }
    }
});
    }

    private void checkout() {
        Intent intent = new Intent(getActivity(), ThanhToanActivity.class);
        intent.putExtra("tongtien", txtTongTien.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTotalPrice();
        if (mangGioHang.isEmpty()) {
            txtEmptyCart.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.GONE);
        } else {
            txtEmptyCart.setVisibility(View.GONE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    public void updateTotalPrice() {
        if (txtTongTien != null && mangGioHang != null) {
            long tongtien = 0;
            for (GioHang gioHang : mangGioHang) {
                tongtien += gioHang.getGiasp();
            }
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txtTongTien.setText(decimalFormat.format(tongtien) + " Đ");
            gioHangAdapter.notifyDataSetChanged();
        }
    }
}
