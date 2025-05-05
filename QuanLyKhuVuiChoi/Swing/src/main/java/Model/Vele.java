package Model;

public class Vele {
    private String maVele;
    private String maDatVe;
    private String doiTuong;
    private int soLuongNguoi;

    public Vele() {
    }

    public Vele(String maVele, String maDatVe, String doiTuong, int soLuongNguoi) {
        this.maVele = maVele;
        this.maDatVe = maDatVe;
        this.doiTuong = doiTuong;
        this.soLuongNguoi = soLuongNguoi;
    }

    // Getters and Setters
    public String getMaVele() { return maVele; }
    public void setMaVele(String maVele) { this.maVele = maVele; }

    public String getMaDatVe() { return maDatVe; }
    public void setMaDatVe(String maDatVe) { this.maDatVe = maDatVe; }

    public String getDoiTuong() { return doiTuong; }
    public void setDoiTuong(String doiTuong) { this.doiTuong = doiTuong; }

    public int getSoLuongNguoi() { return soLuongNguoi; }
    public void setSoLuongNguoi(int soLuongNguoi) { this.soLuongNguoi = soLuongNguoi; }
}
