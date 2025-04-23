/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class Khu {
    private String maKhu;
    private String tenKhu;
    private String mota;

    public Khu(String maKhu, String tenKhu, String mota) {
        this.maKhu = maKhu;
        this.tenKhu = tenKhu;
        this.mota = mota;
    }

    public Khu() {
    }

    public String getMaKhu() {
        return maKhu;
    }

    public void setMaKhu(String maKhu) {
        this.maKhu = maKhu;
    }

    public String getTenKhu() {
        return tenKhu;
    }

    public void setTenKhu(String tenKhu) {
        this.tenKhu = tenKhu;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
    
    
}
