
package Model;

public class DichVu {
    private String maDichVu;
    private String tenDichVu;

    public DichVu() {}

    public DichVu(String maDichVu, String tenDichVu) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }
}
