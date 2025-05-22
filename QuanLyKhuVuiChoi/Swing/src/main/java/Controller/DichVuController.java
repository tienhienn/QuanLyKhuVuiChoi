package Controller;

import DAO.DichVuDAO;
import Model.DichVu;

import java.sql.Connection;
import java.util.ArrayList;

public class DichVuController {
    private DichVuDAO dichVuDAO;

    public DichVuController(Connection conn) {
        this.dichVuDAO = new DichVuDAO(conn);
    }

    public ArrayList<DichVu> layTatCaDichVu() {
        return dichVuDAO.getAll();
    }

    public boolean themDichVu(DichVu dichVu) {
        return dichVuDAO.addDichVu(dichVu);
    }

    public boolean capNhatDichVu(DichVu dichVu) {
        return dichVuDAO.updateDichVu(dichVu);
    }

    public boolean xoaDichVu(String maDichVu) {
        return dichVuDAO.deleteDichVu(maDichVu);
    }

    public DichVu timDichVuTheoMa(String maDichVu) {
        return dichVuDAO.getById(maDichVu);
    }
    
    // --- Các phương thức xử lý sự kiện chuyển từ View sang Controller ---

    public String themDichVuSuKien(String ma, String ten) {
        if (ma == null || ma.isEmpty() || ten == null || ten.isEmpty()) {
            return "Vui lòng nhập đầy đủ thông tin!";
        }
        DichVu dv = new DichVu(ma, ten);
        if (themDichVu(dv)) {
            return "Thêm thành công!";
        } else {
            return "Thêm thất bại!";
        }
    }

    public String suaDichVuSuKien(String ma, String ten) {
        if (ma == null || ma.isEmpty() || ten == null || ten.isEmpty()) {
            return "Vui lòng chọn dịch vụ để sửa!";
        }
        DichVu dv = new DichVu(ma, ten);
        if (capNhatDichVu(dv)) {
            return "Cập nhật thành công!";
        } else {
            return "Cập nhật thất bại!";
        }
    }

    public String xoaDichVuSuKien(String ma) {
        if (ma == null || ma.isEmpty()) {
            return "Vui lòng chọn dịch vụ để xóa!";
        }
        if (xoaDichVu(ma)) {
            return "Xóa thành công!";
        } else {
            return "Xóa thất bại!";
        }
    }

    public ArrayList<DichVu> timKiemDichVuSuKien(String keyword) {
        ArrayList<DichVu> result = new ArrayList<>();
        if (keyword == null || keyword.isEmpty()) {
            result = layTatCaDichVu();
        } else {
            for (DichVu dv : layTatCaDichVu()) {
                if (dv.getMaDichVu().contains(keyword) || dv.getTenDichVu().toLowerCase().contains(keyword.toLowerCase())) {
                    result.add(dv);
                }
            }
        }
        return result;
    }  
}
