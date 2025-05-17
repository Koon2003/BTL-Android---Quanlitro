package com.example.appquanlitro;

public class TienNuoc {
    String idnuoc,id,dongtiennuocthangnam,sokhoitieuthu,giatien,tongtien,trangthai;

    public TienNuoc(String idnuoc, String id, String dongtiennuocthangnam, String sokhoitieuthu, String giatien, String tongtien, String trangthai) {
        this.idnuoc = idnuoc;
        this.id = id;
        this.dongtiennuocthangnam = dongtiennuocthangnam;
        this.sokhoitieuthu = sokhoitieuthu;
        this.giatien = giatien;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public String getIdnuoc() {
        return idnuoc;
    }

    public void setIdnuoc(String idnuoc) {
        this.idnuoc = idnuoc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDongtiennuocthangnam() {
        return dongtiennuocthangnam;
    }

    public void setDongtiennuocthangnam(String dongtiennuocthangnam) {
        this.dongtiennuocthangnam = dongtiennuocthangnam;
    }

    public String getSokhoitieuthu() {
        return sokhoitieuthu;
    }

    public void setSokhoitieuthu(String sokhoitieuthu) {
        this.sokhoitieuthu = sokhoitieuthu;
    }

    public String getGiatien() {
        return giatien;
    }

    public void setGiatien(String giatien) {
        this.giatien = giatien;
    }

    public String getTongtien() {
        return tongtien;
    }

    public void setTongtien(String tongtien) {
        this.tongtien = tongtien;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
