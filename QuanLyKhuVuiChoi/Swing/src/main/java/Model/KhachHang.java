package Model;

public class KhachHang {
    private String maKhachHang;
    private String tenKhachHang;
    private String matKhau;
    private String sdt;
    private String email;

    public KhachHang(String maKhachHang, String tenKhachHang, String matKhau, String sdt, String email) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.matKhau = matKhau;
        this.sdt = sdt;
        this.email = email;
    }

    // Getters & Setters
    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public String getTenKhachHang() { return tenKhachHang; }
    public void setTenKhachHang(String tenKhachHang) { this.tenKhachHang = tenKhachHang; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
