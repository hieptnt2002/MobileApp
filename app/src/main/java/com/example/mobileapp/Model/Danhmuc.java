package com.example.mobileapp.Model;

public class Danhmuc {
    int id;
    String ten,anh;

    public Danhmuc(int id, String ten, String anh) {
        this.id = id;
        this.ten = ten;
        this.anh = anh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
