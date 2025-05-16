package View;

import Controller.DichVuController;
import Model.DichVu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;

public class DichVuPanel extends JPanel {
    private Connection conn;
    private DichVuController controller;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtMaDV, txtTenDV, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private JPanel contentPanel;

    public DichVuPanel(Connection conn) {
        this.conn = conn;
        this.controller = new DichVuController(conn);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        // === WEST: Menu quản lý ===
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createTitledBorder("**********"));

        JButton btnQLDV = new JButton("DỊCH VỤ");
        JButton btnQLTroChoi = new JButton("Quản lý trò chơi");
        JButton btnQLSuKien = new JButton("Quản lý sự kiện");
        JButton btnQLNhaHang = new JButton("Quản lý nhà hàng");

        btnQLDV.setForeground(Color.RED);
        btnQLTroChoi.setForeground(new Color(0, 128, 0)); 
        btnQLSuKien.setForeground(Color.BLUE);
        btnQLNhaHang.setForeground(new Color(255, 140, 0));

        Dimension btnSize = new Dimension(160, 30);
        btnQLDV.setMaximumSize(btnSize);
        btnQLTroChoi.setMaximumSize(btnSize);
        btnQLSuKien.setMaximumSize(btnSize);
        btnQLNhaHang.setMaximumSize(btnSize);

        btnQLDV.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnQLTroChoi.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnQLSuKien.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnQLNhaHang.setAlignmentX(Component.LEFT_ALIGNMENT);

        menuPanel.add(btnQLDV);
        menuPanel.add(Box.createVerticalStrut(16));
        menuPanel.add(btnQLTroChoi);
        menuPanel.add(Box.createVerticalStrut(15));
        menuPanel.add(btnQLSuKien);
        menuPanel.add(Box.createVerticalStrut(16));
        menuPanel.add(btnQLNhaHang);
        menuPanel.add(Box.createVerticalGlue()); 
        
        btnQLDV.addActionListener(e -> showQuanLyDichVu());
        btnQLTroChoi.addActionListener(e -> showQuanLyTroChoi());
        btnQLSuKien.addActionListener(e -> showQuanLySuKien());
        btnQLNhaHang.addActionListener(e -> showQuanLyNhaHang());

        add(menuPanel, BorderLayout.WEST);

        contentPanel = new JPanel(new BorderLayout());
        add(contentPanel, BorderLayout.CENTER);

        showQuanLyDichVu();
    }

    private void showQuanLyDichVu() {
        contentPanel.removeAll();
        contentPanel.add(createDichVuPanel(), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        loadTable(); 
    }   


    private void showQuanLyTroChoi() {
        contentPanel.removeAll();
        contentPanel.add(new TroChoiForm(conn), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showQuanLySuKien() {
        contentPanel.removeAll();
        contentPanel.add(new SuKienForm(conn), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showQuanLyNhaHang() {
        contentPanel.removeAll();
        contentPanel.add(new NhaHangForm(conn), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createDichVuPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin dịch vụ"));

        txtMaDV = new JTextField();
        txtTenDV = new JTextField();

        inputPanel.add(new JLabel("Mã dịch vụ:"));
        inputPanel.add(txtMaDV);
        inputPanel.add(new JLabel("Tên dịch vụ:"));
        inputPanel.add(txtTenDV);

        panel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã dịch vụ", "Tên dịch vụ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");
        txtTimKiem = new JTextField(15);
        JButton btnTim = new JButton("Tìm");

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnLamMoi);
        buttonPanel.add(new JLabel("Tìm kiếm:"));
        buttonPanel.add(txtTimKiem);
        buttonPanel.add(btnTim);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        btnThem.addActionListener(e -> themDichVu());
        btnSua.addActionListener(e -> suaDichVu());
        btnXoa.addActionListener(e -> xoaDichVu());
        btnLamMoi.addActionListener(e -> loadTable());
        btnTim.addActionListener(e -> timKiemDichVu());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaDV.setText(tableModel.getValueAt(row, 0).toString());
                    txtTenDV.setText(tableModel.getValueAt(row, 1).toString());
                    txtMaDV.setEditable(false);
                }
            }
        });

        return panel;
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        ArrayList<DichVu> list = controller.layTatCaDichVu();
        for (DichVu dv : list) {
            tableModel.addRow(new Object[]{dv.getMaDichVu(), dv.getTenDichVu()});
        }
        clearForm();
    }

    private void themDichVu() {
        String ma = txtMaDV.getText().trim();
        String ten = txtTenDV.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        DichVu dv = new DichVu(ma, ten);
        if (controller.themDichVu(dv)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
        }
    }

    private void suaDichVu() {
        String ma = txtMaDV.getText().trim();
        String ten = txtTenDV.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ để sửa!");
            return;
        }

        DichVu dv = new DichVu(ma, ten);
        if (controller.capNhatDichVu(dv)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadTable();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }

    private void xoaDichVu() {
        String ma = txtMaDV.getText().trim();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.xoaDichVu(ma)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }

    private void timKiemDichVu() {
        String keyword = txtTimKiem.getText().trim();

        tableModel.setRowCount(0);

        if (keyword.isEmpty()) {
            for (DichVu dv : controller.layTatCaDichVu()) {
                tableModel.addRow(new Object[]{dv.getMaDichVu(), dv.getTenDichVu()});
            }
        } else {
            for (DichVu dv : controller.layTatCaDichVu()) {
                if (dv.getMaDichVu().contains(keyword) || dv.getTenDichVu().toLowerCase().contains(keyword.toLowerCase())) {
                    tableModel.addRow(new Object[]{dv.getMaDichVu(), dv.getTenDichVu()});
                }
            }
        }
    }
    
    private void clearForm() {
        txtMaDV.setText("");
        txtTenDV.setText("");
        txtTimKiem.setText("");
        txtMaDV.setEditable(true);
    }
}