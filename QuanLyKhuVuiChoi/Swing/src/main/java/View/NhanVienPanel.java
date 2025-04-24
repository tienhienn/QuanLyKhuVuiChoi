package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NhanVienPanel extends JPanel {
    public JTable table;
    public DefaultTableModel model;
    public JTextField tfMa, tfTen, tfNgaySinh, tfSDT, tfEmail, tfNgayBD, tfLuong, tfTimKiem;
    public JButton btnThem, btnSua, btnXoa, btnTim, btnPhanCong, btnXemCa;

    public NhanVienPanel() {
        setLayout(new BorderLayout());

        // Bảng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã NV", "Tên", "Ngày sinh", "SĐT", "Email", "Ngày bắt đầu", "Lương"});
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Form nhập liệu
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 5));
        tfMa = new JTextField();
        tfTen = new JTextField();
        tfNgaySinh = new JTextField();
        tfSDT = new JTextField();
        tfEmail = new JTextField();
        tfNgayBD = new JTextField();
        tfLuong = new JTextField();

        formPanel.add(new JLabel("Mã nhân viên:")); formPanel.add(tfMa);
        formPanel.add(new JLabel("Tên nhân viên:")); formPanel.add(tfTen);
        formPanel.add(new JLabel("Ngày sinh (yyyy-MM-dd):")); formPanel.add(tfNgaySinh);
        formPanel.add(new JLabel("SĐT:")); formPanel.add(tfSDT);
        formPanel.add(new JLabel("Email:")); formPanel.add(tfEmail);
        formPanel.add(new JLabel("Ngày bắt đầu (yyyy-MM-dd):")); formPanel.add(tfNgayBD);
        formPanel.add(new JLabel("Lương:")); formPanel.add(tfLuong);

        add(formPanel, BorderLayout.NORTH);

        // Panel nút
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 5));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnTim = new JButton("Tìm kiếm");
        btnPhanCong = new JButton("Phân công ca");
        btnXemCa = new JButton("Xem ca đã phân công");

        tfTimKiem = new JTextField();
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(tfTimKiem);
        buttonPanel.add(btnTim);
        buttonPanel.add(btnPhanCong);
        buttonPanel.add(btnXemCa);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
