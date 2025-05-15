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
    private Connection conn;  // Thêm Connection vào đây

    public PhanCongCaForm(JFrame parent, String maNV, Connection conn) {
        super(parent, "Phân công ca làm", true);
        this.conn = conn;  // Khởi tạo Connection
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        tfMaNV = new JTextField(maNV);  // Điền mã nhân viên vào ô nhập
        cbTenCa = new JComboBox<>();
        tfNgay = new JTextField("yyyy-MM-dd");
        tfGioBD = new JTextField();
        tfGioKT = new JTextField();

        tfGioBD.setEditable(false);
        tfGioKT.setEditable(false);

        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phân công"));
        formPanel.add(new JLabel("Mã nhân viên:"));
        formPanel.add(tfMaNV);
        formPanel.add(new JLabel("Tên ca:"));
        formPanel.add(cbTenCa);
        formPanel.add(new JLabel("Giờ bắt đầu:"));
        formPanel.add(tfGioBD);
        formPanel.add(new JLabel("Giờ kết thúc:"));
        formPanel.add(tfGioKT);
        formPanel.add(new JLabel("Ngày làm việc:"));
        formPanel.add(tfNgay);

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

        JPanel buttonPanel = new JPanel();
        btnXacNhan = new JButton("Xác nhận");
        buttonPanel.add(btnXacNhan);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}