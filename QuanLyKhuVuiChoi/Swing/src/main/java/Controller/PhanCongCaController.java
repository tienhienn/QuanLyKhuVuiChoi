package Controller;

import DAO.PhanCongCaDAO;
import Model.CaLamViec;
import View.PhanCongCaForm;

import javax.swing.*;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class PhanCongCaController {

    private PhanCongCaForm view;
    private Connection conn;

    public PhanCongCaController(PhanCongCaForm view, Connection conn) {
        this.view = view;
        this.conn = conn;

        // Đăng ký sự kiện cho nút Xác nhận
        view.btnXacNhan.addActionListener(e -> addA());
    }

    private String convertDateFormat(String dateStr) {
        try {
            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
            displayFormat.setLenient(false);
            java.util.Date date = displayFormat.parse(dateStr);
            return dbFormat.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

    private void addA() {
        try {
            String maNV = view.tfMaNV.getText().trim();
            String tenCa = (String) view.cbTenCa.getSelectedItem();
            String ngay = view.tfNgay.getText().trim();

            // Kiểm tra dữ liệu đầu vào
            if (maNV.isEmpty() || tenCa == null || tenCa.isEmpty() || ngay.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            // Kiểm tra và chuyển đổi định dạng ngày
            String ngayDB = convertDateFormat(ngay);
            if (ngayDB == null) {
                JOptionPane.showMessageDialog(view, "Ngày phải theo định dạng dd/MM/yyyy!");
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
            boolean thanhCong = dao.phanCongCa(maNV, maCa, ngayDB);

            if (thanhCong) {
                JOptionPane.showMessageDialog(view, "Phân công ca thành công!");
                view.dispose();  // Đóng form sau khi phân công thành công
            } else {
                JOptionPane.showMessageDialog(view, "Phân công ca thất bại! Có thể ca này đã được phân công cho nhân viên vào ngày này.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Đã xảy ra lỗi: " + ex.getMessage());
        }
    }
}
