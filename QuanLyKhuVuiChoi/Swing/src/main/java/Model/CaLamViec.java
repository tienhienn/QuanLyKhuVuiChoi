package Model;

import java.sql.Time;

public class CaLamViec {
    private String maCa;
    private String tenCa;
    private Time gioBatDau;
    private Time gioKetThuc;

    public CaLamViec(String maCa, String tenCa, Time gioBatDau, Time gioKetThuc) {
        this.maCa = maCa;
        this.tenCa = tenCa;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
    }

    public String getMaCa() {
        return maCa;
    }

    public String getTenCa() {
        return tenCa;
    }

    public Time getGioBatDau() {
        return gioBatDau;
    }

    public Time getGioKetThuc() {
        return gioKetThuc;
    }
}