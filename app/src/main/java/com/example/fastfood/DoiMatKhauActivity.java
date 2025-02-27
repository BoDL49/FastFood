package com.example.fastfood;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoiMatKhauActivity extends AppCompatActivity {

    EditText edtMatKhau, edtXacNhanMatKhau;
    Button btnDoiMatKhau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doi_mat_khau);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDoiMatKhau.setOnClickListener(v -> {
            String matKhau = edtMatKhau.getText().toString();
            String xacNhanMatKhau = edtXacNhanMatKhau.getText().toString();
            if (matKhau.isEmpty() || xacNhanMatKhau.isEmpty()) {
                edtMatKhau.setError("Vui lòng nhập mật khẩu");
                edtXacNhanMatKhau.setError("Vui lòng xác nhận lại mật khẩu");
                return;
            }
            if (!matKhau.equals(xacNhanMatKhau)) {
                edtXacNhanMatKhau.setError("Mật khẩu không khớp");
                return;
            }
            else{
                String newPassword = edtMatKhau.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(DoiMatKhauActivity.this, "Đã đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }
        });
    }

        private void addControls() {
            edtMatKhau = findViewById(R.id.edtMatKhau);
            edtXacNhanMatKhau = findViewById(R.id.edtXacNhanLaiMatKhau);
            btnDoiMatKhau = findViewById(R.id.btnDoiMatKhau);
        }

}