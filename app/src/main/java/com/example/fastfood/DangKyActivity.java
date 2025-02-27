package com.example.fastfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import static com.example.fastfood.DangNhapActivity.database;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKyActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button btnDangNhap, btnDangKy;
    EditText edtEmail, edtPassword;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        addControls();
        addEvents();
    }

    private void addEvents() {

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(DangKyActivity.this, "Nhập email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(DangKyActivity.this, "Nhập password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(DangKyActivity.this, "Mật khẩu phải lớn hơn 6 ký tự", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(DangKyActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(DangKyActivity.this, "Đăng ký thất bại." + task.getException(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });


            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                startActivity(intent);
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }







    private void addControls() {
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        btnBack = findViewById(R.id.btnBack);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
    }


}