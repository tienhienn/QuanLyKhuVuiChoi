package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class NhanVienPanel extends JPanel {
    public JTextField tfMa, tfTen, tfNgaySinh, tfSDT, tfEmail, tfNgayBD, tfLuong, tfTimKiem;
    public JButton btnThem, btnSua, btnXoa, btnTim, btnClear, btnPhanCong, btnXemCa;
    public JTable table;
    public DefaultTableModel model;

    public NhanVienPanel() {
        setLayout(new BorderLayout(10, 10));

        // ===== Panel nhập liệu phía trên =====
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Thông tin Nhân Viên",
                TitledBorder.LEFT,
                TitledBorder.TOP
        ));

        tfMa = new JTextField();
        tfTen = new JTextField();
        tfNgaySinh = new JTextField();
        tfSDT = new JTextField();
        tfEmail = new JTextField();
        tfNgayBD = new JTextField();
        tfLuong = new JTextField();

        inputPanel.add(new JLabel("Mã NV:")); inputPanel.add(tfMa);
        inputPanel.add(new JLabel("Tên NV:")); inputPanel.add(tfTen);
        inputPanel.add(new JLabel("Ngày sinh (yyyy-MM-dd):")); inputPanel.add(tfNgaySinh);
        inputPanel.add(new JLabel("SĐT:")); inputPanel.add(tfSDT);
        inputPanel.add(new JLabel("Email:")); inputPanel.add(tfEmail);
        inputPanel.add(new JLabel("Ngày bắt đầu (yyyy-MM-dd):")); inputPanel.add(tfNgayBD);
        inputPanel.add(new JLabel("Lương:")); inputPanel.add(tfLuong);

        add(inputPanel, BorderLayout.NORTH);

        // ===== Bảng danh sách nhân viên =====
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
            "Mã NV", "Tên", "Ngày sinh", "SĐT", "Email", "Ngày bắt đầu", "Lương"
        });

        table = new JTable(model);
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Nhân Viên"));
        add(scrollPane, BorderLayout.CENTER);

        // ===== Panel chức năng phía dưới =====
        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        tfTimKiem = new JTextField(20);
        btnTim = new JButton("Tìm");
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnClear = new JButton("Làm mới");
        btnPhanCong = new JButton("Phân công ca");
        btnXemCa = new JButton("Xem ca");

        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(tfTimKiem);
        searchPanel.add(btnTim);

        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnPhanCong);
        buttonPanel.add(btnXemCa);

        controlPanel.add(searchPanel, BorderLayout.WEST);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        add(controlPanel, BorderLayout.SOUTH);
    }
}
