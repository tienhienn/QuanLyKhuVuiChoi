package View;

import Controller.KhuController;
import DAO.KhuDAO;
import Model.Khu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;

public class KhuPanel extends JPanel {
    private KhuController khuController;
    private JTable table;
    private DefaultTableModel model;
    private JTextField tfMaKhu, tfTenKhu, tfMoTa;
    private JButton btnThem, btnSua, btnXoa, btnReset;

    public KhuPanel(Connection conn) {
        this.khuController = new KhuController(new KhuDAO(conn));
        initComponents();
        loadTable();
        addListeners();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Bảng hiển thị
        model = new DefaultTableModel(new String[]{"Mã khu", "Tên khu", "Mô tả"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Form nhập
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khu"));

        tfMaKhu = new JTextField();
        tfTenKhu = new JTextField();
        tfMoTa = new JTextField();

        formPanel.add(new JLabel("Mã khu:"));
        formPanel.add(tfMaKhu);
        formPanel.add(new JLabel("Tên khu:"));
        formPanel.add(tfTenKhu);
        formPanel.add(new JLabel("Mô tả:"));
        formPanel.add(tfMoTa);

        add(formPanel, BorderLayout.NORTH);

        // Các nút chức năng
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnReset = new JButton("Reset");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnReset);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadTable() {
        model.setRowCount(0);
        ArrayList<Khu> list = khuController.getAllKhu();
        for (Khu khu : list) {
            model.addRow(new Object[]{
                    khu.getMaKhu(),
                    khu.getTenKhu(),
                    khu.getMoTa()
            });
        }
    }

    private void addListeners() {
        // Click vào dòng để đổ dữ liệu vào form
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                tfMaKhu.setText(model.getValueAt(row, 0).toString());
                tfTenKhu.setText(model.getValueAt(row, 1).toString());
                tfMoTa.setText(model.getValueAt(row, 2).toString());
                tfMaKhu.setEditable(false);
            }
        });

        btnThem.addActionListener(e -> {
            String ma = tfMaKhu.getText().trim();
            String ten = tfTenKhu.getText().trim();
            String moTa = tfMoTa.getText().trim();

            if (ma.isEmpty() || ten.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin");
                return;
            }

            Khu khu = new Khu(ma, ten, moTa);
            if (khuController.themKhu(khu)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại");
            }
        });

        btnSua.addActionListener(e -> {
            String ma = tfMaKhu.getText().trim();
            String ten = tfTenKhu.getText().trim();
            String moTa = tfMoTa.getText().trim();

            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khu để sửa");
                return;
            }

            Khu khu = new Khu(ma, ten, moTa);
            if (khuController.suaKhu(khu)) {
                JOptionPane.showMessageDialog(this, "Sửa thành công");
                loadTable();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại");
            }
        });

        btnXoa.addActionListener(e -> {
            String ma = tfMaKhu.getText().trim();
            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn khu để xóa");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (khuController.xoaKhu(ma)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công");
                    loadTable();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại");
                }
            }
        });

        btnReset.addActionListener(e -> clearForm());
    }

    private void clearForm() {
        tfMaKhu.setText("");
        tfTenKhu.setText("");
        tfMoTa.setText("");
        tfMaKhu.setEditable(true);
        table.clearSelection();
    }
}
