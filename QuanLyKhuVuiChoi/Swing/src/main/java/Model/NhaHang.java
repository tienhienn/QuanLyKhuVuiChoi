package Model;

public class NhaHang {
    private String maNhaHang;
    private String maDichVu;
    private String gioMoCua; 
    private String gioDongCua; 
    private String loaiNhaHang;
    
    private String tenDichVu;

    public NhaHang() {}

    public NhaHang(String maNhaHang, String maDichVu, String gioMoCua, String gioDongCua, String loaiNhaHang) {
        this.maNhaHang = maNhaHang;
        this.maDichVu = maDichVu;
        this.gioMoCua = gioMoCua;
        this.gioDongCua = gioDongCua;
        this.loaiNhaHang = loaiNhaHang;
    }

    // Getters & Setters
    public String getMaNhaHang() {return maNhaHang;
    }

    public void setMaNhaHang(String maNhaHang) {this.maNhaHang = maNhaHang;
    }

    public String getMaDichVu() {return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {this.maDichVu = maDichVu;
    }

    public String getGioMoCua() {return gioMoCua;
    }

    public void setGioMoCua(String gioMoCua) {this.gioMoCua = gioMoCua;
    }

    public String getGioDongCua() {return gioDongCua;
    }

    public void setGioDongCua(String gioDongCua) {this.gioDongCua = gioDongCua;
    }

    public String getLoaiNhaHang() {return loaiNhaHang;
    }

    public void setLoaiNhaHang(String loaiNhaHang) {this.loaiNhaHang = loaiNhaHang;
    }

    public String getTenDichVu() {return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {this.tenDichVu = tenDichVu;
    }
}
