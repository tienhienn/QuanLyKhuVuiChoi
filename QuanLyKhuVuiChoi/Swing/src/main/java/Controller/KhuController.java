package Controller;

import Model.Khu;
import DAO.KhuDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class KhuController {
    private KhuDAO khuDAO;
    private DefaultTableModel model;
    private JTable table;
    private JTextField tfMaKhu, tfTenKhu, tfMoTa;
    private JButton btnThem, btnSua, btnXoa, btnReset;

    public KhuController(KhuDAO khuDAO) {
        this.khuDAO = khuDAO;
    }

    public void setView(JTable table, DefaultTableModel model,
                        JTextField tfMaKhu, JTextField tfTenKhu, JTextField tfMoTa,
                        JButton btnThem, JButton btnSua, JButton btnXoa, JButton btnReset) {
        this.table = table;
        this.model = model;
        this.tfMaKhu = tfMaKhu;
        this.tfTenKhu = tfTenKhu;
        this.tfMoTa = tfMoTa;
        this.btnThem = btnThem;
        this.btnSua = btnSua;
        this.btnXoa = btnXoa;
        this.btnReset = btnReset;

        loadTable();
        addListeners();
    }

    public void loadTable() {
        model.setRowCount(0);
        ArrayList<Khu> list = getAllKhu();
        for (Khu khu : list) {
            model.addRow(new Object[]{
                khu.getMaKhu(),
                khu.getTenKhu(),
                khu.getMoTa()
            });
        }
    }

    public ArrayList<Khu> getAllKhu() {
        return khuDAO.getAllKhu();
    }

    public boolean themKhu(Khu khu) {
        return khuDAO.insertKhu(khu);
    }

    public boolean suaKhu(Khu khu) {
        return khuDAO.updateKhu(khu);
    }

    public boolean xoaKhu(String maKhu) {
        return khuDAO.deleteKhu(maKhu);
    }

    private void clearForm() {
        tfMaKhu.setText("");
        tfTenKhu.setText("");
        tfMoTa.setText("");
        tfMaKhu.setEditable(true);
        table.clearSelection();
    }

    private void addListeners() {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    tfMaKhu.setText(model.getValueAt(row, 0).toString());
                    tfTenKhu.setText(model.getValueAt(row, 1).toString());
                    tfMoTa.setText(model.getValueAt(row, 2).toString());
                    tfMaKhu.setEditable(false);
                }
            }
        });

        btnThem.addActionListener(e -> {
            String ma = tfMaKhu.getText().trim();
            String ten = tfTenKhu.getText().trim();
            String moTa = tfMoTa.getText().trim();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đủ thông tin");
                return;
            }

            Khu khu = new Khu(ma, ten, moTa);
            if (themKhu(khu)) {
                JOptionPane.showMessageDialog(null, "Thêm thành công");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(null, "Thêm thất bại");
            }
        });

        btnSua.addActionListener(e -> {
            String ma = tfMaKhu.getText().trim();
            String ten = tfTenKhu.getText().trim();
            String moTa = tfMoTa.getText().trim();

            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn khu để sửa");
                return;
            }

            Khu khu = new Khu(ma, ten, moTa);
            if (suaKhu(khu)) {
                JOptionPane.showMessageDialog(null, "Sửa thành công");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(null, "Sửa thất bại");
            }
        });

        btnXoa.addActionListener(e -> {
            String ma = tfMaKhu.getText().trim();
            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn khu để xóa");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (xoaKhu(ma)) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa thất bại");
                }
            }
        });

        btnReset.addActionListener(e -> clearForm());
    }
}