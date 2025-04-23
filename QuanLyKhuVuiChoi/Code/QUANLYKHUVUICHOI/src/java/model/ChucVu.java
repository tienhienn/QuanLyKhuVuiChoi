/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class ChucVu {
    private String maChucVu;
    private String tenChuCVu;

    public ChucVu(String maChucVu, String tenChuCVu) {
        this.maChucVu = maChucVu;
        this.tenChuCVu = tenChuCVu;
    }

    public ChucVu() {
    }

    public String getMaChucVu() {
        return maChucVu;
    }

    public void setMaChucVu(String maChucVu) {
        this.maChucVu = maChucVu;
    }

    public String getTenChuCVu() {
        return tenChuCVu;
    }

    public void setTenChuCVu(String tenChuCVu) {
        this.tenChuCVu = tenChuCVu;
    }
    
    
}
