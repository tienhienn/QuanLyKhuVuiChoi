package Model;

import java.sql.Date;

public class CaLamViec {
    private String maCa;
    private String tenCa;
    private Date gioBatDau;
    private Date gioKetThuc;

    public CaLamViec(String maCa, String tenCa, Date gioBatDau, Date gioKetThuc) {
        this.maCa = maCa;
        this.tenCa = tenCa;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }

    public String getTenCa() {
        return tenCa;
    }

    public void setTenCa(String tenCa) {
        this.tenCa = tenCa;
    }

    public Date getGioBatDau() {
        return gioBatDau;
    }

    public void setGioBatDau(Date gioBatDau) {
        this.gioBatDau = gioBatDau;
    }

    public Date getGioKetThuc() {
        return gioKetThuc;
    }

    public void setGioKetThuc(Date gioKetThuc) {
        this.gioKetThuc = gioKetThuc;
    }
}
