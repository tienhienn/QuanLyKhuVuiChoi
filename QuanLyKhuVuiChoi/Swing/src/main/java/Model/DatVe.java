package Model;

import java.util.Date;

public class DatVe {
    private String maDatVe;
    private String maKhachHang;
    private Date ngayDat;
    private String loaiVe;
    private Date ngayDi;

    public DatVe(String maDatVe, String maKhachHang, Date ngayDat, String loaiVe, Date ngayDi) {
        this.maDatVe = maDatVe;
        this.maKhachHang = maKhachHang;
        this.ngayDat = ngayDat;
        this.loaiVe = loaiVe;
        this.ngayDi = ngayDi;
    }

    // Getters and Setters
    public String getMaDatVe() { return maDatVe; }
    public void setMaDatVe(String maDatVe) { this.maDatVe = maDatVe; }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public Date getNgayDat() { return ngayDat; }
    public void setNgayDat(Date ngayDat) { this.ngayDat = ngayDat; }

    public String getLoaiVe() { return loaiVe; }
    public void setLoaiVe(String loaiVe) { this.loaiVe = loaiVe; }

    public Date getNgayDi() { return ngayDi; }
    public void setNgayDi(Date ngayDi) { this.ngayDi = ngayDi; }
}
