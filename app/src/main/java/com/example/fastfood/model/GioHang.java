package com.example.fastfood.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class GioHang  implements Serializable {
    private int idsp;
    private String tensp;
    private int giasp;
    private String hinh;
    private int soluongsp;
    private Drawable hinhDrawable;

    public GioHang() {
    }

    public GioHang(int idsp, String tensp, int giasp, String hinh, int soluongsp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.giasp = giasp;
        this.hinh = hinh;
        this.soluongsp = soluongsp;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public int getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }

    public Drawable getHinhDrawable() {
        return hinhDrawable;
    }

    public void setHinhDrawable(Drawable hinhDrawable) {
        this.hinhDrawable = hinhDrawable;
    }
}
