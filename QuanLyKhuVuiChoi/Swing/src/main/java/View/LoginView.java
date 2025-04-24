package View;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    public JTextField txtUsername;
    public JPasswordField txtPassword;
    public JButton btnLogin;
    public JLabel lblMessage;

    public LoginView() {
        setTitle("User Login");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Tổng thể
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // ==== Header với avatar + tên ====
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel avatarCircle = new JLabel("👤", SwingConstants.CENTER); // Dùng emoji thay vì ảnh
        avatarCircle.setFont(new Font("Segoe UI", Font.PLAIN, 50));
        avatarCircle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblUser = new JLabel("Login as Guest", SwingConstants.CENTER);
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblUser.setForeground(new Color(100, 100, 100));
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(avatarCircle);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(lblUser);

        // ==== Tiêu đề ====
        JLabel lblTitle = new JLabel("Welcome Back!", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(34, 87, 122));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // ==== Form ====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

// Custom border with rounded corners
        Border roundedBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        );

// Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBorder(roundedBorder);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(lblUsername, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(txtUsername, gbc);

// Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBorder(roundedBorder);

// Show/hide password button
        JToggleButton btnShowPassword = new JToggleButton("👁");
        btnShowPassword.setFocusPainted(false);
        btnShowPassword.setMargin(new Insets(2, 6, 2, 6));
        btnShowPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnShowPassword.setBackground(Color.WHITE);
        btnShowPassword.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

// Toggle logic
        btnShowPassword.addActionListener(e -> {
            if (btnShowPassword.isSelected()) {
                txtPassword.setEchoChar((char) 0);
            } else {
                txtPassword.setEchoChar('•');
            }
        });

        JPanel passwordFieldPanel = new JPanel(new BorderLayout());
        passwordFieldPanel.setBackground(Color.WHITE);
        passwordFieldPanel.add(txtPassword, BorderLayout.CENTER);
        passwordFieldPanel.add(btnShowPassword, BorderLayout.EAST);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(passwordFieldPanel, gbc);

// Login Button
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(new Color(0, 153, 102));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(btnLogin, gbc);

// Message label
        lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setForeground(Color.RED);
        gbc.gridy = 3;
        formPanel.add(lblMessage, gbc);



        // ==== Gộp tất cả ====
        contentPanel.add(headerPanel);
        contentPanel.add(lblTitle);
        contentPanel.add(formPanel);
        add(contentPanel);
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
}
