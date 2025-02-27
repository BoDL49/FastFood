package com.example.fastfood.admin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fastfood.R;
import com.example.fastfood.admin.fragment.ChinhSuaFragment;
import com.example.fastfood.admin.fragment.TaiKhoanAdminFragment;
import com.example.fastfood.admin.fragment.ThemFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainAdminActivity extends AppCompatActivity {
    BottomNavigationView bottomNav_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_admin);
        addControls();
        addEvents();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm sản phẩm");
        loadFragment(new ThemFragment());
    }

    private void loadFragment(Fragment fmNew) {
        FragmentTransaction fmTran = getSupportFragmentManager().beginTransaction();
        fmTran.replace(R.id.main_frame_admin, fmNew);
        fmTran.addToBackStack(null);
        fmTran.commit();
    }

    private void addEvents() {
        bottomNav_admin.setOnNavigationItemSelectedListener(item -> {
            Fragment fmNew;
            // Tạo Fragment mới dựa trên item được chọn
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.BottomThem) {
                selectedFragment = new ThemFragment();
                getSupportActionBar().setTitle(item.getTitle());
            }
            else if (item.getItemId() == R.id.BottomSua) {
                selectedFragment = new ChinhSuaFragment();
                getSupportActionBar().setTitle(item.getTitle());
            }
            else if (item.getItemId() == R.id.BottomTaiKhoanAdmin) {
                selectedFragment = new TaiKhoanAdminFragment();
                getSupportActionBar().setTitle(item.getTitle());
            }

            // Thay thế Fragment hiện tại bằng Fragment mới được chọn
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_admin, selectedFragment).commit();
            }
            return true;
        });
    }


    private void addControls() {
        bottomNav_admin = findViewById(R.id.bottomNav_admin);
    }
}