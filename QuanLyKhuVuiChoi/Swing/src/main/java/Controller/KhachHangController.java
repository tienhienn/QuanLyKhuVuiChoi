package Controller;

import DAO.KhachHangDAO;
import Model.KhachHang;
import View.KhachHangPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class KhachHangController {
    private KhachHangPanel view;
    private KhachHangDAO dao;

    public KhachHangController(KhachHangPanel view, Connection conn) {
        this.view = view;
        this.dao = new KhachHangDAO(conn);
        loadDataToTable();

        view.btnThem.addActionListener(e -> them());
        view.btnSua.addActionListener(e -> sua());
        view.btnXoa.addActionListener(e -> xoa());
        view.btnTim.addActionListener(e -> tim());

        view.table.getSelectionModel().addListSelectionListener(e -> fillForm());
    }

    private void loadDataToTable() {
        List<KhachHang> list = dao.getAllKhachHang();
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);
        for (KhachHang kh : list) {
            model.addRow(new Object[]{
                    kh.getMaKhachHang(), kh.getTenKhachHang(), kh.getMatKhau(), kh.getSdt(),
                    kh.getEmail(), kh.getDiaChi(), kh.getGioiTinh(),
                    kh.getNgaySinh() != null ? kh.getNgaySinh().toString() : "",
                    kh.getQuocTich()
            });
        }
    }

    private void fillForm() {
        int row = view.table.getSelectedRow();
        if (row != -1) {
            view.tfMaKH.setText(view.table.getValueAt(row, 0).toString());
            view.tfTenKH.setText(view.table.getValueAt(row, 1).toString());
            view.tfMatKhau.setText(view.table.getValueAt(row, 2).toString());
            view.tfSDT.setText(view.table.getValueAt(row, 3).toString());
            view.tfEmail.setText(view.table.getValueAt(row, 4).toString());
            view.tfDiaChi.setText(view.table.getValueAt(row, 5).toString());
            view.tfGioiTinh.setText(view.table.getValueAt(row, 6).toString());
            view.tfNgaySinh.setText(view.table.getValueAt(row, 7).toString());
            view.tfQuocTich.setText(view.table.getValueAt(row, 8).toString());
            view.tfMaKH.setEditable(false);
        }
    }

    private KhachHang layDuLieuForm() {
        try {
            return new KhachHang(
                    view.tfMaKH.getText(),
                    view.tfTenKH.getText(),
                    view.tfMatKhau.getText(),
                    view.tfSDT.getText(),
                    view.tfEmail.getText(),
                    view.tfDiaChi.getText(),
                    view.tfGioiTinh.getText(),
                    Date.valueOf(view.tfNgaySinh.getText()),
                    view.tfQuocTich.getText()
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Dữ liệu không hợp lệ!");
            return null;
        }
    }

    private void them() {
        KhachHang kh = layDuLieuForm();
        if (kh == null) return;
        if (dao.getById(kh.getMaKhachHang()) != null) {
            JOptionPane.showMessageDialog(view, "Mã khách hàng đã tồn tại!");
            return;
        }
        if (dao.addKhachHang(kh)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!");
        }
    }

    private void sua() {
        KhachHang kh = layDuLieuForm();
        if (kh != null && dao.updateKhachHang(kh)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
        }
    }

    private void xoa() {
        String ma = view.tfMaKH.getText();
        int c = JOptionPane.showConfirmDialog(view, "Xoá khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (c == JOptionPane.YES_OPTION) {
            if (dao.deleteKhachHang(ma)) {
                JOptionPane.showMessageDialog(view, "Xoá thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Xoá thất bại!");
            }
        }
    }

    private void tim() {
        String keyword = view.tfTimKiem.getText();
        List<KhachHang> list = dao.timKiem(keyword);
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);
        for (KhachHang kh : list) {
            model.addRow(new Object[]{
                    kh.getMaKhachHang(), kh.getTenKhachHang(), kh.getMatKhau(), kh.getSdt(),
                    kh.getEmail(), kh.getDiaChi(), kh.getGioiTinh(),
                    kh.getNgaySinh() != null ? kh.getNgaySinh().toString() : "",
                    kh.getQuocTich()
            });
        }
    }
}