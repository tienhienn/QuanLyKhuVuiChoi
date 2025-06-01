package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LoginView extends JFrame {
    public JTextField txtUsername;
    public JPasswordField txtPassword;
    public JButton btnLogin;
    public JLabel lblMessage;
    private Color primaryColor = new Color(41, 128, 185);
    private Color hoverColor = new Color(52, 152, 219);

    public LoginView() {
        setTitle("Đăng Nhập - Hệ Thống Quản Lý Khu Vui Chơi");
        setSize(500, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true); // Loại bỏ window decoration
        setShape(new RoundRectangle2D.Double(0, 0, 500, 450, 20, 20)); // Bo tròn cửa sổ

        // Panel chính với gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(41, 128, 185), 
                                                   0, getHeight(), new Color(44, 62, 80));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Close button
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        titleBar.setOpaque(false);
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBorder(null);
        closeButton.setContentAreaFilled(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> System.exit(0));
        closeButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(new Color(231, 76, 60));
            }
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(Color.WHITE);
            }
        });
        titleBar.add(closeButton);
        mainPanel.add(titleBar);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));

        // Logo
        try {
            ImageIcon avatarIcon = new ImageIcon(getClass().getResource("/Image/login.png"));
            Image img = avatarIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            avatarIcon = new ImageIcon(img);
            JLabel avatarLabel = new JLabel(avatarIcon);
            avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            headerPanel.add(avatarLabel);
        } catch (Exception e) {
            JLabel avatarLabel = new JLabel("User", SwingConstants.CENTER);
            avatarLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
            avatarLabel.setForeground(Color.WHITE);
            avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            headerPanel.add(avatarLabel);
        }

        // Welcome text
        JLabel lblTitle = new JLabel("Chào mừng trở lại!", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(Box.createVerticalStrut(20));
        headerPanel.add(lblTitle);

        JLabel lblSubtitle = new JLabel("Đăng nhập để tiếp tục", SwingConstants.CENTER);
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(236, 240, 241));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(lblSubtitle);

        mainPanel.add(headerPanel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Username field
        JPanel userPanel = new JPanel(new BorderLayout(10, 0));
        userPanel.setOpaque(false);
        userPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        txtUsername = createStyledTextField("Tên đăng nhập");
        userPanel.add(txtUsername, BorderLayout.CENTER);
        formPanel.add(userPanel);

        // Password field with show/hide button
        JPanel passPanel = new JPanel(new BorderLayout(10, 0));
        passPanel.setOpaque(false);
        passPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        txtPassword = createStyledPasswordField("Mật khẩu");
        passPanel.add(txtPassword, BorderLayout.CENTER);

        // Show/Hide password button
        JToggleButton btnShowPassword = createPasswordToggleButton();
        passPanel.add(btnShowPassword, BorderLayout.EAST);
        
        formPanel.add(passPanel);

        // Login button
        btnLogin = createStyledButton("ĐĂNG NHẬP");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(btnLogin);

        // Message label
        lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMessage.setForeground(new Color(231, 76, 60));
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(lblMessage);

        mainPanel.add(formPanel);
        add(mainPanel);

        // Make window draggable
        addWindowDragListener();
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !hasFocus()) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(189, 195, 199));
                    g2d.setFont(getFont().deriveFont(Font.PLAIN));
                    g2d.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
                }
            }
        };
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.WHITE);
        field.setBackground(new Color(44, 62, 80));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        field.setCaretColor(Color.WHITE);
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, primaryColor),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(189, 195, 199)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
            }
        });
        
        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getPassword().length == 0 && !hasFocus()) {
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setColor(new Color(189, 195, 199));
                    g2d.setFont(getFont().deriveFont(Font.PLAIN));
                    g2d.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
                }
            }
        };
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.WHITE);
        field.setBackground(new Color(44, 62, 80));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        field.setCaretColor(Color.WHITE);
        field.setEchoChar('•');

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, primaryColor),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(189, 195, 199)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
            }
        });

        return field;
    }

    private JToggleButton createPasswordToggleButton() {
        ImageIcon eyeOpenIconRaw = new ImageIcon(getClass().getResource("/Image/viewmo.png"));
        ImageIcon eyeClosedIconRaw = new ImageIcon(getClass().getResource("/Image/viewdong.png"));
        
        int iconSize = 20;
        ImageIcon eyeOpenIcon = resizeIcon(eyeOpenIconRaw, iconSize, iconSize);
        ImageIcon eyeClosedIcon = resizeIcon(eyeClosedIconRaw, iconSize, iconSize);

        JToggleButton btn = new JToggleButton(eyeClosedIcon);
        btn.setPreferredSize(new Dimension(iconSize + 10, iconSize + 10));
        btn.setBackground(new Color(44, 62, 80));
        btn.setBorder(null);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addActionListener(e -> {
            if (btn.isSelected()) {
                txtPassword.setEchoChar((char) 0);
                btn.setIcon(eyeOpenIcon);
            } else {
                txtPassword.setEchoChar('•');
                btn.setIcon(eyeClosedIcon);
            }
        });

        return btn;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(primaryColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(hoverColor);
                } else {
                    g2.setColor(primaryColor);
                }
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();

                super.paintComponent(g);
            }
        };
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 40));

        return button;
    }

    private void addWindowDragListener() {
        Point offset = new Point();
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                offset.setLocation(e.getX(), e.getY());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = getLocation();
                setLocation(p.x + e.getX() - offset.x, p.y + e.getY() - offset.y);
            }
        });
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    public void showMessage(String message) {
        lblMessage.setText(message);
    }
    
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }
}
