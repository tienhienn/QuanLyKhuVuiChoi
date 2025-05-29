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
        JPanel header = new JPanel() {                 
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(30, 136, 229),     
                        0, getHeight(), new Color(100, 181, 246)); 
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(1200, 70));
        header.setLayout(new GridBagLayout());              
        
        ImageIcon logoIcon = null;
        java.net.URL logoURL = getClass().getResource("/Image/khuvuichoi.png");
        if (logoURL != null) {
            ImageIcon originalIcon = new ImageIcon(logoURL);
            Image scaledImage = originalIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(scaledImage);
        } else {
            System.err.println("Không tìm thấy file icon: /Image/khuvuichoi.png");
        }

        JLabel titleLabel;
        if (logoIcon != null) { 
            titleLabel = new JLabel(" Quản lý Khu Vui Chơi", logoIcon, JLabel.LEFT);
        } else {
            titleLabel = new JLabel(" Quản lý Khu Vui Chơi");
        }

        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titleLabel.setIconTextGap(10);

        header.add(titleLabel);
        add(header, BorderLayout.NORTH);

        sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(9, 1, 10, 10));
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBorder(new EmptyBorder(20, 15, 20, 15)); 
        sidebar.setBackground(new Color(230, 240, 255));
        sidebar.setBorder(BorderFactory.createTitledBorder("☰ Các chức năng"));

        String[] menuItems = {
            "Trang chủ", "Khách hàng", "Nhân viên", "Tour", "Khu", "Vé", "Dịch vụ", "Hóa đơn", "Đăng xuất"
        };

        String[] iconPaths = {
            "/Image/home.png",
            "/Image/people.png",
            "/Image/nhanvien.png",
            "/Image/tour.png",
            "/Image/khu.png",
            "/Image/ve.png",
            "/Image/dichvu.png",
            "/Image/bill.png",
            "/Image/thoat.png"
        };


    for (int i = 0; i < menuItems.length; i++) {
        String item = menuItems[i];
        String iconPath = iconPaths[i];

        ImageIcon icon = null;
        java.net.URL iconURL = getClass().getResource(iconPath);
        if (iconURL != null) {
            Image img = new ImageIcon(iconURL).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);
        } else {
            System.err.println("Không tìm thấy icon: " + iconPath);
        }

        JButton btn = new JButton(item, icon);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setHorizontalTextPosition(SwingConstants.RIGHT); 
        btn.setIconTextGap(10);  

        btn.setForeground(new Color(51, 51, 51));
        btn.setBackground(new Color(200, 230, 201));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(160, 40));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(129, 212, 250));
                btn.setForeground(Color.WHITE);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(200, 230, 201));
                btn.setForeground(new Color(51, 51, 51));
            }
        });

        sidebar.add(btn);
        btn.addActionListener(e -> switchPanel(item));
    }


        add(sidebar, BorderLayout.WEST);
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        // Kết nối CSDL
        conn = DBConnect.getConnection();

        // Trang chủ
        TrangChuPanel trangChuPanel = new TrangChuPanel();
        contentPanel.add(trangChuPanel, "Trang chủ");

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

        //Panel Hóa Đơn
        HoaDonPanel hoaDonPanel = new HoaDonPanel(conn);
        contentPanel.add(hoaDonPanel, "Hóa đơn");        

        add(contentPanel, BorderLayout.CENTER);

        // === Footer ===
        JPanel footer = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(179, 229, 252),  
                        0, getHeight(), new Color(129, 212, 250)); 
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        footer.setPreferredSize(new Dimension(1200, 50));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(100, 181, 246))); 
        footer.setOpaque(true);

        ImageIcon smileIcon = null;
        java.net.URL iconURL = getClass().getResource("/Image/smile.png"); 
        if (iconURL != null) {
            smileIcon = new ImageIcon(iconURL);
        } else {
            System.err.println("Không tìm thấy file icon: /Image/smile.png");
        }

        // Tạo label footer
        JLabel footerLabel;
        if (smileIcon != null) {
            footerLabel = new JLabel(" 2025 Quản lý Khu vui chơi.    Không vui thì chơi...!!!", smileIcon, JLabel.LEFT);
        } else {
            footerLabel = new JLabel(" 2025 Quản lý Khu vui chơi.    Không vui thì chơi...!!!");
        }

        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footerLabel.setForeground(new Color(25, 118, 210)); 
        footerLabel.setIconTextGap(8);

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
