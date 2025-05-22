package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TourPanel extends JPanel {

    public JTextField tfMaTour, tfTenTour, tfMieuTa, tfGiaTour, tfNgayBatDau, tfNgayKetThuc, tfSoLuongMax, tfTimKiem;
    public JButton btnThem, btnSua, btnXoa, btnTim, btnClear;
    public JTable table;
    public DefaultTableModel model;

    public TourPanel() {
        Color blueLight  = new Color(227, 242, 253);   // #E3F2FD
        Color blueDeep   = new Color(30, 136, 229);    // #1E88E5

        setLayout(new BorderLayout(10, 10));
        setBackground(blueLight);

        // ===== PANEL NHẬP =====
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBackground(blueLight);

        TitledBorder tBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blueDeep, 1, true),
                "Thông tin Tour",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                blueDeep);
        inputPanel.setBorder(tBorder);

        JTextField[] fields = {
                tfMaTour = new JTextField(),
                tfTenTour = new JTextField(),
                tfMieuTa = new JTextField(),
                tfGiaTour = new JTextField(),
                tfNgayBatDau = new JTextField(),
                tfNgayKetThuc = new JTextField(),
                tfSoLuongMax = new JTextField()
        };
        for (JTextField f : fields) {
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(blueDeep, 1, true),
                    BorderFactory.createEmptyBorder(1,2,1,2)));
        }

        inputPanel.add(new JLabel("Mã Tour:"));       inputPanel.add(tfMaTour);
        inputPanel.add(new JLabel("Tên Tour:"));      inputPanel.add(tfTenTour);
        inputPanel.add(new JLabel("Mô Tả:"));         inputPanel.add(tfMieuTa);
        inputPanel.add(new JLabel("Giá Tour:"));      inputPanel.add(tfGiaTour);
        inputPanel.add(new JLabel("Ngày Bắt Đầu (dd/mm/yyyy):")); inputPanel.add(tfNgayBatDau);
        inputPanel.add(new JLabel("Ngày Kết Thúc (dd/mm/yyyy):")); inputPanel.add(tfNgayKetThuc);
        inputPanel.add(new JLabel("Số Lượng Max:")); inputPanel.add(tfSoLuongMax);

        add(inputPanel, BorderLayout.NORTH);
        
        for (JTextField f : fields) {
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(blueDeep, 1, true),
                    BorderFactory.createEmptyBorder(1,2,1,2)));
        }

        tfMaTour.addActionListener(e -> tfTenTour.requestFocus());
        tfTenTour.addActionListener(e -> tfMieuTa.requestFocus());
        tfMieuTa.addActionListener(e -> tfGiaTour.requestFocus());
        tfGiaTour.addActionListener(e -> tfNgayBatDau.requestFocus());
        tfNgayBatDau.addActionListener(e -> tfNgayKetThuc.requestFocus());
        tfNgayKetThuc.addActionListener(e -> tfSoLuongMax.requestFocus());

        // ===== BẢNG DANH SÁCH =====
        model = new DefaultTableModel(new Object[]{
                "Mã Tour", "Tên Tour", "Mô Tả", "Giá Tour", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Số Lượng Max"
        }, 0);

        table = new JTable(model) {
            // Tô màu các hàng so le
            @Override
            public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : blueLight);
                } else {
                    c.setBackground(new Color(144, 202, 249)); // khi chọn
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
                "Danh sách Tour",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                blueDeep));
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);

        // ===== PANEL CHỨC NĂNG =====
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(blueLight);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(blueLight);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(blueLight);

        tfTimKiem = new JTextField(20);
        styleField(tfTimKiem, blueDeep);

        btnTim   = styleButton(new JButton("Tìm"),   blueDeep);
        btnThem  = styleButton(new JButton("Thêm"),  blueDeep);
        btnSua   = styleButton(new JButton("Sửa"),   blueDeep);
        btnXoa   = styleButton(new JButton("Xóa"),   blueDeep);
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
