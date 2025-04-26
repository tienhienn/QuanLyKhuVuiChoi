package Model;

public class Khu {
    private String maKhu;
    private String tenKhu;
    private String moTa;

    public Khu(String maKhu, String tenKhu, String moTa) {
        this.maKhu = maKhu;
        this.tenKhu = tenKhu;
        this.moTa = moTa;
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

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return tenKhu;
    }
}

