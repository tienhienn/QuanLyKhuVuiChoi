package Model;


public class SuKien {
    private String maSuKien;
    private String maDichVu;
    private String tenSuKien;
    private String thoiGianBatDau;
    private String thoiGianKetThuc;
    private String trangThaiHoatDong;
    private String mucDoCuonHut;
    private String gioiHanDoTuoi;
    private int sucChua;

    // Thêm tên dịch vụ từ bảng DichVu
    private String tenDichVu;
    
    public SuKien() {
    }
    public SuKien(String maSuKien, String maDichVu, String tenSuKien,
              String thoiGianBatDau, String thoiGianKetThuc,
              String trangThaiHoatDong, String mucDoCuonHut,
              String gioiHanDoTuoi, int sucChua) {
        this.maSuKien = maSuKien;
        this.maDichVu = maDichVu;
        this.tenSuKien = tenSuKien;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.trangThaiHoatDong = trangThaiHoatDong;
        this.mucDoCuonHut = mucDoCuonHut;
        this.gioiHanDoTuoi = gioiHanDoTuoi;
        this.sucChua = sucChua;
    }


    // Getters & Setters
    public String getMaSuKien() { return maSuKien; }
    public void setMaSuKien(String maSuKien) { this.maSuKien = maSuKien; }

    public String getMaDichVu() { return maDichVu; }
    public void setMaDichVu(String maDichVu) { this.maDichVu = maDichVu; }

    public String getTenSuKien() { return tenSuKien; }
    public void setTenSuKien(String tenSuKien) { this.tenSuKien = tenSuKien; }

    public String getThoiGianBatDau() { return thoiGianBatDau; }
    public void setThoiGianBatDau(String thoiGianBatDau) { this.thoiGianBatDau = thoiGianBatDau; }

    public String getThoiGianKetThuc() { return thoiGianKetThuc; }
    public void setThoiGianKetThuc(String thoiGianKetThuc) { this.thoiGianKetThuc = thoiGianKetThuc; }

    public String getTrangThaiHoatDong() { return trangThaiHoatDong; }
    public void setTrangThaiHoatDong(String trangThaiHoatDong) { this.trangThaiHoatDong = trangThaiHoatDong; }

    public String getMucDoCuonHut() { return mucDoCuonHut; }
    public void setMucDoCuonHut(String mucDoCuonHut) { this.mucDoCuonHut = mucDoCuonHut; }

    public String getGioiHanDoTuoi() { return gioiHanDoTuoi; }
    public void setGioiHanDoTuoi(String gioiHanDoTuoi) { this.gioiHanDoTuoi = gioiHanDoTuoi; }

    public int getSucChua() { return sucChua; }
    public void setSucChua(int sucChua) { this.sucChua = sucChua; }

    public String getTenDichVu() { return tenDichVu; }
    public void setTenDichVu(String tenDichVu) { this.tenDichVu = tenDichVu; }
}
