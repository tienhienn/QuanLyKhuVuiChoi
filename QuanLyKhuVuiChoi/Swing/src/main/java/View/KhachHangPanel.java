package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KhachHangPanel extends JPanel {
    public JTextField tfMaKH, tfTenKH, tfMatKhau, tfSDT, tfEmail, tfDiaChi, tfGioiTinh, tfNgaySinh, tfQuocTich, tfTimKiem;
    public JButton btnThem, btnSua, btnXoa, btnTim;
    public JTable table;
    public DefaultTableModel model;

    public KhachHangPanel() {
        setLayout(new BorderLayout(10, 10));

        // Panel nhập
        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông tin Khách Hàng", TitledBorder.LEFT, TitledBorder.TOP));

        tfMaKH = new JTextField();
        tfTenKH = new JTextField();
        tfMatKhau = new JTextField();
        tfSDT = new JTextField();
        tfEmail = new JTextField();
        tfDiaChi = new JTextField();
        tfGioiTinh = new JTextField();
        tfNgaySinh = new JTextField();
        tfQuocTich = new JTextField();

        inputPanel.add(new JLabel("Mã KH:")); inputPanel.add(tfMaKH);
        inputPanel.add(new JLabel("Tên KH:")); inputPanel.add(tfTenKH);
        inputPanel.add(new JLabel("Mật khẩu:")); inputPanel.add(tfMatKhau);
        inputPanel.add(new JLabel("SĐT:")); inputPanel.add(tfSDT);
        inputPanel.add(new JLabel("Email:")); inputPanel.add(tfEmail);
        inputPanel.add(new JLabel("Địa chỉ:")); inputPanel.add(tfDiaChi);
        inputPanel.add(new JLabel("Giới tính:")); inputPanel.add(tfGioiTinh);
        inputPanel.add(new JLabel("Ngày sinh (yyyy-MM-dd):")); inputPanel.add(tfNgaySinh);
        inputPanel.add(new JLabel("Quốc tịch:")); inputPanel.add(tfQuocTich);

        add(inputPanel, BorderLayout.NORTH);

        // Bảng
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Mã KH", "Tên KH", "Mật khẩu", "SĐT", "Email", "Địa chỉ", "Giới tính", "Ngày sinh", "Quốc tịch"
        });
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Khách Hàng"));
        add(scrollPane, BorderLayout.CENTER);

        // Panel chức năng
        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        tfTimKiem = new JTextField(20);
        btnTim = new JButton("Tìm");
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");

        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(tfTimKiem);
        searchPanel.add(btnTim);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        controlPanel.add(searchPanel, BorderLayout.WEST);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        add(controlPanel, BorderLayout.SOUTH);
    }
}
