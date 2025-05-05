package View;

import Model.Vele;
import DAO.VeleDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VelePanel extends JPanel {
    private JTextField txtMaVele, txtMaDatVe, txtDoiTuong, txtSoLuong;
    private JButton btnThem, btnSua, btnXoa, btnTim, btnBack;
    private JTable table;
    private DefaultTableModel model;
    private CardLayout cardLayout;
    private JPanel parentPanel;

    public VelePanel(CardLayout cardLayout,JPanel parentPanel) {
        this.cardLayout = cardLayout;
        this.parentPanel=parentPanel;// Set CardLayout
        setLayout(new BorderLayout(10, 10));

        // ======= FORM NHẬP LIỆU =======
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Vé lẻ"));

        formPanel.add(new JLabel("Mã Vé lẻ:"));
        txtMaVele = new JTextField();
        formPanel.add(txtMaVele);

        formPanel.add(new JLabel("Mã Đặt Vé:"));
        txtMaDatVe = new JTextField();
        formPanel.add(txtMaDatVe);

        formPanel.add(new JLabel("Đối Tượng:"));
        txtDoiTuong = new JTextField();
        formPanel.add(txtDoiTuong);

        formPanel.add(new JLabel("Số Lượng Người:"));
        txtSoLuong = new JTextField();
        formPanel.add(txtSoLuong);

        // ======= NÚT CHỨC NĂNG =======
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xoá");
        btnTim = new JButton("Tìm kiếm");
        btnBack = new JButton("Quay lại");
        buttonPanel.add(btnThem);
        buttonPanel.add(btnSua);
        buttonPanel.add(btnXoa);
        buttonPanel.add(btnTim);
        buttonPanel.add(btnBack); // Add back button

        // ======= TABLE DANH SÁCH =======
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã Vé lẻ", "Mã Đặt Vé", "Đối Tượng", "Số Lượng"});
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Vé lẻ"));

        // ======= GOM THÀNH GIAO DIỆN =======
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // ======= XỬ LÝ NÚT QUAY LẠI =======
        btnBack.addActionListener(e -> {
            // Quay lại màn hình chính hoặc chuyển sang màn hình khác
            cardLayout.show((Container) getParent(), "QuanLyVeForm"); // Change to the main panel using CardLayout
        });
    }

    // ======= GETTER CHO CONTROLLER =======
    public JTextField getTxtMaVele() { return txtMaVele; }
    public JTextField getTxtMaDatVe() { return txtMaDatVe; }
    public JTextField getTxtDoiTuong() { return txtDoiTuong; }
    public JTextField getTxtSoLuong() { return txtSoLuong; }

    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnTim() { return btnTim; }
    public JButton getBtnBack() { return btnBack; } // Getter for back button

    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }

    // ======= ĐỔ DỮ LIỆU LÊN TABLE =======
    public void setTableData(List<Vele> list) {
        model.setRowCount(0);
        for (Vele v : list) {
            model.addRow(new Object[]{
                    v.getMaVele(),
                    v.getMaDatVe(),
                    v.getDoiTuong(),
                    v.getSoLuongNguoi()
            });
        }
    }

    // ======= TIỆN ÍCH CHO LOAD TEST =======
    public void loadData(VeleDAO dao) {
        List<Vele> list = dao.getAllVele();
        setTableData(list);
    }

    // ======= XOÁ DỮ LIỆU NHẬP FORM =======
    public void clearForm() {
        txtMaVele.setText("");
        txtMaDatVe.setText("");
        txtDoiTuong.setText("");
        txtSoLuong.setText("");
    }
}
