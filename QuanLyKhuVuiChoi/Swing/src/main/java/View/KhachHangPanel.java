package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class KhachHangPanel extends JPanel {
    public JTextField tfMaKH, tfTenKH, tfMatKhau, tfSDT, tfEmail, tfDiaChi, tfGioiTinh, tfNgaySinh, tfQuocTich, tfTimKiem;
    public JButton btnThem, btnSua, btnXoa, btnTim, btnClear;
    public JTable table;
    public DefaultTableModel model;

    public KhachHangPanel() {
        /* ===== MÀU CHỦ ĐẠO ===== */
        Color blueLight  = new Color(227, 242, 253);   // #E3F2FD
        Color blueDeep   = new Color(30, 136, 229);    // #1E88E5

        setLayout(new BorderLayout(10, 10));
        setBackground(blueLight);

        /* ===== PANEL NHẬP ===== */
        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        inputPanel.setBackground(blueLight);

        TitledBorder tBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blueDeep, 1, true),
                "Thông tin Khách Hàng",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                blueDeep);
        inputPanel.setBorder(tBorder);

        // Ô nhập liệu bo tròn & padding
        JTextField[] fields = {
                tfMaKH = new JTextField(),
                tfTenKH = new JTextField(),
                tfMatKhau = new JTextField(),
                tfSDT = new JTextField(),
                tfEmail = new JTextField(),
                tfDiaChi = new JTextField(),
                tfGioiTinh = new JTextField(),
                tfNgaySinh = new JTextField(),
                tfQuocTich = new JTextField()
        };
        for (JTextField f : fields) {
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(blueDeep, 1, true),
                    BorderFactory.createEmptyBorder(1,2,1,2)));
        }

        inputPanel.add(new JLabel("Mã KH:"));                 inputPanel.add(tfMaKH);
        inputPanel.add(new JLabel("Tên KH:"));                inputPanel.add(tfTenKH);
        inputPanel.add(new JLabel("Mật khẩu:"));              inputPanel.add(tfMatKhau);
        inputPanel.add(new JLabel("SĐT:"));                   inputPanel.add(tfSDT);
        inputPanel.add(new JLabel("Email:"));                 inputPanel.add(tfEmail);
        inputPanel.add(new JLabel("Địa chỉ:"));               inputPanel.add(tfDiaChi);
        inputPanel.add(new JLabel("Giới tính:"));             inputPanel.add(tfGioiTinh);
        inputPanel.add(new JLabel("Ngày sinh (dd/mm/yyyy):")); inputPanel.add(tfNgaySinh);
        inputPanel.add(new JLabel("Quốc tịch:"));             inputPanel.add(tfQuocTich);

        add(inputPanel, BorderLayout.NORTH);
        
        for (JTextField f : fields) {
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(blueDeep, 1, true),
                    BorderFactory.createEmptyBorder(1,2,1,2)));
        }

        tfMaKH.addActionListener(e -> tfTenKH.requestFocus());
        tfTenKH.addActionListener(e -> tfMatKhau.requestFocus());
        tfMatKhau.addActionListener(e -> tfSDT.requestFocus());
        tfSDT.addActionListener(e -> tfEmail.requestFocus());
        tfEmail.addActionListener(e -> tfDiaChi.requestFocus());
        tfDiaChi.addActionListener(e -> tfGioiTinh.requestFocus());
        tfGioiTinh.addActionListener(e -> tfNgaySinh.requestFocus());
        tfNgaySinh.addActionListener(e -> tfQuocTich.requestFocus());

        /* ===== BẢNG DANH SÁCH ===== */
        model = new DefaultTableModel(new Object[]{
                "Mã KH", "Tên KH", "Mật khẩu", "SĐT",
                "Email", "Địa chỉ", "Giới tính", "Ngày sinh", "Quốc tịch"
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

        // Header style
        JTableHeader header = table.getTableHeader();
        header.setBackground(blueDeep);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blueDeep, 1, true),
                "Danh sách Khách Hàng",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                blueDeep));
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);

        /* ===== PANEL CHỨC NĂNG ===== */
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

    /* ====== Helpers ====== */

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
        // Hiệu ứng hover
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
