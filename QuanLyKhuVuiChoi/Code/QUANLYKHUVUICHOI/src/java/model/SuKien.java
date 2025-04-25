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
public class SuKien {
    private String masukien;
    private String maDichVu;
    private String tenSuKien;
    private Date TGianBatDau;
    private Date TgianKetThuc;
    private String trangThaiHoatDong;
    private String mucDoCuonHut;
    private int gioiHanDoTuoi;
    private int sucChua;

    public SuKien(String masukien, String maDichVu, String tenSuKien, Date TGianBatDau, Date TgianKetThuc, String TrangThaiHoatDong, String mucDoCuonHut, int gioiHanDoTuoi, int SucChua) {
        this.masukien = masukien;
        this.maDichVu = maDichVu;
        this.tenSuKien = tenSuKien;
        this.TGianBatDau = TGianBatDau;
        this.TgianKetThuc = TgianKetThuc;
        this.trangThaiHoatDong = TrangThaiHoatDong;
        this.mucDoCuonHut = mucDoCuonHut;
        this.gioiHanDoTuoi = gioiHanDoTuoi;
        this.sucChua = SucChua;
    }

    public SuKien() {
    }

    public String getMasukien() {
        return masukien;
    }

    public void setMasukien(String masukien) {
        this.masukien = masukien;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenSuKien() {
        return tenSuKien;
    }

    public void setTenSuKien(String tenSuKien) {
        this.tenSuKien = tenSuKien;
    }

    public Date getTGianBatDau() {
        return TGianBatDau;
    }

    public void setTGianBatDau(Date TGianBatDau) {
        this.TGianBatDau = TGianBatDau;
    }

    public Date getTgianKetThuc() {
        return TgianKetThuc;
    }

    public void setTgianKetThuc(Date TgianKetThuc) {
        this.TgianKetThuc = TgianKetThuc;
    }

    public String getTrangThaiHoatDong() {
        return trangThaiHoatDong;
    }

    public void setTrangThaiHoatDong(String TrangThaiHoatDong) {
        this.trangThaiHoatDong = TrangThaiHoatDong;
    }

    public String getMucDoCuonHut() {
        return mucDoCuonHut;
    }

    public void setMucDoCuonHut(String mucDoCuonHut) {
        this.mucDoCuonHut = mucDoCuonHut;
    }

    public int getGioiHanDoTuoi() {
        return gioiHanDoTuoi;
    }

    public void setGioiHanDoTuoi(int gioiHanDoTuoi) {
        this.gioiHanDoTuoi = gioiHanDoTuoi;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setSucChua(int SucChua) {
        this.sucChua = SucChua;
    }
    
    
    
}
