package com.example.fastfood.admin.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fastfood.DangNhapActivity;
import com.example.fastfood.DoiMatKhauActivity;
import com.example.fastfood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class TaiKhoanAdminFragment extends Fragment {

    LinearLayout layoutSignout, layoutDoiMatKhau;


    TextView txtEmail;

    public TaiKhoanAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tai_khoan_admin, container, false);

        layoutSignout = view.findViewById(R.id.layout_sign_out_admin);
        layoutDoiMatKhau = view.findViewById(R.id.layout_doimatkhau_admin);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        txtEmail = view.findViewById(R.id.txtEmailAdmin);
        String email = user.getEmail();
        txtEmail.setText(email);

        layoutSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xulyDangXuat();
            }
        });

        layoutDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoiMatKhauActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void xulyDangXuat() {
        new AlertDialog.Builder(getContext()).setTitle("Xác nhận đăng xuất").setMessage("Bạn có chắc chắn muốn đăng xuất không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(), DangNhapActivity.class);
                    startActivity(intent);
                }).setNegativeButton("Không", (dialog, which) -> {
        }).show();

    }
}