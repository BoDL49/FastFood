package com.example.fastfood.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.fastfood.R;
import com.example.fastfood.SanPhamActivity;

public class DoAnFragment extends Fragment {



    public DoAnFragment() {
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
        //return inflater.inflate(R.layout.fragment_main_menu, container, false);
        View view = inflater.inflate(R.layout.fragment_do_an, container, false);



        ImageView ivpizza = null;
        ImageView ivburger = null;
        ImageView ivsalad = null;
        ImageView ivdessert = null;

        if (view != null) {
            ivpizza = view.findViewById(R.id.ivpizza);
            ivburger = view.findViewById(R.id.ivburger);
            ivsalad = view.findViewById(R.id.ivsalad);
            ivdessert = view.findViewById(R.id.ivdessert);
        }




        ivpizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSanPhamActivity("pizza");
            }
        });

        ivburger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSanPhamActivity("burger");
            }
        });

        ivsalad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSanPhamActivity("salad");
            }
        });

        ivdessert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSanPhamActivity("dessert");
            }
        });

        return view;
    }



    private void openSanPhamActivity(String categoryName) {
        Intent intent = new Intent(getActivity(), SanPhamActivity.class);
        intent.putExtra("categoryName", categoryName);
        startActivity(intent);
    }
}