package com.example.fastfood.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.fastfood.R;
import com.example.fastfood.fragment.DoAnFragment;
import com.example.fastfood.model.HinhAnh;

import java.util.List;

public class HinhAnhAdapter extends PagerAdapter {
    private DoAnFragment mContext;
    private List<HinhAnh> mlistHinhAnh;

    public HinhAnhAdapter(DoAnFragment mContext, List<HinhAnh> mlistHinhAnh) {
        this.mContext = mContext;
        this.mlistHinhAnh = mlistHinhAnh;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container, false);
        ImageView imgHinhAnh = view.findViewById(R.id.img_anh);
        HinhAnh hinhanh = mlistHinhAnh.get(position);
        if(hinhanh != null)
            Glide.with(mContext).load(hinhanh.getResourceId()).into(imgHinhAnh);

        //Add view to viewgroup
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(mlistHinhAnh != null)
            return mlistHinhAnh.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
