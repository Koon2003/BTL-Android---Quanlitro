package com.example.appquanlitro;

public class TienDien {
    String iddien,id,dongtiendienthangnam,sokwtieuthu,giatien,tongtien,trangthai;

    public TienDien(String iddien, String id, String dongtiendienthangnam, String sokwtieuthu, String giatien, String tongtien, String trangthai) {
        this.iddien = iddien;
        this.id = id;
        this.dongtiendienthangnam = dongtiendienthangnam;
        this.sokwtieuthu = sokwtieuthu;
        this.giatien = giatien;
        this.tongtien = tongtien;
        this.trangthai = trangthai;
    }

    public String getGiatien() {
        return giatien;
    }

    public void setGiatien(String giatien) {
        this.giatien = giatien;
    }

    public String getIddien() {
        return iddien;
    }

    public void setIddien(String iddien) {
        this.iddien = iddien;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDongtiendienthangnam() {
        return dongtiendienthangnam;
    }

    public void setDongtiendienthangnam(String dongtiendienthangnam) {
        this.dongtiendienthangnam = dongtiendienthangnam;
    }

    public String getSokwtieuthu() {
        return sokwtieuthu;
    }

    public void setSokwtieuthu(String sokwtieuthu) {
        this.sokwtieuthu = sokwtieuthu;
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
