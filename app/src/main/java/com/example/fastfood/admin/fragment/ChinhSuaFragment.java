package com.example.fastfood.admin.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfood.R;
import com.example.fastfood.admin.adapter.SanPhamAdminAdapter;
import com.example.fastfood.model.SanPham;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChinhSuaFragment extends Fragment {

    private RecyclerView rvSanPham;
    private EditText edtSearch;
    private Button btnSearch;
    private SanPhamAdminAdapter sanPhamAdminAdapter;
    private List<SanPham> mListSP;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChinhSuaFragment() {
        // Required empty public constructor
    }

    public static ChinhSuaFragment newInstance(String param1, String param2) {
        ChinhSuaFragment fragment = new ChinhSuaFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chinh_sua, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addControls(view);
        addEvents();
        SearchData("");
    }

    private void addControls(View view) {
        rvSanPham = view.findViewById(R.id.recyclerChinhSua);
        edtSearch = view.findViewById(R.id.edtTimKiem);
        btnSearch = view.findViewById(R.id.btnTimKiem);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvSanPham.setLayoutManager(linearLayoutManager);
        mListSP = new ArrayList<>();

        sanPhamAdminAdapter = new SanPhamAdminAdapter(mListSP, new SanPhamAdminAdapter.ICickItemListener() {
            @Override
            public void onClickUpdateItem(SanPham sanPham) {
                openDialogUpdateItem(sanPham);
            }

            @Override
            public void onClickDeleteItem(SanPham sanPham) {
                onClickDeleteData(sanPham);
            }

        });

        rvSanPham.setAdapter(sanPhamAdminAdapter);
    }

    //lấy dữ liệu từ realtime database về cho mListSP
    private void addEvents() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_category");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mListSP.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot productSnapshot : categorySnapshot.getChildren()) {
                        // Kiểm tra xem "id" có tồn tại trong productSnapshot không và khác null
                        Integer idValue = productSnapshot.child("id").getValue(Integer.class);
                        if (idValue != null) {
                            int id = idValue.intValue();
                            String hinh = productSnapshot.child("hinh").getValue(String.class);
                            String tensp = productSnapshot.child("tensp").getValue(String.class);
                            String mota = productSnapshot.child("mota").getValue(String.class);
                            double dongia = productSnapshot.child("dongia").getValue(Double.class);

                            // Tạo đối tượng SanPham từ dữ liệu lấy từ Firebase
                            SanPham sp = new SanPham(id, hinh, tensp, mota, dongia, categorySnapshot.getKey());
                            mListSP.add(sp);
                        }
                        else {
                            // Xử lý trường hợp không có giá trị "id" tồn tại hoặc có giá trị null
                            Log.e("ChinhSuaFragment", "Giá trị 'id' không tồn tại hoặc null trong productSnapshot");
                        }
                    }
                }

                // Cập nhật RecyclerView khi có dữ liệu mới
                sanPhamAdminAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi một cách chi tiết hơn
                Log.e("ChinhSuaFragment", "Lỗi khi lấy danh sách sản phẩm từ Firebase: " + error.getMessage(), error.toException());
                Toast.makeText(getContext(), "Không lấy được danh sách sản phẩm: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void openDialogUpdateItem(SanPham sanPham) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(dialog.getWindow().FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_item);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText edtTenSP = dialog.findViewById(R.id.edtTenUpdate);
        EditText edtMoTa = dialog.findViewById(R.id.edtMoTaUpdate);
        EditText edtGia = dialog.findViewById(R.id.edtGiaUpdate);
        EditText edtHinh = dialog.findViewById(R.id.edtHinhUpdate);
        Button btnUpdate = dialog.findViewById(R.id.btnUpdate);
        Button btnCancel = dialog.findViewById(R.id.btnHuyUpdate);

        edtTenSP.setText(sanPham.getTensp());
        edtMoTa.setText(sanPham.getMota());
        edtGia.setText(String.valueOf(sanPham.getDongia()));
        edtHinh.setText(sanPham.getHinh());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("list_category").child(sanPham.getLoaisp());

                String newName = edtTenSP.getText().toString().trim();
                String newDescription = edtMoTa.getText().toString().trim();
                double newPrice = Double.parseDouble(edtGia.getText().toString().trim());
                String newImage = edtHinh.getText().toString().trim();

                // Tạo một HashMap để chứa các giá trị cần cập nhật
                Map<String, Object> updates = new HashMap<>();
                updates.put("tensp", newName);
                updates.put("mota", newDescription);
                updates.put("dongia", newPrice);
                updates.put("hinh", newImage);

                // Cập nhật dữ liệu trên Firebase
                myRef.child(String.valueOf(sanPham.getId())).updateChildren(updates, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        if (error != null) {
                            Log.e("ChinhSuaFragment", "Lỗi khi cập nhật dữ liệu: " + error.getMessage());
                            Toast.makeText(getContext(), "Đã xảy ra lỗi, không thể cập nhật sản phẩm", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
        dialog.show();
    }
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        SanPham sp = dataSnapshot.getValue(SanPham.class);
        if(mListSP == null || mListSP.isEmpty()){
            return;
        }
        for (int i =0; i < mListSP.size(); i++) {
            if (mListSP.get(i).getId() == sp.getId()) {
                mListSP.set(i, sp);
                return;
            }
        }
        sanPhamAdminAdapter.notifyDataSetChanged();
    }

    private void onClickDeleteData(SanPham sanPham) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("list_category").child(sanPham.getLoaisp());
                        myRef.child(String.valueOf(sanPham.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                if (error != null) {
                                    Log.e("ChinhSuaFragment", "Lỗi khi xóa sản phẩm: " + error.getMessage());
                                    Toast.makeText(getContext(), "Đã xảy ra lỗi, không thể xóa sản phẩm", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                })
                .setNegativeButton("Không", null)
                .show();
    }

    private void SearchData(String keySearch) {
        keySearch = edtSearch.getText().toString().trim();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_category");
        String finalKeySearch = keySearch;
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListSP.clear();
                if (finalKeySearch.isEmpty()) {
                    addEvents();
                } else {
                    myRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@androidx.annotation.NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {
                            SanPham sp = snapshot.getValue(SanPham.class);
                            if (sp != null) {
                                if(sp.getTensp().toLowerCase().contains(finalKeySearch.toLowerCase()))
                                {
                                    mListSP.add(sp);
                                }
                                sanPhamAdminAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onChildChanged(@androidx.annotation.NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@androidx.annotation.NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@androidx.annotation.NonNull DataSnapshot snapshot, @androidx.annotation.Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}
