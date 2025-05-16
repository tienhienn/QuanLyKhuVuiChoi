package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;

public class VeTourPanel extends JPanel {

    public JTextField tfMaVeTour, tfMaDatVe, tfMaTour, tfNguoiLon, tfTreEm, tfNguoiGia, tfTimKiem;
    public JButton btnThem, btnSua, btnXoa, btnTim, btnClear;
    public JTable table;
    public DefaultTableModel model;
    
    public Connection conn;

    public VeTourPanel(Connection conn) {
        this();
        this.conn = conn;
    }

    public VeTourPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Thông tin Vé Tour", 
            TitledBorder.LEFT, TitledBorder.TOP));

        tfMaVeTour = new JTextField();
        tfMaDatVe = new JTextField();
        tfMaTour = new JTextField();
        tfNguoiLon = new JTextField();
        tfTreEm = new JTextField();
        tfNguoiGia = new JTextField();

        inputPanel.add(new JLabel("Mã Vé Tour:"));   inputPanel.add(tfMaVeTour);
        inputPanel.add(new JLabel("Mã Đặt Vé:"));    inputPanel.add(tfMaDatVe);
        inputPanel.add(new JLabel("Mã Tour:"));      inputPanel.add(tfMaTour);
        inputPanel.add(new JLabel("Người Lớn:"));    inputPanel.add(tfNguoiLon);
        inputPanel.add(new JLabel("Trẻ Em:"));       inputPanel.add(tfTreEm);
        inputPanel.add(new JLabel("Người Già:"));    inputPanel.add(tfNguoiGia);

        add(inputPanel, BorderLayout.NORTH);

        // === Table Panel ===
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[] {
            "Mã Vé Tour", "Mã Đặt Vé", "Mã Tour", "Người Lớn", "Trẻ Em", "Người Già"
        });

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Danh sách Vé Tour", 
            TitledBorder.LEFT, TitledBorder.TOP));
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
        btnClear = new JButton("Làm mới");
        
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnClear);
        
        controlPanel.add(searchPanel, BorderLayout.WEST);
        controlPanel.add(buttonPanel, BorderLayout.EAST);

        add(controlPanel, BorderLayout.SOUTH);
    }
}
