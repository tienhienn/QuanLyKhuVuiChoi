package View;

import DAO.PhanCongCaDAO;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class CaDaPhanCongForm extends JDialog {
    private Connection conn;
    private PhanCongCaDAO phanCongCaDAO;
    private JPanel contentPanel;

    public CaDaPhanCongForm(JFrame parent, String maNhanVien, Connection conn) {
        super(parent, "Thông Tin Ca Làm", true);
        this.conn = conn;
        this.phanCongCaDAO = new PhanCongCaDAO(conn);

        setSize(650, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 242, 245));

        // Panel chính với gradient và shadow
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ shadow
                int shadowSize = 5;
                int shadowOffset = 3;
                g2d.setColor(new Color(0, 0, 0, 30));
                g2d.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowSize - 1, getHeight() - shadowSize - 1, 20, 20);

                // Vẽ background gradient
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 255, 255), 0, getHeight(), new Color(240, 247, 255));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 20, 20);
            }
        };
        mainPanel.setLayout(new BorderLayout(0, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setOpaque(false);

        // Scroll pane chính
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Custom scrollbar
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setPreferredSize(new Dimension(8, 0));
        verticalBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(30, 136, 229);
                this.trackColor = new Color(240, 242, 245);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }
        });

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);

        loadData(maNhanVien);
    }

    private void loadData(String maNV) {
        List<Object[]> list = phanCongCaDAO.getCaDaPhanCongTheoNhanVien(maNV);
        contentPanel.removeAll();

        if (list.isEmpty()) {
            showEmptyState();
        } else {
            showShiftList(list);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showEmptyState() {
        JPanel emptyPanel = new JPanel();
        emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS));
        emptyPanel.setOpaque(false);

        // Icon cho trạng thái trống
        JLabel iconLabel = new JLabel("📅");
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel messageLabel = new JLabel("Chưa có ca phân công");
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        messageLabel.setForeground(new Color(100, 100, 100));
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subMessageLabel = new JLabel("Nhân viên này chưa được phân công ca làm nào");
        subMessageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subMessageLabel.setForeground(new Color(150, 150, 150));
        subMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        emptyPanel.add(Box.createVerticalGlue());
        emptyPanel.add(iconLabel);
        emptyPanel.add(Box.createVerticalStrut(15));
        emptyPanel.add(messageLabel);
        emptyPanel.add(Box.createVerticalStrut(5));
        emptyPanel.add(subMessageLabel);
        emptyPanel.add(Box.createVerticalGlue());

        contentPanel.add(emptyPanel);
    }

    private void showShiftList(List<Object[]> list) {
        // Header với tên nhân viên
        String tenNV = list.get(0)[1].toString();
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JLabel nameLabel = new JLabel(tenNV);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        nameLabel.setForeground(new Color(30, 136, 229));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("Danh Sách Ca Làm Việc");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        titleLabel.setForeground(new Color(100, 100, 100));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(nameLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(25));
        contentPanel.add(headerPanel);

        // Panel chứa các ca
        JPanel shiftsPanel = new JPanel();
        shiftsPanel.setLayout(new BoxLayout(shiftsPanel, BoxLayout.Y_AXIS));
        shiftsPanel.setOpaque(false);

        for (int i = 0; i < list.size(); i++) {
            Object[] row = list.get(i);
            shiftsPanel.add(createShiftCard(row, i));
            if (i < list.size() - 1) {
                shiftsPanel.add(Box.createVerticalStrut(15));
            }
        }

        contentPanel.add(shiftsPanel);
    }

    private JPanel createShiftCard(Object[] data, int index) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Vẽ shadow nhẹ
                int shadowSize = 3;
                int shadowOffset = 2;
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.fillRoundRect(shadowOffset, shadowOffset, getWidth() - shadowSize - 1, getHeight() - shadowSize - 1, 15, 15);

                // Vẽ background
                GradientPaint gp = new GradientPaint(
                    0, 0,
                    index % 2 == 0 ? new Color(240, 247, 255) : new Color(255, 250, 240),
                    getWidth(), 0,
                    index % 2 == 0 ? new Color(230, 240, 255) : new Color(255, 245, 230)
                );
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth() - shadowSize, getHeight() - shadowSize, 15, 15);
            }
        };
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        card.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Thông tin ca làm
        addCardInfo(card, gbc, "Mã NV:", data[0].toString(), 0, 0);
        addCardInfo(card, gbc, "Tên ca:", data[2].toString(), 0, 1);
        addCardInfo(card, gbc, "Ngày làm:", data[3].toString(), 1, 0);
        addCardInfo(card, gbc, "Giờ bắt đầu:", data[4].toString(), 1, 1);
        addCardInfo(card, gbc, "Giờ kết thúc:", data[5].toString(), 2, 0);

        return card;
    }

    private void addCardInfo(JPanel panel, GridBagConstraints gbc, String label, String value, int row, int col) {
        gbc.gridy = row;
        gbc.gridx = col;
        
        JPanel infoPanel = new JPanel(new BorderLayout(10, 0));
        infoPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(label);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(new Color(100, 100, 100));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblValue.setForeground(new Color(50, 50, 50));

        infoPanel.add(lblTitle, BorderLayout.WEST);
        infoPanel.add(lblValue, BorderLayout.CENTER);

        panel.add(infoPanel, gbc);
    }
}
