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
import javax.swing.border.TitledBorder;

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
        JPanel topMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topMenuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        topMenuPanel.setBackground(new Color(245, 245, 245));

        JButton btnQLDV = new JButton("DỊCH VỤ");
        JButton btnQLTroChoi = new JButton("TRÒ CHƠI");
        JButton btnQLSuKien = new JButton("SỰ KIỆN");
        JButton btnQLNhaHang = new JButton("NHÀ HÀNG");

        Font menuFont = new Font("Segoe UI", Font.BOLD, 13);

        Color baseBgColor = Color.WHITE;
        Color baseFgColor = Color.DARK_GRAY;
        Color hoverBgColor = new Color(51, 255, 204); 
        Color selectedBgColor = new Color(30, 144, 255);
        Color selectedFgColor = Color.WHITE;

        JButton[] buttons = {btnQLDV, btnQLTroChoi, btnQLSuKien, btnQLNhaHang};

        final JButton[] btnSelected = new JButton[1]; 

        for (JButton btn : buttons) {
            btn.setFocusPainted(false);
            btn.setFont(menuFont);
            btn.setPreferredSize(new Dimension(120, 32));
            btn.setBackground(baseBgColor);
            btn.setForeground(baseFgColor);
            btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (btn != btnSelected[0]) {
                        btn.setBackground(hoverBgColor);
                        btn.setForeground(selectedFgColor);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (btn != btnSelected[0]) {
                        btn.setBackground(baseBgColor);
                        btn.setForeground(baseFgColor);
                    }
                }
            });

            btn.addActionListener(e -> {
                if (btnSelected[0] != null) {
                    btnSelected[0].setBackground(baseBgColor);
                    btnSelected[0].setForeground(baseFgColor);
                }

                btnSelected[0] = btn;
                btnSelected[0].setBackground(selectedBgColor);
                btnSelected[0].setForeground(selectedFgColor);

                if (btn == btnQLDV) {
                    showQuanLyDichVu();
                } else if (btn == btnQLTroChoi) {
                    showQuanLyTroChoi();
                } else if (btn == btnQLSuKien) {
                    showQuanLySuKien();
                } else if (btn == btnQLNhaHang) {
                    showQuanLyNhaHang();
                }
            });

            topMenuPanel.add(btn);
        }
        btnSelected[0] = btnQLDV;
        btnSelected[0].setBackground(selectedBgColor);
        btnSelected[0].setForeground(selectedFgColor);

        add(topMenuPanel, BorderLayout.NORTH);

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
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.setBackground(new Color(0xE3F2FD));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        inputPanel.setBackground(new Color(0xE3F2FD));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0x1E88E5), 2, true),
            "Thông tin dịch vụ",
                TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            new Color(0x1E88E5)
        ));

        JLabel lblMaDV = new JLabel("Mã dịch vụ:");
        JLabel lblTenDV = new JLabel("Tên dịch vụ:");

        lblMaDV.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTenDV.setFont(new Font("Segoe UI", Font.BOLD, 16));

        txtMaDV = new JTextField(15);
        txtTenDV = new JTextField(20);
        txtMaDV.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtTenDV.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        inputPanel.add(lblMaDV);
        inputPanel.add(txtMaDV);
        inputPanel.add(lblTenDV);
        inputPanel.add(txtTenDV);

        panel.add(inputPanel, BorderLayout.NORTH);

        String[] columnNames = {"Mã dịch vụ", "Tên dịch vụ"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(220, 235, 245));
                } else {
                    c.setBackground(new Color(184, 207, 229));
                }
                return c;
            }
        };
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(51, 102, 255));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(0x64B5F6));

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(0xE3F2FD));
        tablePanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0x1E88E5), 2, true),
            "Danh sách dịch vụ",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 15),
            new Color(0x1E88E5)
        ));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(0xE3F2FD));

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");

        txtTimKiem = new JTextField(15);
        JButton btnTim = new JButton("Tìm");

        JButton[] actionButtons = {btnThem, btnSua, btnXoa, btnLamMoi, btnTim};
        Color baseColor = new Color(0x1E88E5);
        Color hoverColor = new Color(0x1565C0);

        for (JButton btn : actionButtons) {
            btn.setPreferredSize(new Dimension(90, 28)); 
            btn.setBackground(baseColor);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14)); 
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(hoverColor);
                }
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(baseColor);
                }
            });
        }

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

        String msg = controller.themDichVuSuKien(ma, ten);
        JOptionPane.showMessageDialog(this, msg);
        loadTable();
    }

    private void suaDichVu() {
        String ma = txtMaDV.getText().trim();
        String ten = txtTenDV.getText().trim();

        String msg = controller.suaDichVuSuKien(ma, ten);
        JOptionPane.showMessageDialog(this, msg);
        loadTable();
    }

    private void xoaDichVu() {
        String ma = txtMaDV.getText().trim();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ để xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            String msg = controller.xoaDichVuSuKien(ma);
            JOptionPane.showMessageDialog(this, msg);
            loadTable();
        }
    }

    private void timKiemDichVu() {
        String keyword = txtTimKiem.getText().trim();
        ArrayList<DichVu> list = controller.timKiemDichVuSuKien(keyword);

        tableModel.setRowCount(0);
        for (DichVu dv : list) {
            tableModel.addRow(new Object[]{dv.getMaDichVu(), dv.getTenDichVu()});
        }
    }
    
    private void clearForm() {
        txtMaDV.setText("");
        txtTenDV.setText("");
        txtTimKiem.setText("");
        txtMaDV.setEditable(true);
    }
}