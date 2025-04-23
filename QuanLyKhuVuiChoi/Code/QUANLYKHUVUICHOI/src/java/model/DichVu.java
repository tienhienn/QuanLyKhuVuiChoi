/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class DichVu {
    private String maDichVu;
    private String maKhu;
    private String tenDichVu;
    private String mota;

    public DichVu(String maDichVu, String maKhu, String tenDichVu, String mota) {
        this.maDichVu = maDichVu;
        this.maKhu = maKhu;
        this.tenDichVu = tenDichVu;
        this.mota = mota;
    }

    public DichVu() {
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getMaKhu() {
        return maKhu;
    }

    public void setMaKhu(String maKhu) {
        this.maKhu = maKhu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
    
}
