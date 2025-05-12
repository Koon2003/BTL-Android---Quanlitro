package com.example.appquanlitro;

public class TaiKhoan {
    String tendn,matkhau,hovaten,ngaysinh;
    String cccd;
    String quequan;
    String sdt;
    String quyen;

    public TaiKhoan(String tendn, String matkhau, String hovaten,String ngaysinh, String cccd, String quequan, String sdt, String quyen) {
        this.tendn = tendn;
        this.matkhau = matkhau;
        this.hovaten = hovaten;
        this.ngaysinh = ngaysinh;
        this.cccd = cccd;
        this.quequan = quequan;
        this.sdt = sdt;
        this.quyen = quyen;
    }

    public String getTendn() {
        return tendn;
    }

    public void setTendn(String tendn) {
        this.tendn = tendn;
    }

    public String getNgaysinh() {
        return ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        this.ngaysinh = ngaysinh;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
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

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getQuyen() {
        return quyen;
    }

    public void setQuyen(String quyen) {
        this.quyen = quyen;
    }
}
