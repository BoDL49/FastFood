package com.example.fastfood;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfood.adapter.SanPhamAdapter;
import com.example.fastfood.model.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SanPhamActivity extends AppCompatActivity {
    RecyclerView recyclerViewSP;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<SanPham> arr_SanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        addControls();


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("categoryName")) {
            String categoryName = intent.getStringExtra("categoryName");
            // Gọi hàm để lấy danh sách sản phẩm từ Firebase
            getListFromDatabase(categoryName);
        }

    }

    private void addControls() {
        recyclerViewSP = findViewById(R.id.recyclerSanPham);
        arr_SanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(this, arr_SanPham);
        recyclerViewSP.setAdapter(sanPhamAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewSP.setLayoutManager(linearLayoutManager);
    }

    private void getListFromDatabase(String categoryName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference categoryRef = database.getReference().child("list_category").child(categoryName);



        categoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arr_SanPham.clear(); // Clear previous data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SanPham sanPham = dataSnapshot.getValue(SanPham.class);
                    arr_SanPham.add(sanPham);
                }
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SanPhamActivity.this, "Failed to get products for category: " + categoryName, Toast.LENGTH_SHORT).show();
            }
        });
    }
}