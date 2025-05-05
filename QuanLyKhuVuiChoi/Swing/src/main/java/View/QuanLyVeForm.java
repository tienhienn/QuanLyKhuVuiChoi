package View;

import Controller.VeTourController;
import Controller.VeleController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class QuanLyVeForm extends JPanel {

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private JButton btnVeLe, btnVeTour;
    private Connection conn;
    private JLabel title;
    private CardLayout mainCardLayout;
    private JPanel mainPanel;

    public QuanLyVeForm(Connection conn, CardLayout mainCardLayout, JPanel mainPanel) {
        this.conn = conn;
        this.mainCardLayout = mainCardLayout;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        // ===== Tiêu đề =====
        title = new JLabel("QUẢN LÝ VÉ", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // ===== Nút chọn loại vé =====
        JPanel buttonPanel = new JPanel();
        btnVeLe = new JButton("Vé Lẻ");
        btnVeTour = new JButton("Vé Tour");
        buttonPanel.add(btnVeLe);
        buttonPanel.add(btnVeTour);
        add(buttonPanel, BorderLayout.CENTER);

        // ===== Panel chứa các giao diện con =====
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Giao diện quản lý vé lẻ và vé tour
        VelePanel veLeForm = new VelePanel(cardLayout, this);
        VeTourForm veTourForm = new VeTourForm(mainCardLayout, mainPanel);

        contentPanel.add(veLeForm, "VeLe");
        new VeleController(veLeForm, conn);
        contentPanel.add(veTourForm, "VeTour");
        new VeTourController(veTourForm, conn);

        // Ban đầu KHÔNG hiển thị panel vé nào cả
        contentPanel.setVisible(false);
        add(contentPanel, BorderLayout.SOUTH);

        // ===== Sự kiện nút =====
        btnVeLe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                title.setVisible(false); // Ẩn tiêu đề
                contentPanel.setVisible(true); // Hiện contentPanel
                cardLayout.show(contentPanel, "VeLe");
            }
        });

        btnVeTour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                title.setVisible(false); // Ẩn tiêu đề
                contentPanel.setVisible(true); // Hiện contentPanel
                cardLayout.show(contentPanel, "VeTour");
            }
        });
    }
}
