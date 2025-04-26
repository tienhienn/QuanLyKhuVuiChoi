package Model;

import java.sql.Date;

public class Tour {
    private String maTour;
    private String tenTour;
    private String moTa;
    private double giaTour;
    private Date tgBatDau;
    private Date tgKetThuc;
    private int soLuongMax;

    public Tour(String maTour, String tenTour, String moTa, double giaTour, Date tgBatDau, Date tgKetThuc, int soLuongMax) {
        this.maTour = maTour;
        this.tenTour = tenTour;
        this.moTa = moTa;
        this.giaTour = giaTour;
        this.tgBatDau = tgBatDau;
        this.tgKetThuc = tgKetThuc;
        this.soLuongMax = soLuongMax;
    }

    // Getter - Setter
    public String getMaTour() { return maTour; }
    public void setMaTour(String maTour) { this.maTour = maTour; }

    public String getTenTour() { return tenTour; }
    public void setTenTour(String tenTour) { this.tenTour = tenTour; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public double getGiaTour() { return giaTour; }
    public void setGiaTour(double giaTour) { this.giaTour = giaTour; }

    public Date getTgBatDau() { return tgBatDau; }
    public void setTgBatDau(Date tgBatDau) { this.tgBatDau = tgBatDau; }

    public Date getTgKetThuc() { return tgKetThuc; }
    public void setTgKetThuc(Date tgKetThuc) { this.tgKetThuc = tgKetThuc; }

    public int getSoLuongMax() { return soLuongMax; }
    public void setSoLuongMax(int soLuongMax) { this.soLuongMax = soLuongMax; }
}
