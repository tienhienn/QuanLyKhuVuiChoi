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
public class NhaHang {
    private String maNhaHang;
    private String maDichVu;
    private Date TGianMoCua;
    private Date TgianDongCua;
    private String loaiNhaHang;

    public NhaHang(String maNhaHang, String maDichVu, Date TGianMoCua, Date TgianDongCua, String loaiNhaHang) {
        this.maNhaHang = maNhaHang;
        this.maDichVu = maDichVu;
        this.TGianMoCua = TGianMoCua;
        this.TgianDongCua = TgianDongCua;
        this.loaiNhaHang = loaiNhaHang;
    }

    public NhaHang() {
    }

    public String getMaNhaHang() {
        return maNhaHang;
    }

    public void setMaNhaHang(String maNhaHang) {
        this.maNhaHang = maNhaHang;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public Date getTGianMoCua() {
        return TGianMoCua;
    }

    public void setTGianMoCua(Date TGianMoCua) {
        this.TGianMoCua = TGianMoCua;
    }

    public Date getTgianDongCua() {
        return TgianDongCua;
    }

    public void setTgianDongCua(Date TgianDongCua) {
        this.TgianDongCua = TgianDongCua;
    }

    public String getLoaiNhaHang() {
        return loaiNhaHang;
    }

    public void setLoaiNhaHang(String loaiNhaHang) {
        this.loaiNhaHang = loaiNhaHang;
    }
    
    
}
