package com.example.appquanlitro;

public class TienCocPhong {
    String maidcoc,mahoso,id,giatien,hovaten,ngaysinh,cccdnguoinop,sdt,hinhthuccoc,sotiendanopcoc,sotienconlai;

    public TienCocPhong(String maidcoc, String mahoso, String id, String giatien, String hovaten, String ngaysinh, String cccdnguoinop, String sdt, String hinhthuccoc, String sotiendanopcoc, String sotienconlai) {
        this.maidcoc = maidcoc;
        this.mahoso = mahoso;
        this.id = id;
        this.giatien = giatien;
        this.hovaten = hovaten;
        this.ngaysinh = ngaysinh;
        this.cccdnguoinop = cccdnguoinop;
        this.sdt = sdt;
        this.hinhthuccoc = hinhthuccoc;
        this.sotiendanopcoc = sotiendanopcoc;
        this.sotienconlai = sotienconlai;
    }

    public String getMaidcoc() {
        return maidcoc;
    }

    public String getGiatien() {
        return giatien;
    }

    public void setGiatien(String giatien) {
        this.giatien = giatien;
    }

    public String getHovaten() {
        return hovaten;
    }

    public void setHovaten(String hovaten) {
        this.hovaten = hovaten;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setMaidcoc(String maidcoc) {
        this.maidcoc = maidcoc;
    }

    public String getMahoso() {
        return mahoso;
    }

    public void setMahoso(String mahoso) {
        this.mahoso = mahoso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHinhthuccoc() {
        return hinhthuccoc;
    }

    public void setHinhthuccoc(String hinhthuccoc) {
        this.hinhthuccoc = hinhthuccoc;
    }

    public String getCccdnguoinop() {
        return cccdnguoinop;
    }

    public void setCccdnguoinop(String cccdnguoinop) {
        this.cccdnguoinop = cccdnguoinop;
    }

    public String getSotiendanopcoc() {
        return sotiendanopcoc;
    }

    public void setSotiendanopcoc(String sotiendanopcoc) {
        this.sotiendanopcoc = sotiendanopcoc;
    }

    public String getSotienconlai() {
        return sotienconlai;
    }

    public void setSotienconlai(String sotienconlai) {
        this.sotienconlai = sotienconlai;
    }
}
