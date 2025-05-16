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
            view.tfMa.setText(view.table.getValueAt(row, 0).toString());
            view.tfTen.setText(view.table.getValueAt(row, 1).toString());
            view.tfNgaySinh.setText(view.table.getValueAt(row, 2).toString());
            view.tfSDT.setText(view.table.getValueAt(row, 3).toString());
            view.tfEmail.setText(view.table.getValueAt(row, 4).toString());
            view.tfNgayBD.setText(view.table.getValueAt(row, 5).toString());
            view.tfLuong.setText(view.table.getValueAt(row, 6).toString());
            view.tfMa.setEditable(false);
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
            return new NhanVien(
                    view.tfMa.getText(),
                    view.tfTen.getText(),
                    Date.valueOf(view.tfNgaySinh.getText()),
                    view.tfSDT.getText(),
                    view.tfEmail.getText(),
                    Date.valueOf(view.tfNgayBD.getText()),
                    Double.parseDouble(view.tfLuong.getText())
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Dữ liệu không hợp lệ! Vui lòng kiểm tra lại.");
            return null;
        }
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
