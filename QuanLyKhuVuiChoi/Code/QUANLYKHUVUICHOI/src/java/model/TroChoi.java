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
public class TroChoi {
    private String maTroChoi;
    private String maDichVu;
    private String tenTroChoi;
    private String moTa;
    private int gioiHanDoTuoi;
    private int sucChua;
    private Date thoiGianHoatDong;

    public TroChoi(String maTroChoi, String maDichVu, String tenTroChoi, String mota, int gioiHanDoTuoi, int sucChua, Date thoiGianHoatDong) {
        this.maTroChoi = maTroChoi;
        this.maDichVu = maDichVu;
        this.tenTroChoi = tenTroChoi;
        this.moTa = mota;
        this.gioiHanDoTuoi = gioiHanDoTuoi;
        this.sucChua = sucChua;
        this.thoiGianHoatDong = thoiGianHoatDong;
    }

    public TroChoi() {
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }
    
    
    public String getMaTroChoi() {
        return maTroChoi;
    }

    public void setMaTroChoi(String maTroChoi) {
        this.maTroChoi = maTroChoi;
    }

    public String getTenTroChoi() {
        return tenTroChoi;
    }

    public void setTenTroChoi(String tenTroChoi) {
        this.tenTroChoi = tenTroChoi;
    }

    public String getMota() {
        return moTa;
    }

    public void setMota(String mota) {
        this.moTa = mota;
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

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    public Date getThoiGianHoatDong() {
        return thoiGianHoatDong;
    }

    public void setThoiGianHoatDong(Date thoiGianHoatDong) {
        this.thoiGianHoatDong = thoiGianHoatDong;
    }
    
    
}
