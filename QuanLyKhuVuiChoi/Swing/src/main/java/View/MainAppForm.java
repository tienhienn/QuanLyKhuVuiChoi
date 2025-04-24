package View;

import Controller.DBConnect;
import Controller.KhachHangController;
import Controller.NhanVienController;
import org.example.HomePageWithBackground;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;  // Sửa import đúng!

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

        // === Sidebar bên trái ===
        sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 1, 10, 10));  // 8 dòng, 10px khoảng cách
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(45, 62, 80));  // Màu xanh đậm hiện đại

        String[] menuItems = {
                "Trang chủ", "Khách hàng", "Nhân viên", "Tour", "Vé", "Dịch vụ", "Hóa đơn", "Đăng xuất"
        };

        for (String item : menuItems) {
            JButton btn = new JButton(item);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(52, 73, 94));
            btn.setFocusPainted(false);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            sidebar.add(btn);

            btn.addActionListener(e -> switchPanel(item));
        }

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
        new KhachHangController(conn, khachHangPanel); // Gắn controller

        // Panel Nhân Viên
        NhanVienPanel nhanVienPanel = new NhanVienPanel();
        contentPanel.add(nhanVienPanel, "Nhân viên");
        new NhanVienController(nhanVienPanel, conn);

        // Các panel giả lập khác
        //contentPanel.add(createLabelPanel("Quản lý Nhân viên"), "Nhân viên");
        contentPanel.add(createLabelPanel("Quản lý Tour"), "Tour");
        contentPanel.add(createLabelPanel("Quản lý Vé"), "Vé");
        contentPanel.add(createLabelPanel("Quản lý Dịch vụ"), "Dịch vụ");
        contentPanel.add(createLabelPanel("Quản lý Hóa đơn"), "Hóa đơn");

        add(sidebar, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createLabelPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 30));
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
