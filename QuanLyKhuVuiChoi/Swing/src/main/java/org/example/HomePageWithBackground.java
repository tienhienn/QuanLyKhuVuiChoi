package org.example;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class HomePageWithBackground extends JFrame {
    private JLabel lblBackground;
    private JButton btnLogin, btnRegister, btnHelp;
    private String[] images = {
            "/Image/background1.jpg",  // Đường dẫn tài nguyên (resources)
            "/Image/background2.jpg",
            "/Image/background3.jpg",
            "/Image/background4.jpg",
            "/Image/background5.jpg"
    };
    private int currentImageIndex = 0;
    private JLabel lblTitle;
    private int titleColorIndex = 0;
    private final Color[] rainbowColors = {
            Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA
    };

    public HomePageWithBackground() {
        setTitle("Trang Chủ");
        setSize(1500, 850);  // Thay đổi kích thước của form
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Tạo Panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());  // Sử dụng BorderLayout để dễ dàng chia ra các phần
        mainPanel.setOpaque(false);

        // Tạo panel tiêu đề
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);

        // Tạo khoảng cách giữa tiêu đề và phần trên của form
        titlePanel.add(Box.createRigidArea(new Dimension(0, 300)));

        // Tiêu đề trang chủ (phóng to và căn giữa)
        lblTitle = new JLabel("Chào mừng đến với VINPEARL CHKTT!");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 60));  // Phóng to tiêu đề
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(lblTitle);  // Thêm tiêu đề vào panel tiêu đề

        // Tạo panel cho các nút
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));  // Nút nằm ngang và căn giữa
        buttonsPanel.setOpaque(false);  // Không có background

        // Tạo khoảng cách giữa các nút và phần dưới của form
        buttonsPanel.add(Box.createRigidArea(new Dimension(0, 200)));

        // Đổi kích thước các nút cho phù hợp với form lớn
        btnLogin = new JButton("Đăng Nhập");
        btnLogin.setFont(new Font("Arial", Font.PLAIN, 20));
        btnLogin.setPreferredSize(new Dimension(300, 50));
        btnLogin.setBackground(new Color(0, 123, 255));  // Màu nền nút
        btnLogin.setForeground(Color.WHITE);

        btnRegister = new JButton("Thoát");
        btnRegister.setFont(new Font("Arial", Font.PLAIN, 20));
        btnRegister.setPreferredSize(new Dimension(300, 50));
        btnRegister.setBackground(new Color(40, 167, 69));  // Màu nền nút
        btnRegister.setForeground(Color.WHITE);

        btnHelp = new JButton("Hỗ Trợ");
        btnHelp.setFont(new Font("Arial", Font.PLAIN, 20));
        btnHelp.setPreferredSize(new Dimension(300, 50));
        btnHelp.setBackground(new Color(255, 193, 7));  // Màu nền nút
        btnHelp.setForeground(Color.WHITE);

        // Thêm các nút vào buttonsPanel
        buttonsPanel.add(btnLogin);
        buttonsPanel.add(btnRegister);
        buttonsPanel.add(btnHelp);

        // Tạo một JLabel để chèn ảnh nền
        lblBackground = new JLabel();
        lblBackground.setLayout(new BorderLayout());

        // Đổi kích thước ảnh theo kích thước của form (1500x850)
        ImageIcon icon = new ImageIcon(getClass().getResource(images[currentImageIndex]));
        Image img = icon.getImage(); // Lấy ảnh gốc
        Image scaledImg = img.getScaledInstance(1500, 850, Image.SCALE_SMOOTH); // Thay đổi kích thước ảnh
        icon = new ImageIcon(scaledImg); // Đặt ảnh đã thay đổi kích thước
        lblBackground.setIcon(icon);  // Đặt ảnh vào JLabel

        setContentPane(lblBackground);  // Thiết lập ảnh nền cho cửa sổ

        // Thêm Panel vào cửa sổ chính
        lblBackground.add(titlePanel, BorderLayout.NORTH);  // Thêm panel tiêu đề vào phần trên
        lblBackground.add(buttonsPanel, BorderLayout.SOUTH);  // Thêm panel nút vào phần dưới

        // Lắng nghe sự kiện cho các nút
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model.UserModel model = new Model.UserModel(); // Khởi tạo model
                View.LoginView loginView = new View.LoginView(); // Khởi tạo view
                new Controller.LoginController(model, loginView); // Tạo controller
                loginView.setVisible(true); // Mở form đăng nhập
                dispose(); // Đóng form trang chủ
            }
        });



        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Thoát chương trình
            }
        });

        btnHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Hướng dẫn sử dụng: \n1. Đăng nhập bằng tài khoản của bạn.\n2. Tìm tài khoản của bạn trong Model.\n3. Liên hệ hỗ trợ nếu gặp sự cố.");
            }
        });

        // Sử dụng Timer để thay đổi ảnh nền sau mỗi 5 giây
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex = (currentImageIndex + 1) % images.length;  // Chuyển sang ảnh tiếp theo

                // Đổi kích thước ảnh mới và đặt vào JLabel
                ImageIcon newIcon = new ImageIcon(getClass().getResource(images[currentImageIndex]));
                Image newImg = newIcon.getImage(); // Lấy ảnh gốc
                Image scaledNewImg = newImg.getScaledInstance(1500, 850, Image.SCALE_SMOOTH); // Thay đổi kích thước ảnh
                newIcon = new ImageIcon(scaledNewImg); // Đặt ảnh đã thay đổi kích thước
                lblBackground.setIcon(newIcon);  // Cập nhật ảnh nền
            }
        });
        timer.start();  // Bắt đầu timer

        // Thay đổi màu sắc của tiêu đề liên tục (7 sắc cầu vồng)
        Timer colorChangeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblTitle.setForeground(rainbowColors[titleColorIndex]);
                titleColorIndex = (titleColorIndex + 1) % rainbowColors.length;  // Chuyển sang màu tiếp theo
            }
        });
        colorChangeTimer.start();  // Bắt đầu timer thay đổi màu sắc của tiêu đề
    }

    public static void main(String[] args) {
        new HomePageWithBackground().setVisible(true);
    }
}
