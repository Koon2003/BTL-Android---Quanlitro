package com.example.appquanlitro;

public class TienPhongConLai {
    String maidtienconlai,mahoso,id,giatien,hovaten,ngaysinh,cccdnguoinop,sdt,sotienconlaiphaidong,trangthai;

    public TienPhongConLai(String maidtienconlai, String mahoso, String id, String giatien, String hovaten, String ngaysinh, String cccdnguoinop, String sdt, String sotienconlaiphaidong, String trangthai) {
        this.maidtienconlai = maidtienconlai;
        this.mahoso = mahoso;
        this.id = id;
        this.giatien = giatien;
        this.hovaten = hovaten;
        this.ngaysinh = ngaysinh;
        this.cccdnguoinop = cccdnguoinop;
        this.sdt = sdt;
        this.sotienconlaiphaidong = sotienconlaiphaidong;
        this.trangthai = trangthai;
    }

    public String getMaidtienconlai() {
        return maidtienconlai;
    }

    public void setMaidtienconlai(String maidtienconlai) {
        this.maidtienconlai = maidtienconlai;
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

    public String getCccdnguoinop() {
        return cccdnguoinop;
    }

    public void setCccdnguoinop(String cccdnguoinop) {
        this.cccdnguoinop = cccdnguoinop;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getSotienconlaiphaidong() {
        return sotienconlaiphaidong;
    }

    public void setSotienconlaiphaidong(String sotienconlaiphaidong) {
        this.sotienconlaiphaidong = sotienconlaiphaidong;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
