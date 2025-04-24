package Controller;

import DAO.KhachHangDAO;
import Model.KhachHang;
import View.KhachHangPanel;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;

public class KhachHangController {
    private KhachHangPanel view;
    private KhachHangDAO dao;

    public KhachHangController(Connection conn, KhachHangPanel view) {
        this.view = view;
        this.dao = new KhachHangDAO(conn);

        loadTable();

        view.btnAdd.addActionListener(e -> addKhachHang());
        view.btnDelete.addActionListener(e -> deleteKhachHang());
        view.btnEdit.addActionListener(e -> editKhachHang());
        view.btnRefresh.addActionListener(e -> loadTable());
    }

    private void loadTable() {
        List<KhachHang> list = dao.getAllKhachHang();
        view.loadData(list);
    }

    private void addKhachHang() {
        JTextField ma = new JTextField();
        JTextField ten = new JTextField();
        JTextField mk = new JTextField();
        JTextField sdt = new JTextField();
        JTextField email = new JTextField();

        Object[] fields = {
                "Mã KH:", ma,
                "Tên KH:", ten,
                "Mật khẩu:", mk,
                "SDT:", sdt,
                "Email:", email
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Thêm khách hàng", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            KhachHang kh = new KhachHang(ma.getText(), ten.getText(), mk.getText(), sdt.getText(), email.getText());
            if (dao.addKhachHang(kh)) {
                JOptionPane.showMessageDialog(null, "Thêm thành công!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(null, "Thêm thất bại!");
            }
        }
    }

    private void deleteKhachHang() {
        int row = view.table.getSelectedRow();
        if (row >= 0) {
            String ma = view.table.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(null, "Xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (dao.deleteKhachHang(ma)) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công!");
                    loadTable();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Chọn khách hàng để xóa.");
        }
    }

    private void editKhachHang() {
        int row = view.table.getSelectedRow();
        if (row >= 0) {
            JTextField ten = new JTextField(view.table.getValueAt(row, 1).toString());
            JTextField mk = new JTextField(view.table.getValueAt(row, 2).toString());
            JTextField sdt = new JTextField(view.table.getValueAt(row, 3).toString());
            JTextField email = new JTextField(view.table.getValueAt(row, 4).toString());
            String ma = view.table.getValueAt(row, 0).toString();

            Object[] fields = {
                    "Tên KH:", ten,
                    "Mật khẩu:", mk,
                    "SDT:", sdt,
                    "Email:", email
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Cập nhật khách hàng", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                KhachHang kh = new KhachHang(ma, ten.getText(), mk.getText(), sdt.getText(), email.getText());
                if (dao.updateKhachHang(kh)) {
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
                    loadTable();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Chọn khách hàng để sửa.");
        }
    }
}
