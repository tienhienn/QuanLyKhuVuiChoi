package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TourPanel extends JPanel {

    // Các thành phần giao diện
    public JTextField tfMaTour, tfTenTour, tfMieuTa, tfGiaTour, tfNgayBatDau, tfNgayKetThuc, tfSoLuongMax, tfTimKiem;
    public JButton btnThem, btnSua, btnXoa, btnTim;
    public JTable table;
    public DefaultTableModel model;

    public TourPanel() {
        setLayout(new BorderLayout(10, 10));

        // === Panel Nhập thông tin ===
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông tin Tour", TitledBorder.LEFT, TitledBorder.TOP));

        tfMaTour = new JTextField();
        tfTenTour = new JTextField();
        tfMieuTa = new JTextField();
        tfGiaTour = new JTextField();
        tfNgayBatDau = new JTextField();
        tfNgayKetThuc = new JTextField();
        tfSoLuongMax = new JTextField();

        inputPanel.add(new JLabel("Mã Tour:")); inputPanel.add(tfMaTour);
        inputPanel.add(new JLabel("Tên Tour:")); inputPanel.add(tfTenTour);
        inputPanel.add(new JLabel("Mô Tả:")); inputPanel.add(tfMieuTa);
        inputPanel.add(new JLabel("Giá Tour:")); inputPanel.add(tfGiaTour);
        inputPanel.add(new JLabel("Ngày Bắt Đầu (yyyy-MM-dd):")); inputPanel.add(tfNgayBatDau);
        inputPanel.add(new JLabel("Ngày Kết Thúc (yyyy-MM-dd):")); inputPanel.add(tfNgayKetThuc);
        inputPanel.add(new JLabel("Số Lượng Max:")); inputPanel.add(tfSoLuongMax);

        add(inputPanel, BorderLayout.NORTH);

        // === Table Panel ===
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Mã Tour", "Tên Tour", "Mô Tả", "Giá Tour", "Ngày Bắt Đầu", "Ngày Kết Thúc", "Số Lượng Max"
        });

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Danh sách Tour", TitledBorder.LEFT, TitledBorder.TOP));
        add(scrollPane, BorderLayout.CENTER);

        // === Panel Tìm kiếm + nút chức năng ===
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        tfTimKiem = new JTextField(20);
        btnTim = new JButton("Tìm Kiếm");
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(tfTimKiem);
        searchPanel.add(btnTim);

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);

        controlPanel.add(searchPanel, BorderLayout.WEST);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        add(controlPanel, BorderLayout.SOUTH);
    }
}
