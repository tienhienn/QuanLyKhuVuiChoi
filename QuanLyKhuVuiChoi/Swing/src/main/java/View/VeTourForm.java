package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VeTourForm extends JPanel {

    private JTextField txtMaVeTour, txtMaDatVe, txtMaTour, txtSoLuongNguoi, txtPhuongTien, txtGioDi;
    private JButton btnAdd, btnEdit, btnDelete, btnSearch, btnBack;
    private JTable table;
    private DefaultTableModel model;
    private CardLayout cardLayout;  // Thêm CardLayout nếu sử dụng để quay lại màn hình chính
    private JPanel parentPanel;  // Panel cha để thao tác với CardLayout

    public VeTourForm(CardLayout cardLayout, JPanel parentPanel) { // Truyền cardLayout và parentPanel vào constructor
        this.cardLayout = cardLayout;
        this.parentPanel = parentPanel;
        setLayout(new BorderLayout(10, 10));

        // ==== Panel nhập liệu ====
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Vé Tour"));

        inputPanel.add(new JLabel("Mã Vé Tour:"));
        txtMaVeTour = new JTextField();
        inputPanel.add(txtMaVeTour);

        inputPanel.add(new JLabel("Mã Đặt Vé:"));
        txtMaDatVe = new JTextField();
        inputPanel.add(txtMaDatVe);

        inputPanel.add(new JLabel("Mã Tour:"));
        txtMaTour = new JTextField();
        inputPanel.add(txtMaTour);

        inputPanel.add(new JLabel("Số Lượng Người:"));
        txtSoLuongNguoi = new JTextField();
        inputPanel.add(txtSoLuongNguoi);

        inputPanel.add(new JLabel("Phương Tiện:"));
        txtPhuongTien = new JTextField();
        inputPanel.add(txtPhuongTien);

        inputPanel.add(new JLabel("Giờ Đi:"));
        txtGioDi = new JTextField();
        inputPanel.add(txtGioDi);

        // ==== Panel nút chức năng ====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnSearch = new JButton("Tìm kiếm");
        btnBack = new JButton("Quay lại");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnBack); // Thêm nút "Quay lại"

        // ==== Bảng dữ liệu ====
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã Vé Tour", "Mã Đặt Vé", "Mã Tour", "Số Lượng", "Phương Tiện", "Giờ Đi"});
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Vé Tour"));

        // ==== Gộp lại ====
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(inputPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ===== Xử lý nút "Quay lại" =====
        btnBack.addActionListener(e -> {
            // Quay lại màn hình chính hoặc chuyển sang màn hình khác
            cardLayout.show(parentPanel, "Vé"); // Sử dụng cardLayout để chuyển về QuanLyVeForm
        });
    }

    // ===== Getter để Controller sử dụng =====
    public JTextField getTxtMaVeTour() { return txtMaVeTour; }
    public JTextField getTxtMaDatVe() { return txtMaDatVe; }
    public JTextField getTxtMaTour() { return txtMaTour; }
    public JTextField getTxtSoLuongNguoi() { return txtSoLuongNguoi; }
    public JTextField getTxtPhuongTien() { return txtPhuongTien; }
    public JTextField getTxtGioDi() { return txtGioDi; }

    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnEdit() { return btnEdit; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnSearch() { return btnSearch; }
    public JButton getBtnBack() { return btnBack; } // Getter cho nút Quay lại

    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }

    // ===== Đổ dữ liệu lên bảng =====
    public void setTableData(java.util.List<Model.VeTour> list) {
        model.setRowCount(0);
        for (Model.VeTour vt : list) {
            model.addRow(new Object[]{
                    vt.getMaVeTour(),
                    vt.getMaDatVe(),
                    vt.getMaTour(),
                    vt.getSoLuongNguoi(),
                    vt.getPhuongTien(),
                    vt.getGioDi()
            });
        }
    }

    // ===== Xoá form =====
    public void clearForm() {
        txtMaVeTour.setText("");
        txtMaDatVe.setText("");
        txtMaTour.setText("");
        txtSoLuongNguoi.setText("");
        txtPhuongTien.setText("");
        txtGioDi.setText("");
    }
}
