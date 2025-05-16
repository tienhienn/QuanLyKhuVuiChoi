package View;

import Controller.*;
import org.example.HomePageWithBackground;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;

public class MainAppForm extends JFrame {
    private JPanel sidebar, contentPanel;
    private CardLayout cardLayout;
    private Connection conn;

    public MainAppForm() {
        setTitle("Hệ thống quản lý Khu vui chơi");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Header ===
        JPanel header = new JPanel();
        header.setBackground(new Color(33, 150, 243)); // xanh dương đậm
        header.setPreferredSize(new Dimension(1200, 70));
        header.setLayout(new FlowLayout(FlowLayout.LEFT, 30, 20));

        JLabel titleLabel = new JLabel("Quản lý Khu Vui Chơi");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.add(titleLabel);
        add(header, BorderLayout.NORTH);

        // === Sidebar bên trái ===
        sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(9, 1, 10, 10));
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15));  // Padding
        sidebar.setBackground(new Color(230, 240, 255));
        sidebar.setBorder(BorderFactory.createTitledBorder("☰ Các chức năng"));

        String[] menuItems = {
                "Trang chủ", "Khách hàng", "Nhân viên", "Tour", "Khu", "Vé", "Dịch vụ", "Hóa đơn", "Đăng xuất"
        };

        for (String item : menuItems) {
            JButton btn = new JButton(item);

            btn.setForeground(new Color(51, 51, 51));                      // Màu chữ xám đậm
            btn.setBackground(new Color(200, 230, 201));                    // Nền nút - Xanh lá pastel nhẹ
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            btn.setFocusPainted(false);
            btn.setBorder(new EmptyBorder(10, 20, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setAlignmentX(Component.LEFT_ALIGNMENT); // Không giãn full width
            btn.setMaximumSize(new Dimension(160, 40));  // Chiều ngang cố định 160px
            btn.setHorizontalAlignment(SwingConstants.LEFT);              // Canh trái

            // Hover đổi màu nhẹ
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(129, 212, 250));  // Hover - Xanh dương nhạt
                    btn.setForeground(new Color(255, 255, 255));  // Màu chữ trắng khi hover
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(200, 230, 201));  // Nền xanh lá pastel khi không hover
                    btn.setForeground(new Color(51, 51, 51));     // Màu chữ xám đậm
                }
            });

            sidebar.add(btn);
            btn.addActionListener(e -> switchPanel(item));
        }

        add(sidebar, BorderLayout.WEST);

        // === Panel nội dung bên phải ===
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Kết nối CSDL
        conn = DBConnect.getConnection();

        // Trang chủ
        contentPanel.add(createLabelPanel("Trang chủ"), "Trang chủ");

        // Panel Khách hàng
        KhachHangPanel khachHangPanel = new KhachHangPanel();
        contentPanel.add(khachHangPanel, "Khách hàng");
        new KhachHangController(khachHangPanel, conn);

        // Panel Nhân Viên
        NhanVienPanel nhanVienPanel = new NhanVienPanel();
        contentPanel.add(nhanVienPanel, "Nhân viên");
        new NhanVienController(nhanVienPanel, conn);

        // Panel Tour
        TourPanel tourPanel = new TourPanel();
        contentPanel.add(tourPanel, "Tour");
        new TourController(tourPanel, conn);

        // Panel Khu
        KhuPanel khuPanel = new KhuPanel(conn);
        contentPanel.add(khuPanel, "Khu");

        // Panel Vé
        VeTourPanel veTourPanel = new VeTourPanel(conn);    
        contentPanel.add(veTourPanel, "Vé");
        new VeTourController(veTourPanel, conn);

        // Panel Dịch vụ
        DichVuPanel dichVuPanel = new DichVuPanel(conn);
        contentPanel.add(dichVuPanel, "Dịch vụ");
//        cardLayout = new CardLayout();
//        contentPanel = new JPanel(cardLayout);
        

        //Panel Hóa Đơn
        contentPanel.add(createLabelPanel("Quản lý Hóa đơn"), "Hóa đơn");

        add(contentPanel, BorderLayout.CENTER);

        // === Footer ===
        JPanel footer = new JPanel();
        footer.setBackground(new Color(245, 245, 245));
        footer.setPreferredSize(new Dimension(1200, 40));
        JLabel footerLabel = new JLabel("© 2025 Công ty Quản lý Khu Vui Chơi. All rights reserved.");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footerLabel.setForeground(new Color(100, 100, 100));
        footer.add(footerLabel);
        add(footer, BorderLayout.SOUTH);
    }

    private JPanel createLabelPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 30));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private void switchPanel(String name) {
        if (name.equals("Đăng xuất")) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn đăng xuất?", "Xác nhận",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new HomePageWithBackground().setVisible(true);
                dispose();
            }
        } else {
            cardLayout.show(contentPanel, name);
        }
    }
}
