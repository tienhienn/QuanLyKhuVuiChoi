/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author admin
 */
public class VeTour {
    private String maVeTour;
    private String maDatVe;
    private String maTour;
    private Date thoiGian;
    private String phuongTien;
    private String gioDi;

    public VeTour(String maVeTour, String maDatVe, String maTour, Date thoiGian, String phuongTien, String gioDi) {
        this.maVeTour = maVeTour;
        this.maDatVe = maDatVe;
        this.maTour = maTour;
        this.thoiGian = thoiGian;
        this.phuongTien = phuongTien;
        this.gioDi = gioDi;
    }

    public VeTour() {
    }

    public String getMaVeTour() {
        return maVeTour;
    }

    public void setMaVeTour(String maVeTour) {
        this.maVeTour = maVeTour;
    }

    public String getMaDatVe() {
        return maDatVe;
    }

    public void setMaDatVe(String maDatVe) {
        this.maDatVe = maDatVe;
    }

    public String getMaTour() {
        return maTour;
    }

    public void setMaTour(String maTour) {
        this.maTour = maTour;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getPhuongTien() {
        return phuongTien;
    }

    public void setPhuongTien(String phuongTien) {
        this.phuongTien = phuongTien;
    }

    public String getGioDi() {
        return gioDi;
    }

    public void setGioDi(String gioDi) {
        this.gioDi = gioDi;
    }
    
    
}
