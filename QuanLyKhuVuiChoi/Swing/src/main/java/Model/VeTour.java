package Model;

public class VeTour {
    private String maVeTour;
    private String maDatVe;
    private String maTour;
    private int soLuongNguoi;
    private String phuongTien;
    private String gioDi;

    public VeTour() {
    }

    public VeTour(String maVeTour, String maDatVe, String maTour, int soLuongNguoi, String phuongTien, String gioDi) {
        this.maVeTour = maVeTour;
        this.maDatVe = maDatVe;
        this.maTour = maTour;
        this.soLuongNguoi = soLuongNguoi;
        this.phuongTien = phuongTien;
        this.gioDi = gioDi;
    }

    // Getters and Setters
    public String getMaVeTour() { return maVeTour; }
    public void setMaVeTour(String maVeTour) { this.maVeTour = maVeTour; }

    public String getMaDatVe() { return maDatVe; }
    public void setMaDatVe(String maDatVe) { this.maDatVe = maDatVe; }

    public String getMaTour() { return maTour; }
    public void setMaTour(String maTour) { this.maTour = maTour; }

    public int getSoLuongNguoi() { return soLuongNguoi; }
    public void setSoLuongNguoi(int soLuongNguoi) { this.soLuongNguoi = soLuongNguoi; }

    public String getPhuongTien() { return phuongTien; }
    public void setPhuongTien(String phuongTien) { this.phuongTien = phuongTien; }

    public String getGioDi() { return gioDi; }
    public void setGioDi(String gioDi) { this.gioDi = gioDi; }
}
