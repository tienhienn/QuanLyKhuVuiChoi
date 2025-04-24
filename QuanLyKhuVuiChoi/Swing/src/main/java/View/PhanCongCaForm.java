package View;

import javax.swing.*;
import java.awt.*;

public class PhanCongCaForm extends JDialog {
    public JTextField tfMaNV, tfMaCa, tfNgay;
    public JButton btnXacNhan;

    public PhanCongCaForm(JFrame parent) {
        super(parent, "Phân công ca làm", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        tfMaNV = new JTextField();
        tfMaCa = new JTextField();
        tfNgay = new JTextField("yyyy-MM-dd");

        formPanel.add(new JLabel("Mã nhân viên:"));
        formPanel.add(tfMaNV);
        formPanel.add(new JLabel("Mã ca:"));
        formPanel.add(tfMaCa);
        formPanel.add(new JLabel("Ngày làm việc:"));
        formPanel.add(tfNgay);

        JPanel buttonPanel = new JPanel();
        btnXacNhan = new JButton("Xác nhận");
        buttonPanel.add(btnXacNhan);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
