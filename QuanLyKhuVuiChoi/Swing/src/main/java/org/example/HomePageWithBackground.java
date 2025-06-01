package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class HomePageWithBackground extends JFrame {
    private JLabel lblBackground;
    private JButton btnLogin, btnRegister, btnHelp;
    private String[] images = {
            "/Image/background1.jpg",
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
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Ảnh nền ban đầu
        lblBackground = new JLabel();
        lblBackground.setLayout(new BorderLayout());
        updateBackgroundImage();
        setContentPane(lblBackground);

        // Panel chính chứa tiêu đề và nút
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);

        // Tiêu đề
        Font titleFont = new Font("Segoe UI Black", Font.BOLD, 64);
        Title3DLabel title3DLabel = new Title3DLabel("Chào mừng đến với QL KHU VUI CHƠI!", titleFont, rainbowColors);

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.add(title3DLabel);

        // Nút
        btnLogin = createStyledButton("Đăng Nhập", new Color(0, 123, 255));
        btnRegister = createStyledButton("Thoát", new Color(220, 53, 69));
        btnHelp = createStyledButton("Hỗ Trợ", new Color(255, 193, 7));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(btnLogin);
        buttonsPanel.add(btnRegister);
        buttonsPanel.add(btnHelp);

        // Canh chỉnh vị trí bằng khoảng trống
        mainPanel.add(Box.createVerticalStrut(80)); // Cách trên cùng
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(300)); // Khoảng cách giữa tiêu đề và nút
        mainPanel.add(buttonsPanel);
        mainPanel.add(Box.createVerticalStrut(50)); // Cách dưới

        lblBackground.add(mainPanel, BorderLayout.CENTER);

        // Sự kiện nút
        btnLogin.addActionListener(e -> {
            Model.UserModel model = new Model.UserModel();
            View.LoginView loginView = new View.LoginView();
            new Controller.LoginController(model, loginView);
            loginView.setVisible(true);
            dispose();
        });
        btnRegister.addActionListener(e -> System.exit(0));

        btnHelp.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Hướng dẫn sử dụng: \n1. Đăng nhập bằng tài khoản của bạn.\n2. Tìm tài khoản của bạn trong Model.\n3. Liên hệ hỗ trợ nếu gặp sự cố."));

        // Timer đổi ảnh nền
        Timer bgTimer = new Timer(5000, e -> {
            currentImageIndex = (currentImageIndex + 1) % images.length;
            updateBackgroundImage();
        });
        bgTimer.start();

        // Timer đổi màu tiêu đề
        Timer colorChangeTimer = new Timer(1000, e -> title3DLabel.nextColor());
        colorChangeTimer.start();
    }
    
    class Title3DLabel extends JComponent {
        private String text;
        private Font font;
        private Color[] colors;
        private int colorIndex = 0;

        public Title3DLabel(String text, Font font, Color[] colors) {
            this.text = text;
            this.font = font;
            this.colors = colors;
            setPreferredSize(new Dimension(1400, 100));
            setOpaque(false);
        }

        public void nextColor() {
            colorIndex = (colorIndex + 1) % colors.length;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setFont(font);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            int x = 50;
            int y = 70;

            // Vẽ bóng 3D bằng nhiều lớp lệch nhau
            for (int i = 5; i > 0; i--) {
                g2.setColor(new Color(0, 0, 0, 30 + i * 20)); // các lớp bóng mờ dần
                g2.drawString(text, x + i, y + i);
            }

            // Vẽ chữ chính với màu nổi bật
            g2.setColor(colors[colorIndex]);
            g2.drawString(text, x, y);

            g2.dispose();
        }
    }

    // Cập nhật ảnh nền
    private void updateBackgroundImage() {
        ImageIcon icon = new ImageIcon(getClass().getResource(images[currentImageIndex]));
        Image img = icon.getImage().getScaledInstance(1500, 850, Image.SCALE_SMOOTH);
        lblBackground.setIcon(new ImageIcon(img));
    }

    // Tạo nút có style đẹp
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setPreferredSize(new Dimension(300, 55));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return button;
    }

    public static void main(String[] args) {
        new HomePageWithBackground().setVisible(true);
    }
}