package View;

import Model.Tour;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.Connection;

public class VeTourPanel extends JPanel {
    public JTextField tfMaVeTour, tfNguoiLon, tfTreEm, tfNguoiGia, tfTimKiem;
    public JComboBox<Tour> cbTour;
    public JComboBox<String> cbMaDat;
    public JButton btnThem, btnSua, btnXoa, btnTim, btnClear;
    public JTable table;
    public DefaultTableModel model;

    public Connection conn;

    public VeTourPanel(Connection conn) {
        this();
        this.conn = conn;
    }

    public VeTourPanel() {
        Color blueLight = new Color(227, 242, 253);
        Color blueDeep = new Color(30, 136, 229);

        setLayout(new BorderLayout(10, 10));
        setBackground(blueLight);

        // === PANEL NHẬP THÔNG TIN ===
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBackground(blueLight);
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blueDeep, 1, true),
                "Thông tin Vé Tour",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                blueDeep));

        tfMaVeTour = new JTextField(); styleField(tfMaVeTour, blueDeep);
        cbMaDat = new JComboBox<>();
        cbTour = new JComboBox<>();
        tfNguoiLon = new JTextField(); styleField(tfNguoiLon, blueDeep);
        tfTreEm = new JTextField();    styleField(tfTreEm, blueDeep);
        tfNguoiGia = new JTextField(); styleField(tfNguoiGia, blueDeep);

        inputPanel.add(new JLabel("Mã Vé Tour:"));   inputPanel.add(tfMaVeTour);
        inputPanel.add(new JLabel("Mã Đặt Vé:"));    inputPanel.add(cbMaDat);
        inputPanel.add(new JLabel("Tour:"));         inputPanel.add(cbTour);
        inputPanel.add(new JLabel("Người Lớn:"));    inputPanel.add(tfNguoiLon);
        inputPanel.add(new JLabel("Trẻ Em:"));       inputPanel.add(tfTreEm);
        inputPanel.add(new JLabel("Người Già:"));    inputPanel.add(tfNguoiGia);

        add(inputPanel, BorderLayout.NORTH);

        // === BẢNG DANH SÁCH ===
        model = new DefaultTableModel(new Object[]{
                "Mã Vé Tour", "Mã Đặt Vé", "Mã Tour", "Người Lớn", "Trẻ Em", "Người Già"
        }, 0);

        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : blueLight);
                } else {
                    c.setBackground(new Color(144, 202, 249));
                }
                return c;
            }
        };
        table.setFillsViewportHeight(true);
        table.setRowHeight(24);
        table.setGridColor(new Color(200, 200, 200));

        JTableHeader header = table.getTableHeader();
        header.setBackground(blueDeep);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blueDeep, 1, true),
                "Danh sách Vé Tour",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                blueDeep));
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);

        // === PANEL ĐIỀU KHIỂN ===
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(blueLight);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(blueLight);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(blueLight);

        tfTimKiem = new JTextField(20);
        styleField(tfTimKiem, blueDeep);

        btnTim   = styleButton(new JButton("Tìm"),     blueDeep);
        btnThem  = styleButton(new JButton("Thêm"),    blueDeep);
        btnSua   = styleButton(new JButton("Sửa"),     blueDeep);
        btnXoa   = styleButton(new JButton("Xóa"),     blueDeep);
        btnClear = styleButton(new JButton("Làm mới"), blueDeep);

        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(tfTimKiem);
        searchPanel.add(btnTim);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnClear);

        controlPanel.add(searchPanel, BorderLayout.WEST);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        add(controlPanel, BorderLayout.SOUTH);
    }

    private void styleField(JTextField field, Color borderCol) {
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderCol, 1, true),
                BorderFactory.createEmptyBorder(4, 6, 4, 6)));
        field.setPreferredSize(new Dimension(200, 26));
    }

    private JButton styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(100, 181, 246));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(bg);
            }
        });
        return btn;
    }
}
