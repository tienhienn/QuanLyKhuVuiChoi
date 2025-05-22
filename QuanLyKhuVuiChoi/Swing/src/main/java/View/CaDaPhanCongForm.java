package View;

import DAO.PhanCongCaDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class CaDaPhanCongForm extends JDialog {
    private Connection conn;
    private PhanCongCaDAO phanCongCaDAO;
    private JPanel contentPanel;

    public CaDaPhanCongForm(JFrame parent, String maNhanVien, Connection conn) {
        super(parent, "Ca đã phân công", true);
        this.conn = conn;
        this.phanCongCaDAO = new PhanCongCaDAO(conn);

        setSize(600, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Thay thế đoạn tạo contentPanel trong constructor thành:

        contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                // Gradient từ xanh dương nhạt sang trắng
                GradientPaint gp = new GradientPaint(0, 0, new Color(180, 210, 255), 0, h, Color.WHITE);
                g2.setPaint(gp);
                g2.fillRect(0, 0, w, h);
            }
        };
        contentPanel.setOpaque(false); // để paintComponent có hiệu lực
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createTitledBorder("☰ Các ca đã phân công"));

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        loadData(maNhanVien);
    }

    private void loadData(String maNV) {
        List<Object[]> list = phanCongCaDAO.getCaDaPhanCongTheoNhanVien(maNV);
        contentPanel.removeAll();

        if (list.isEmpty()) {
            JLabel emptyLabel = new JLabel("Chưa có ca phân công.");
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 18));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(emptyLabel);
        } else {
            // Lấy tên nhân viên từ dòng đầu tiên (giả sử luôn có)
            String tenNV = list.get(0)[1].toString();

            // Label tên nhân viên to và căn giữa
            JLabel lblTenNV = new JLabel(tenNV);
            lblTenNV.setFont(new Font("Segoe UI", Font.BOLD, 28));
            lblTenNV.setForeground(new Color(30, 136, 229));
            lblTenNV.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTenNV.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0)); // margin dưới
            contentPanel.add(lblTenNV);

            // Panel chứa các ca
            JPanel caPanel = new JPanel();
            caPanel.setLayout(new BoxLayout(caPanel, BoxLayout.Y_AXIS));
            caPanel.setBackground(Color.WHITE);

            for (int i = 0; i < list.size(); i++) {
                Object[] row = list.get(i);
                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
                itemPanel.setBackground(new Color(245, 247, 250));
                itemPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));

                Color bg1 = new Color(230, 245, 255); // xanh nhạt
                Color bg2 = new Color(255, 250, 240); // vàng nhạt

                itemPanel.add(createInfoBox("Mã NV", row[0].toString(), i % 2 == 0 ? bg1 : bg2));
                itemPanel.add(Box.createVerticalStrut(6));
                itemPanel.add(createInfoBox("Tên ca", row[2].toString(), i % 2 == 0 ? bg2 : bg1));
                itemPanel.add(Box.createVerticalStrut(6));
                itemPanel.add(createInfoBox("Ngày làm", row[3].toString(), i % 2 == 0 ? bg1 : bg2));
                itemPanel.add(Box.createVerticalStrut(6));
                itemPanel.add(createInfoBox("Giờ bắt đầu", row[4].toString(), i % 2 == 0 ? bg2 : bg1));
                itemPanel.add(Box.createVerticalStrut(6));
                itemPanel.add(createInfoBox("Giờ kết thúc", row[5].toString(), i % 2 == 0 ? bg1 : bg2));

                itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, itemPanel.getPreferredSize().height));
                caPanel.add(itemPanel);
                caPanel.add(Box.createVerticalStrut(15));
            }

            contentPanel.add(caPanel);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createInfoBox(String label, String value, Color bgColor) {
        JPanel box = new JPanel();
        box.setPreferredSize(new Dimension(400, 50));
        box.setBackground(bgColor);
        box.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150), 1, true), // viền bo tròn
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        box.setLayout(new BorderLayout());

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setForeground(new Color(30, 136, 229));
        box.add(lbl, BorderLayout.NORTH);

        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        val.setForeground(Color.DARK_GRAY);
        val.setHorizontalAlignment(SwingConstants.CENTER);
        box.add(val, BorderLayout.CENTER);

        return box;
    }
}
