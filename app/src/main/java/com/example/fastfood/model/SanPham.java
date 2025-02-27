package com.example.fastfood.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private String hinh;// Đường dẫn URL của hình ảnh


    private String tensp;
    private String mota;
    private double dongia;

    private  int id;

    private String loaisp;

    public SanPham() {
    }

    public SanPham(int id,String hinh, String tensp, String mota, double dongia, String loaisp) {
        this.id = id;
        this.hinh = hinh;
        this.tensp = tensp;
        this.mota = mota;
        this.dongia = dongia;
        this.loaisp = loaisp;
    }

    public String getLoaisp() {
        return loaisp;
    }

    public void setLoaisp(String loaisp) {
        this.loaisp = loaisp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHinh() {
        return hinh;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public double getDongia() {
        return dongia;
    }

    public void setDongia(double dongia) {
        this.dongia = dongia;
    }

//    public Map<String, Object> toMap(){
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("tensp", tensp);
//        return result;
//    }
}
