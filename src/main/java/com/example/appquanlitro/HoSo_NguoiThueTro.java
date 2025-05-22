package com.example.appquanlitro;

public class HoSo_NguoiThueTro {
    String maid,mahoso,hovaten,ngaysinh,cccd,sdt,quequan,id,giatien,hinhthucthue,ngaybatdau,ngayketthuc,xacnhanhopdong,trangthai;

    public HoSo_NguoiThueTro(String id, String mahoso, String hovaten,String ngaysinh, String cccd, String queuquan, String sdt ,String idphongtro, String giatien, String hinhthucthue, String ngaybatdau, String ngayketthuc, String xacnhanhopdong,String trangthai) {
        this.maid = id;
        this.mahoso = mahoso;
        this.hovaten = hovaten;
        this.cccd = cccd;
        this.quequan = queuquan;
        this.sdt = sdt;
this.ngaysinh=ngaysinh;
        this.id = idphongtro;
        this.giatien = giatien;
        this.hinhthucthue = hinhthucthue;
        this.ngaybatdau = ngaybatdau;
        this.ngayketthuc = ngayketthuc;
        this.xacnhanhopdong = xacnhanhopdong;
        this.trangthai=trangthai;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMahoso() {
        return mahoso;
    }

    public void setMahoso(String mahoso) {
        this.mahoso = mahoso;
    }

    public String getHovaten() {
        return hovaten;
    }

    public void setHovaten(String hovaten) {
        this.hovaten = hovaten;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String queuquan) {
        this.quequan = queuquan;
    }

    public String getMaid() {
        return maid;
    }

    public void setMaid(String maid) {
        this.maid = maid;
    }

    public String getGiatien() {
        return giatien;
    }

    public void setGiatien(String giatien) {
        this.giatien = giatien;
    }

    public String getHinhthucthue() {
        return hinhthucthue;
    }

    public void setHinhthucthue(String hinhthucthue) {
        this.hinhthucthue = hinhthucthue;
    }

    public String getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(String ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public String getNgayketthuc() {
        return ngayketthuc;
    }

    public void setNgayketthuc(String ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }

    public String getXacnhanhopdong() {
        return xacnhanhopdong;
    }

    public void setXacnhanhopdong(String xacnhanhopdong) {
        this.xacnhanhopdong = xacnhanhopdong;
    }
}
