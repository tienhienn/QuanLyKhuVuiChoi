package Model;


public class TroChoi {
    private String maTroChoi;
    private String maDichVu;
    private String tenTroChoi;
    private String moTa;
    private String gioiHanDoTuoi;
    private int sucChua;
    private String thoiGianHoatDong;

    // Thêm tên dịch vụ từ bảng DichVu
    private String tenDichVu;
    
    public TroChoi(){
            
    }
    public TroChoi(String maTroChoi, String maDichVu, String tenTroChoi, String moTa, int gioiHanTuoi, int sucChua, String thoiGianHoatDong) {
        this.maTroChoi = maTroChoi;
        this.maDichVu = maDichVu;
        this.tenTroChoi = tenTroChoi;
        this.moTa = moTa;
        this.gioiHanDoTuoi = String.valueOf(gioiHanTuoi);
        this.sucChua = sucChua;
        this.thoiGianHoatDong = thoiGianHoatDong.toString();
    }
    // Getters & Setters
    public String getMaTroChoi() { return maTroChoi; }
    public void setMaTroChoi(String maTroChoi) { this.maTroChoi = maTroChoi; }

    public String getMaDichVu() { return maDichVu; }
    public void setMaDichVu(String maDichVu) { this.maDichVu = maDichVu; }

    public String getTenTroChoi() { return tenTroChoi; }
    public void setTenTroChoi(String tenTroChoi) { this.tenTroChoi = tenTroChoi; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getGioiHanDoTuoi() { return gioiHanDoTuoi; }
    public void setGioiHanDoTuoi(String gioiHanDoTuoi) { this.gioiHanDoTuoi = gioiHanDoTuoi; }

    public int getSucChua() { return sucChua; }
    public void setSucChua(int sucChua) { this.sucChua = sucChua; }

    public String getThoiGianHoatDong() { return thoiGianHoatDong; }
    public void setThoiGianHoatDong(String thoiGianHoatDong) { this.thoiGianHoatDong = thoiGianHoatDong; }

    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }
}

