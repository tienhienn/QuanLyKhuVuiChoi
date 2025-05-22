package View;


import javax.swing.*;
import java.awt.*;

public class TrangChuPanel extends JPanel {
    public TrangChuPanel() {
        setLayout(new BorderLayout());

        /* 🌟 NỀN TỔNG THỂ XANH THIÊN THANH NHẠT */
        setBackground(new Color(220, 237, 255));   // #DCEFFF – xanh rất nhạt

        // ===== Dashboard được căn giữa toàn trang =====
        JPanel wrapper = new JPanel(new GridBagLayout());        // gói để căn giữa
        wrapper.setOpaque(false);                                // cho nền xuyên suốt

        JPanel dashboard = new JPanel(new GridLayout(2, 4, 20, 20));
        dashboard.setOpaque(false);                              // nền xuyên suốt
        dashboard.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        // Các card vẫn giữ màu riêng
        dashboard.add(createCard("👤", "Khách hàng", 1243, new Color(66, 165, 245)));
        dashboard.add(createCard("👨‍💼", "Nhân viên", 85, new Color(102, 187, 106)));
        dashboard.add(createCard("🎢", "Tour đang mở", 12, new Color(126, 87, 194)));
        dashboard.add(createCard("🏞️", "Khu vui chơi", 5, new Color(255, 167, 38)));
        dashboard.add(createCard("🎟️", "Vé đã bán", 1032, new Color(239, 83, 80)));
        dashboard.add(createCard("🧾", "Hóa đơn", 856, new Color(38, 198, 218)));
        dashboard.add(createCard("👾", "Trò chơi", 48,  new Color(0x8E24AA)));  // tím
        dashboard.add(createCard("🎉", "Sự kiện", 9,  new Color(0xD81B60)));    // hồng
        dashboard.add(createCard("🍽️", "Nhà hàng", 3,  new Color(0xF4511E)));  // cam trầm
        dashboard.add(createCard("🎁", "Lưu niệm", 27,  new Color(255, 153, 204)));

        wrapper.add(dashboard);          // GridBagLayout sẽ tự căn giữa
        add(wrapper, BorderLayout.CENTER);
    }

    /* Tạo card y như trước */
    private JPanel createCard(String icon, String label, int value, Color bg) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bg);

        // ✅ Padding lớn hơn
        card.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20)); 

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setMaximumSize(new Dimension(250, 160)); // Tùy chọn kích thước tối đa nếu bạn muốn giới hạn
        card.setOpaque(true);

        JLabel iconLb = new JLabel(icon, JLabel.CENTER);
        iconLb.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 42)); // to hơn
        iconLb.setForeground(Color.WHITE);

        JLabel valueLb = new JLabel(String.valueOf(value), JLabel.CENTER);
        valueLb.setFont(new Font("Segoe UI", Font.BOLD, 28)); // to hơn
        valueLb.setForeground(Color.WHITE);
        valueLb.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JLabel textLb = new JLabel(label, JLabel.CENTER);
        textLb.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // to nhẹ
        textLb.setForeground(Color.WHITE);

        card.add(iconLb);
        card.add(valueLb);
        card.add(textLb);

        return card;
    }
}
