package Model;

import java.util.Date;

public class NhanVien {
    private String maNhanVien;
    private String tenNhanVien;
    private Date ngaySinh;
    private String sdt;
    private String email;
    private Date ngayBatDauLam;
    private double luong;

    // Constructors, getters, setters
    public NhanVien(String maNhanVien, String tenNhanVien, Date ngaySinh, String sdt,
                    String email, Date ngayBatDauLam, double luong) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.email = email;
        this.ngayBatDauLam = ngayBatDauLam;
        this.luong = luong;
    }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public String getTenNhanVien() { return tenNhanVien; }
    public void setTenNhanVien(String tenNhanVien) { this.tenNhanVien = tenNhanVien; }

    public java.sql.Date getNgaySinh() { return (java.sql.Date) ngaySinh; }
    public void setNgaySinh(Date ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public java.sql.Date getNgayBatDauLam() { return (java.sql.Date) ngayBatDauLam; }
    public void setNgayBatDauLam(Date ngayBatDauLam) { this.ngayBatDauLam = ngayBatDauLam; }

    public double getLuong() { return luong; }
    public void setLuong(double luong) { this.luong = luong; }
}
