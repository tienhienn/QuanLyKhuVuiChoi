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
public class DatVe {
    private String maDatVe;
    private String maKhachHang;
    private Date ngayDat;
    private String loaiVe;
    private int soLuong;
    private Date ngayDi;

    public DatVe(String maDatVe, String maKhachHang, Date ngayDat, String loaiVe, int soLuong, Date ngayDi) {
        this.maDatVe = maDatVe;
        this.maKhachHang = maKhachHang;
        this.ngayDat = ngayDat;
        this.loaiVe = loaiVe;
        this.soLuong = soLuong;
        this.ngayDi = ngayDi;
    }

    public DatVe() {
    }

    public String getMaDatVe() {
        return maDatVe;
    }

    public void setMaDatVe(String maDatVe) {
        this.maDatVe = maDatVe;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public Date getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Date ngayDat) {
        this.ngayDat = ngayDat;
    }

    public String getLoaiVe() {
        return loaiVe;
    }

    public void setLoaiVe(String loaiVe) {
        this.loaiVe = loaiVe;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayDi() {
        return ngayDi;
    }

    public void setNgayDi(Date ngayDi) {
        this.ngayDi = ngayDi;
    }
    
    
}
