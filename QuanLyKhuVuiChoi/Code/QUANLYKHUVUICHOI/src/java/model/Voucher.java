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
public class Voucher {
    private String maVoucher;
    private String tenVoucher;
    private Date TGianHieuLuc;
    private Date TGianKetThuc;
    private long giaTriUuDai;
    private int soLuong;

    public Voucher(String maVoucher, String tenVoucher, Date TGianHieuLuc, Date TGianKetThuc, long giaTriUuDai, int soLuong) {
        this.maVoucher = maVoucher;
        this.tenVoucher = tenVoucher;
        this.TGianHieuLuc = TGianHieuLuc;
        this.TGianKetThuc = TGianKetThuc;
        this.giaTriUuDai = giaTriUuDai;
        this.soLuong = soLuong;
    }

    public Voucher() {
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public String getTenVoucher() {
        return tenVoucher;
    }

    public void setTenVoucher(String tenVoucher) {
        this.tenVoucher = tenVoucher;
    }

    public Date getTGianHieuLuc() {
        return TGianHieuLuc;
    }

    public void setTGianHieuLuc(Date TGianHieuLuc) {
        this.TGianHieuLuc = TGianHieuLuc;
    }

    public Date getTGianKetThuc() {
        return TGianKetThuc;
    }

    public void setTGianKetThuc(Date TGianKetThuc) {
        this.TGianKetThuc = TGianKetThuc;
    }

    public double getGiaTriUuDai() {
        return giaTriUuDai;
    }

    public void setGiaTriUuDai(long giaTriUuDai) {
        this.giaTriUuDai = giaTriUuDai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
    
    
}
