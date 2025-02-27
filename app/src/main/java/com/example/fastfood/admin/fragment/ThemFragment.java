package com.example.fastfood.admin.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fastfood.R;
import com.example.fastfood.model.SanPham;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThemFragment extends Fragment {
    private EditText edtTenMon, edtGia, edtMoTa, edtHinhAnh, edtLoaiSP, edtIdSP;
    private Button btnThem, btnHuy;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThemFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ThemFragment newInstance(String param1, String param2) {
        ThemFragment fragment = new ThemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        addControls(view);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(edtIdSP.getText().toString());
                String ten = edtTenMon.getText().toString();
                String mota = edtMoTa.getText().toString();
                double gia = Double.parseDouble(edtGia.getText().toString());
                String hinh = edtHinhAnh.getText().toString();
                String loai = edtLoaiSP.getText().toString();
                SanPham sp = new SanPham(id,hinh, ten, mota, gia, loai);
                themSanPham(sp);
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
        return view;
    }

    private void clearFields() {
        edtIdSP.setText("");
        edtTenMon.setText("");
        edtGia.setText("");
        edtMoTa.setText("");
        edtHinhAnh.setText("");
        edtLoaiSP.setText("");
    }

    private void themSanPham(SanPham spNew) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_category").child(spNew.getLoaisp());

        String pathOBJ = String.valueOf(spNew.getId());
        myRef.child(pathOBJ).setValue(spNew, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
    void addControls(View view) {
        edtIdSP = view.findViewById(R.id.edtIDSP);
        edtTenMon = view.findViewById(R.id.edtTenSP);
        edtGia = view.findViewById(R.id.edtGiaSP);
        edtMoTa = view.findViewById(R.id.edtMoTaSP);
        edtHinhAnh = view.findViewById(R.id.edtHinhSP);
        edtLoaiSP = view.findViewById(R.id.edtLoaiSP);
        btnThem = view.findViewById(R.id.btnThem);
        btnHuy = view.findViewById(R.id.btnHuy);
    }
}