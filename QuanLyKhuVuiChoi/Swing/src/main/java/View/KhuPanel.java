package View;

import Controller.KhuController;
import DAO.KhuDAO;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;
import java.sql.Connection;
import javax.swing.table.JTableHeader;

public class KhuPanel extends JPanel {
    private KhuController khuController;
    private JTable table;
    private DefaultTableModel model;
    private JTextField tfMaKhu, tfTenKhu, tfMoTa;
    private JButton btnThem, btnSua, btnXoa, btnReset;

    public KhuPanel(Connection conn) {
        khuController = new KhuController(new KhuDAO(conn));

        // MÀU CHỦ ĐẠO giống KhachHangPanel
        Color blueLight  = new Color(227, 242, 253);   // #E3F2FD
        Color blueDeep   = new Color(30, 136, 229);    // #1E88E5

        setLayout(new BorderLayout(10, 10));
        setBackground(blueLight);

        initComponents(blueLight, blueDeep);

        // Truyền component cho controller xử lý
        khuController.setView(table, model, tfMaKhu, tfTenKhu, tfMoTa, btnThem, btnSua, btnXoa, btnReset);
    }

    private void initComponents(Color blueLight, Color blueDeep) {
        // ===== PANEL 3 KHU CÓ HÌNH ẢNH TO HƠN =====
        JPanel khuContainer = new JPanel(new GridLayout(1, 3, 10, 10));
        khuContainer.setPreferredSize(new Dimension(0, 300)); // tăng chiều cao để ảnh đẹp
        khuContainer.setBackground(blueLight);
        khuContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        khuContainer.add(createKhuPanel("Khu Vui Chơi", "vuichoi.jpg", blueDeep));
        khuContainer.add(createKhuPanel("Khu Sự Kiện", "sukien.jpg", blueDeep));
        khuContainer.add(createKhuPanel("Khu Ẩm Thực", "amthuc.jpg", blueDeep));

        add(khuContainer, BorderLayout.NORTH);

        // ===== TRUNG TÂM: FORM VÀ BẢNG =====
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(blueLight);

        // FORM THÔNG TIN KHU
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBackground(blueLight);
        TitledBorder tBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blueDeep, 1, true),
                "Thông tin Khu",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                blueDeep);
        inputPanel.setBorder(tBorder);
        inputPanel.setPreferredSize(new Dimension(300, 120)); // rộng hơn

        tfMaKhu = new JTextField();
        tfTenKhu = new JTextField();
        tfMoTa = new JTextField();

        styleField(tfMaKhu, blueDeep);
        styleField(tfTenKhu, blueDeep);
        styleField(tfMoTa, blueDeep);

        inputPanel.add(new JLabel("Mã Khu:")); inputPanel.add(tfMaKhu);
        inputPanel.add(new JLabel("Tên Khu:")); inputPanel.add(tfTenKhu);
        inputPanel.add(new JLabel("Mô Tả:")); inputPanel.add(tfMoTa);
        
        tfMaKhu.addActionListener(e -> tfTenKhu.requestFocus());
        tfTenKhu.addActionListener(e -> tfMoTa.requestFocus());

        centerPanel.add(inputPanel, BorderLayout.WEST);

        // BẢNG DANH SÁCH
        model = new DefaultTableModel(new String[]{"Mã Khu", "Tên Khu", "Mô Tả"}, 0);
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer r, int row, int col) {
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

        // Header style
        JTableHeader header = table.getTableHeader();
        header.setBackground(blueDeep);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 150));
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blueDeep, 1, true),
                "Danh sách Khu",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                blueDeep));
        scrollPane.getViewport().setBackground(Color.WHITE);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        // ===== CÁC NÚT CHỨC NĂNG =====
        btnThem = styleButton(new JButton("Thêm"), blueDeep);
        btnSua = styleButton(new JButton("Sửa"), blueDeep);
        btnXoa = styleButton(new JButton("Xóa"), blueDeep);
        btnReset = styleButton(new JButton("Làm mới"), blueDeep);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        buttonPanel.setBackground(blueLight);
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnReset);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createKhuPanel(String title, String imageName, Color blueDeep) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blueDeep, 1, true),
                title));

        JLabel lblImage = new JLabel();
        lblImage.setHorizontalAlignment(SwingConstants.CENTER);
        lblImage.setVerticalAlignment(SwingConstants.CENTER);

        try {
            URL imageUrl = getClass().getResource("/Image/" + imageName);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                lblImage.setIcon(icon);

                panel.addComponentListener(new java.awt.event.ComponentAdapter() {
                    public void componentResized(java.awt.event.ComponentEvent evt) {
                        int width = panel.getWidth() - 20;
                        int height = panel.getHeight() - 40;
                        if (width > 0 && height > 0) {
                            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                            lblImage.setIcon(new ImageIcon(scaledImage));
                        }
                    }
                });
            } else {
                lblImage.setText("Ảnh không tồn tại");
            }
        } catch (Exception e) {
            lblImage.setText("Lỗi tải ảnh");
        }

        panel.add(lblImage, BorderLayout.CENTER);
        return panel;
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
