package com.example.fastfood;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.fastfood.fragment.DoAnFragment;
import com.example.fastfood.fragment.DonHangFragment;
import com.example.fastfood.fragment.GioHangFragment;
import com.example.fastfood.fragment.TaiKhoanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Đồ ăn");
        loadFragment(new DoAnFragment());
    }


    void loadFragment(Fragment fmNew){
        FragmentTransaction fmTran = getSupportFragmentManager().beginTransaction();
        fmTran.replace(R.id.main_frame, fmNew);
        fmTran.addToBackStack(null);
        fmTran.commit();
    }

    private void addEvents() {
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fmNew = null;
                // Tạo Fragment mới dựa trên item được chọn
                if (item.getItemId() == R.id.BottomDoAn) {
                    fmNew = new DoAnFragment();
                    getSupportActionBar().setTitle(item.getTitle());
                } else if (item.getItemId() == R.id.BottomGioHang) {
                    fmNew = new GioHangFragment();
                    getSupportActionBar().setTitle(item.getTitle());
                } else if (item.getItemId() == R.id.BottomDonHang) {
                    fmNew = new DonHangFragment();
                    getSupportActionBar().setTitle(item.getTitle());}
                else if (item.getItemId() == R.id.BottomTaiKhoan) {
                    fmNew = new TaiKhoanFragment();
                    getSupportActionBar().setTitle(item.getTitle());
                }

                // Thay thế Fragment hiện tại bằng Fragment mới được chọn
                if (fmNew != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, fmNew).commit();
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    private void addControls() {
        bottomNav = findViewById(R.id.bottomNav);
    }
}