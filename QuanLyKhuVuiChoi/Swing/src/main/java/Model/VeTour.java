package Model;

public class VeTour {
    private String maVeTour;
    private String maDatVe;
    private String maTour;
    private int nguoiLon;
    private int treEm;
    private int nguoiGia;

    public VeTour() {
    }

    public VeTour(String maVeTour, String maDatVe, String maTour, int nguoiLon, int treEm, int nguoiGia) {
        this.maVeTour = maVeTour;
        this.maDatVe = maDatVe;
        this.maTour = maTour;
        this.nguoiLon = nguoiLon;
        this.treEm = treEm;
        this.nguoiGia = nguoiGia;
    }

    // Getters and Setters
    public String getMaVeTour() {return maVeTour;}

    public void setMaVeTour(String maVeTour) {this.maVeTour = maVeTour;}

    public String getMaDatVe() {return maDatVe;}

    public void setMaDatVe(String maDatVe) {this.maDatVe = maDatVe;}

    public String getMaTour() {return maTour;}

    public void setMaTour(String maTour) {this.maTour = maTour;}

    public int getNguoiLon() {return nguoiLon;}

    public void setNguoiLon(int nguoiLon) {this.nguoiLon = nguoiLon;}
    
    public int getTreEm() {return treEm;}

    public void setTreEm(int treEm) {this.treEm = treEm;}

    public int getNguoiGia() {return nguoiGia;}

    public void setNguoiGia(int nguoiGia) {this.nguoiGia = nguoiGia;}
}
