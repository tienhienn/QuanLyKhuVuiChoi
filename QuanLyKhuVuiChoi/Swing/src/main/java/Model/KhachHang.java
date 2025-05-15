package Model;

import java.sql.Date;

public class KhachHang {
    private String maKhachHang;
    private String tenKhachHang;
    private String matKhau;
    private String sdt;
    private String email;
    private String diaChi;
    private String gioiTinh;
    private Date ngaySinh;
    private String quocTich;

    public KhachHang(String maKhachHang, String tenKhachHang, String matKhau, String sdt, String email, String diaChi, String gioiTinh, Date ngaySinh, String quocTich) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.matKhau = matKhau;
        this.sdt = sdt;
        this.email = email;
        this.diaChi = diaChi;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.quocTich = quocTich;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }  
}
