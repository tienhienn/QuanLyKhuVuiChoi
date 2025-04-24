package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    public JTextField txtUsername;
    public JPasswordField txtPassword;
    public JButton btnLogin;
    public JLabel lblMessage;

    public LoginView() {
        setTitle("User Login");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Tổng thể chia 2 phần: avatar trái và form phải
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // ==== Avatar Panel ====
        JPanel avatarPanel = new JPanel();
        avatarPanel.setBackground(Color.WHITE);
        avatarPanel.setPreferredSize(new Dimension(250, 300));
        JLabel avatarLabel = new JLabel();
        avatarLabel.setHorizontalAlignment(SwingConstants.CENTER);
        avatarLabel.setIcon(new ImageIcon(getClass().getResource("/avatar.png"))); // đặt avatar.png trong folder resources
        avatarPanel.add(avatarLabel);

        // ==== Form Panel ====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle = new JLabel("User Login");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Username with icon
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(Color.WHITE);
        JLabel userIcon = new JLabel(new ImageIcon(getClass().getResource("/user_icon.png")));
        txtUsername = new JTextField();
        txtUsername.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        usernamePanel.add(userIcon, BorderLayout.WEST);
        usernamePanel.add(txtUsername, BorderLayout.CENTER);

        // Password with icon
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.WHITE);
        JLabel passIcon = new JLabel(new ImageIcon(getClass().getResource("/lock_icon.png")));
        txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        passwordPanel.add(passIcon, BorderLayout.WEST);
        passwordPanel.add(txtPassword, BorderLayout.CENTER);

        // Login button
        btnLogin = new JButton("Login");
        btnLogin.setBackground(new Color(0, 153, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);

        // Message label
        lblMessage = new JLabel("", SwingConstants.CENTER);
        lblMessage.setForeground(Color.RED);

        // Add to layout
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(lblTitle, gbc);
        gbc.gridy++;
        formPanel.add(usernamePanel, gbc);
        gbc.gridy++;
        formPanel.add(passwordPanel, gbc);
        gbc.gridy++;
        formPanel.add(btnLogin, gbc);
        gbc.gridy++;
        formPanel.add(lblMessage, gbc);

        contentPanel.add(avatarPanel, BorderLayout.WEST);
        contentPanel.add(formPanel, BorderLayout.CENTER);

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
