package View;

import DAO.PhanCongCaDAO;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import Model.CaLamViec;
import java.sql.Connection;

public class PhanCongCaForm extends JDialog {
    public JTextField tfMaNV, tfNgay;
    public JComboBox<String> cbTenCa;
    public JTextField tfGioBD, tfGioKT;
    public JButton btnXacNhan;
    public Map<String, CaLamViec> caMap = new HashMap<>();
    private Connection conn;

    public PhanCongCaForm(JFrame parent, String maNV, Connection conn) {
        super(parent, "Phân công ca làm", true);
        this.conn = conn;
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // Panel chính với màu nền
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(227, 242, 253)); // Màu xanh nhạt

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(new Color(227, 242, 253));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(30, 136, 229), 1),
                "Thông tin phân công",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(30, 136, 229)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Khởi tạo các components
        tfMaNV = createStyledTextField(maNV);
        tfMaNV.setEditable(false); // Không cho phép sửa mã NV
        
        cbTenCa = new JComboBox<>();
        cbTenCa.setBackground(Color.WHITE);
        cbTenCa.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        tfNgay = createStyledTextField("dd/MM/yyyy");
        tfGioBD = createStyledTextField("");
        tfGioKT = createStyledTextField("");

        tfGioBD.setEditable(false);
        tfGioKT.setEditable(false);

        // Thêm components vào form
        addFormRow(formPanel, "Mã nhân viên:", tfMaNV);
        addFormRow(formPanel, "Tên ca:", cbTenCa);
        addFormRow(formPanel, "Giờ bắt đầu:", tfGioBD);
        addFormRow(formPanel, "Giờ kết thúc:", tfGioKT);
        addFormRow(formPanel, "Ngày làm việc:", tfNgay);

        // Khi chọn tên ca thì cập nhật giờ
        cbTenCa.addActionListener(e -> {
            String tenCa = (String) cbTenCa.getSelectedItem();
            CaLamViec ca = caMap.get(tenCa);
            if (ca != null) {
                tfGioBD.setText(ca.getGioBatDau().toString());
                tfGioKT.setText(ca.getGioKetThuc().toString());
            } else {
                tfGioBD.setText("");
                tfGioKT.setText("");
            }
        });

        // Panel nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(227, 242, 253));
        
        btnXacNhan = new JButton("Xác nhận");
        styleButton(btnXacNhan);
        buttonPanel.add(btnXacNhan);

        // Thêm các panel vào frame
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }

    private JTextField createStyledTextField(String text) {
        JTextField textField = new JTextField(text);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(30, 136, 229), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        return textField;
    }

    private void addFormRow(JPanel panel, String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(new Color(33, 33, 33));
        panel.add(label);
        panel.add(component);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 136, 229));
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setFocusPainted(false);
        
        // Hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(21, 101, 192));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 136, 229));
            }
        });
    }
}