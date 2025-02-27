package com.example.fastfood;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fastfood.admin.MainAdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangNhapActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText edtEmail, edtPassword;
    Button btnDangNhap, btnDangKy, btnQuenMatKhau;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if(user.getEmail().endsWith("@admin.com")){
                Intent intent = new Intent(DangNhapActivity.this, MainAdminActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                startActivity(intent);
            }
        } else {
            addControls();
            addEvents();
        }

    }





    private void addEvents() {
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(intent);
            }
        });
        btnQuenMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhapActivity.this, QuenMatKhauActivity.class);
                startActivity(intent);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
//                startActivity(intent);
                login();
            }
        });


    }

    private void login() {
        String email, password;
        email = edtEmail.getText().toString();
        password = edtPassword.getText().toString();

        //Kiem tra email, password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(DangNhapActivity.this, "Vui lòng nhập địa chỉ email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(DangNhapActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                               @Override
                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                   if (task.isSuccessful()) {
                                                       Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                                       if(email.endsWith("@admin.com")){
                                                           Intent intent = new Intent(DangNhapActivity.this, MainAdminActivity.class);
                                                           startActivity(intent);
                                                       }
                                                       else{
                                                           Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                                                           startActivity(intent);
                                                       }
                                                   } else {
                                                       Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                                   }
                                               }
                                           }
                    );
        }


    }

    private void addControls() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnQuenMatKhau = findViewById(R.id.btnQuenMatKhau);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

    }
}