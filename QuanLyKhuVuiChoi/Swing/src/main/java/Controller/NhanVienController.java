package Controller;

import DAO.CaLamViecDAO;
import DAO.NhanVienDAO;
import Model.CaLamViec;
import Model.NhanVien;
import View.NhanVienPanel;
import View.PhanCongCaForm;
import View.CaDaPhanCongForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class NhanVienController {
    private NhanVienPanel view;
    private NhanVienDAO dao;
    private Connection conn;


    public NhanVienController(NhanVienPanel view, Connection conn) {
        this.view = view;
        this.dao = new NhanVienDAO(conn);
        this.conn = conn;
        loadDataToTable();

        view.btnThem.addActionListener(e -> themNhanVien());
        view.btnSua.addActionListener(e -> suaNhanVien());
        view.btnXoa.addActionListener(e -> xoaNhanVien());
        view.btnTim.addActionListener(e -> timKiemNhanVien());
        view.btnClear.addActionListener(e -> clearForm());
        view.btnPhanCong.addActionListener(e -> moFormPhanCongCa());
        view.btnXemCa.addActionListener(e -> moFormXemCa()); 

        view.table.getSelectionModel().addListSelectionListener(e -> fillFormTuTable());
    }

    private void loadDataToTable() {
        List<NhanVien> list = dao.getAllNhanVien();
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);
        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                    nv.getMaNhanVien(), nv.getTenNhanVien(),
                    nv.getNgaySinh(), nv.getSdt(), nv.getEmail(),
                    nv.getNgayBatDauLam(), nv.getLuong()
            });
        }
    }

    private void fillFormTuTable() {
        int row = view.table.getSelectedRow();
        if (row != -1) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                view.tfMa.setText(view.table.getValueAt(row, 0).toString());
                view.tfTen.setText(view.table.getValueAt(row, 1).toString());

                Date ngaySinh = Date.valueOf(view.table.getValueAt(row, 2).toString());
                view.tfNgaySinh.setText(sdf.format(ngaySinh));

                view.tfSDT.setText(view.table.getValueAt(row, 3).toString());
                view.tfEmail.setText(view.table.getValueAt(row, 4).toString());

                Date ngayBD = Date.valueOf(view.table.getValueAt(row, 5).toString());
                view.tfNgayBD.setText(sdf.format(ngayBD));

                view.tfLuong.setText(view.table.getValueAt(row, 6).toString());
                view.tfMa.setEditable(false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Lỗi khi đọc dữ liệu ngày.");
            }
        }
    }


    private void themNhanVien() {
        NhanVien nv = layDuLieuForm();
        if (dao.themNhanVien(nv)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!");
        }
    }

    private void suaNhanVien() {
        NhanVien nv = layDuLieuForm();
        if (dao.capNhatNhanVien(nv)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
        }
    }

    private void xoaNhanVien() {
        String maNV = view.tfMa.getText();
        if (dao.xoaNhanVien(maNV)) {
            JOptionPane.showMessageDialog(view, "Xoá thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(view, "Xoá thất bại!");
        }
    }

    private void timKiemNhanVien() {
        String keyword = view.tfTimKiem.getText();
        List<NhanVien> list = dao.timKiemNhanVien(keyword);
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);
        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                    nv.getMaNhanVien(), nv.getTenNhanVien(),
                    nv.getNgaySinh(), nv.getSdt(), nv.getEmail(),
                    nv.getNgayBatDauLam(), nv.getLuong()
            });
        }
    }

    private void moFormPhanCongCa() {
        String maNV = view.tfMa.getText();
        if (!maNV.isEmpty()) {
            // Truyền Connection và maNV vào PhanCongCaForm
            PhanCongCaForm form = new PhanCongCaForm((JFrame) SwingUtilities.getWindowAncestor(view), maNV, conn);

            // Nạp danh sách ca từ DB
            List<CaLamViec> danhSachCa = new CaLamViecDAO(conn).layTatCaCa();
            for (CaLamViec ca : danhSachCa) {
                form.cbTenCa.addItem(ca.getTenCa());
                form.caMap.put(ca.getTenCa(), ca);
            }

            // Tạo controller và kết nối với form
            PhanCongCaController controller = new PhanCongCaController(form, conn);

            // Hiện form
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view, "Chọn nhân viên để phân công ca!");
        }
    }


    private void moFormXemCa() {
        String maNV = view.tfMa.getText();
        if (!maNV.isEmpty()) {
            CaDaPhanCongForm form = new CaDaPhanCongForm((JFrame) SwingUtilities.getWindowAncestor(view), maNV, conn);
            form.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view, "Chọn nhân viên để xem ca!");
        }
    }


    private NhanVien layDuLieuForm() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false); // để bắt lỗi định dạng sai

            String ma = view.tfMa.getText().trim();
            String ten = view.tfTen.getText().trim();
            String sdt = view.tfSDT.getText().trim();
            String email = view.tfEmail.getText().trim();
            double luong = Double.parseDouble(view.tfLuong.getText().trim());

            java.util.Date utilNgaySinh = sdf.parse(view.tfNgaySinh.getText().trim());
            java.util.Date utilNgayBD = sdf.parse(view.tfNgayBD.getText().trim());

            return new NhanVien(
                    ma,
                    ten,
                    new java.sql.Date(utilNgaySinh.getTime()),
                    sdt,
                    email,
                    new java.sql.Date(utilNgayBD.getTime()),
                    luong
            );
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(view, "Ngày không đúng định dạng (dd/MM/yyyy).");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Lương không hợp lệ.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Dữ liệu không hợp lệ! Vui lòng kiểm tra lại.");
        }
        return null;
    }
    
    private void clearForm() {
        view.tfMa.setText("");
        view.tfTen.setText("");
        view.tfNgaySinh.setText("");
        view.tfSDT.setText("");
        view.tfEmail.setText("");
        view.tfNgayBD.setText("");
        view.tfLuong.setText("");
        view.tfMa.setEditable(true);
        view.table.clearSelection();
    }
}
