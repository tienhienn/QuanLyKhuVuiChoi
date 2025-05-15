package Controller;

import DAO.PhanCongCaDAO;
import Model.CaLamViec;
import View.PhanCongCaForm;

import javax.swing.*;
import java.sql.Connection;

public class PhanCongCaController {

    private PhanCongCaForm view;
    private Connection conn;

    public PhanCongCaController(PhanCongCaForm view, Connection conn) {
        this.view = view;
        this.conn = conn;

        // Đăng ký sự kiện cho nút Xác nhận
        view.btnXacNhan.addActionListener(e -> addA());
    }

    private void addA() {
        String maNV = view.tfMaNV.getText().trim();
        String tenCa = (String) view.cbTenCa.getSelectedItem();
        String ngay = view.tfNgay.getText().trim();

        // Kiểm tra dữ liệu đầu vào
        if (maNV.isEmpty() || tenCa == null || tenCa.isEmpty() || ngay.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin!");
            return;
        }

        // Lấy CaLamViec từ map
        CaLamViec ca = view.caMap.get(tenCa);
        if (ca == null) {
            JOptionPane.showMessageDialog(view, "Ca làm việc không hợp lệ!");
            return;
        }

        String maCa = ca.getMaCa();

        // Gọi DAO để phân công ca
        PhanCongCaDAO dao = new PhanCongCaDAO(conn);
        boolean thanhCong = dao.phanCongCa(maNV, maCa, ngay);

        if (thanhCong) {
            JOptionPane.showMessageDialog(view, "Phân công ca thành công!");
            view.dispose();  // Đóng form sau khi phân công thành công
        } else {
            JOptionPane.showMessageDialog(view, "Phân công ca thất bại!");
        }
    }
}
