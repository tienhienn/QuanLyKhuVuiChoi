package Model;

import java.sql.Date;

public class PhanCongCa {
    private String maNhanVien;
    private String maCa;
    private Date ngayLamViec;

    public PhanCongCa(String maNhanVien, String maCa, Date ngayLamViec) {
        this.maNhanVien = maNhanVien;
        this.maCa = maCa;
        this.ngayLamViec = ngayLamViec;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaCa() {
        return maCa;
    }

    public void setMaCa(String maCa) {
        this.maCa = maCa;
    }

    public Date getNgayLamViec() {
        return ngayLamViec;
    }

    public void setNgayLamViec(Date ngayLamViec) {
        this.ngayLamViec = ngayLamViec;
    }
}
