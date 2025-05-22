package com.example.appquanlitro;

public class PhongTro {
    private String id;
    private String tenPhong;
    private String dienTich;
    private String moTa;
    private String giaTien;
    private String img1Path; // Đường dẫn đến hình ảnh
    private String img2Path; // Đường dẫn đến hình ảnh
    private String img3Path; // Đường dẫn đến hình ảnh
    private String img4Path; // Đường dẫn đến hình ảnh
    private String img5Path; // Đường dẫn đến hình ảnh

    public PhongTro(String id, String tenPhong, String dienTich, String moTa, String giaTien, String img1Path, String img2Path, String img3Path, String img4Path, String img5Path) {
        this.id = id;
        this.tenPhong = tenPhong;
        this.dienTich = dienTich;
        this.moTa = moTa;
        this.giaTien = giaTien;
        this.img1Path = img1Path;
        this.img2Path = img2Path;
        this.img3Path = img3Path;
        this.img4Path = img4Path;
        this.img5Path = img5Path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getDienTich() {
        return dienTich;
    }

    public void setDienTich(String dienTich) {
        this.dienTich = dienTich;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String getImg1Path() {
        return img1Path;
    }

    public void setImg1Path(String img1Path) {
        this.img1Path = img1Path;
    }

    public String getImg2Path() {
        return img2Path;
    }

    public void setImg2Path(String img2Path) {
        this.img2Path = img2Path;
    }

    public String getImg3Path() {
        return img3Path;
    }

    public void setImg3Path(String img3Path) {
        this.img3Path = img3Path;
    }

    public String getImg4Path() {
        return img4Path;
    }

    public void setImg4Path(String img4Path) {
        this.img4Path = img4Path;
    }

    public String getImg5Path() {
        return img5Path;
    }

    public void setImg5Path(String img5Path) {
        this.img5Path = img5Path;
    }
}