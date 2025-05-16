package View;

import Controller.TroChoiController;
import DAO.DichVuDAO;
import Model.DichVu;
import Model.TroChoi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;

public class TroChoiForm extends JPanel {
    private TroChoiController controller;
    private DichVuDAO dichVuDAO;
    private ArrayList<DichVu> listDichVu;

    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField txtMaTroChoi, txtTenTroChoi, txtMoTa, txtGioiHanTuoi, txtSucChua;
    private JTextField txtThoiGianHoatDong;
    private JComboBox<String> comboDichVu;
    private JTextField txtTimKiem;

    public TroChoiForm(Connection conn) {
        controller = new TroChoiController(conn);
        dichVuDAO = new DichVuDAO(conn);
        initComponents();
        loadDichVu();
        loadData();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panelTop = new JPanel(new GridLayout(4, 4, 10, 10));
        panelTop.setBorder(BorderFactory.createTitledBorder("Thông tin Trò Chơi"));

        txtMaTroChoi = new JTextField();
        comboDichVu = new JComboBox<>();
        txtTenTroChoi = new JTextField();
        txtMoTa = new JTextField();
        txtGioiHanTuoi = new JTextField();
        txtSucChua = new JTextField();
        txtThoiGianHoatDong = new JTextField();

        panelTop.add(new JLabel("Mã Trò Chơi:"));
        panelTop.add(txtMaTroChoi);
        panelTop.add(new JLabel("Tên Dịch Vụ:"));
        panelTop.add(comboDichVu);
        panelTop.add(new JLabel("Tên Trò Chơi:"));
        panelTop.add(txtTenTroChoi);
        panelTop.add(new JLabel("Mô Tả:"));
        panelTop.add(txtMoTa);
        panelTop.add(new JLabel("Giới Hạn Tuổi:"));
        panelTop.add(txtGioiHanTuoi);
        panelTop.add(new JLabel("Sức Chứa:"));
        panelTop.add(txtSucChua);
        panelTop.add(new JLabel("Thời Gian Hoạt Động (yyyy-mm-dd):"));
        panelTop.add(txtThoiGianHoatDong);
        panelTop.add(new JLabel());
        panelTop.add(new JLabel());

        add(panelTop);

        tableModel = new DefaultTableModel(new Object[]{
                "Mã Trò Chơi", "Tên Dịch Vụ", "Tên Trò Chơi", "Mô Tả",
                "Giới Hạn Tuổi", "Sức Chứa", "Thời Gian Hoạt Động"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 250));
        add(scrollPane);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        txtTimKiem = new JTextField(15);
        JButton btnTimKiem = new JButton("Tìm kiếm");
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xoá");
        JButton btnClear = new JButton("Làm mới");

        panelButtons.add(new JLabel("   Mã Trò Chơi:"));
        panelButtons.add(txtTimKiem);
        panelButtons.add(btnTimKiem);

        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);

        add(panelButtons);

        // Events
        btnTimKiem.addActionListener(e -> timKiemTroChoi());
        btnAdd.addActionListener(e -> addTroChoi());
        btnUpdate.addActionListener(e -> updateTroChoi());
        btnDelete.addActionListener(e -> deleteTroChoi());
        btnClear.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaTroChoi.setText(tableModel.getValueAt(row, 0).toString());
                    txtMaTroChoi.setEnabled(false);

                    String tenDv = tableModel.getValueAt(row, 1).toString();
                    comboDichVu.setSelectedItem(tenDv);

                    txtTenTroChoi.setText(tableModel.getValueAt(row, 2).toString());
                    txtMoTa.setText(tableModel.getValueAt(row, 3).toString());
                    txtGioiHanTuoi.setText(tableModel.getValueAt(row, 4).toString());
                    txtSucChua.setText(tableModel.getValueAt(row, 5).toString());
                    txtThoiGianHoatDong.setText(tableModel.getValueAt(row, 6).toString());
                }
            }
        });
    }

    private void loadDichVu() {
        listDichVu = dichVuDAO.getAll();
        comboDichVu.removeAllItems();
        for (DichVu dv : listDichVu) {
            comboDichVu.addItem(dv.getTenDichVu());
        }
    }

    private void loadData() {
        tableModel.setRowCount(0);
        ArrayList<TroChoi> list = controller.getAllTroChoi();
        for (TroChoi tc : list) {
            String tenDichVu = "";
            for (DichVu dv : listDichVu) {
                if (dv.getMaDichVu().equals(tc.getMaDichVu())) {
                    tenDichVu = dv.getTenDichVu();
                    break;
                }
            }
            tableModel.addRow(new Object[]{
                    tc.getMaTroChoi(),
                    tenDichVu,
                    tc.getTenTroChoi(),
                    tc.getMoTa(),
                    tc.getGioiHanDoTuoi(),
                    tc.getSucChua(),
                    tc.getThoiGianHoatDong()
            });
        }
    }

    private void timKiemTroChoi() {
        String maTim = txtTimKiem.getText().trim();
        tableModel.setRowCount(0);

        if (maTim.isEmpty()) {
            ArrayList<TroChoi> list = controller.getAllTroChoi();
            for (TroChoi tc : list) {
                String tenDichVu = "";
                for (DichVu dv : listDichVu) {
                    if (dv.getMaDichVu().equals(tc.getMaDichVu())) {
                        tenDichVu = dv.getTenDichVu();
                        break;
                    }
                }
                tableModel.addRow(new Object[]{
                        tc.getMaTroChoi(),
                        tenDichVu,
                        tc.getTenTroChoi(),
                        tc.getMoTa(),
                        tc.getGioiHanDoTuoi(),
                        tc.getSucChua(),
                        tc.getThoiGianHoatDong()
                });
            }
        } else {
            ArrayList<TroChoi> list = controller.timKiemTroChoi(maTim);
            for (TroChoi tc : list) {
                String tenDichVu = "";
                for (DichVu dv : listDichVu) {
                    if (dv.getMaDichVu().equals(tc.getMaDichVu())) {
                        tenDichVu = dv.getTenDichVu();
                        break;
                    }
                }
                tableModel.addRow(new Object[]{
                        tc.getMaTroChoi(),
                        tenDichVu,
                        tc.getTenTroChoi(),
                        tc.getMoTa(),
                        tc.getGioiHanDoTuoi(),
                        tc.getSucChua(),
                        tc.getThoiGianHoatDong()
                });
            }
        }
    }

    private void addTroChoi() {
        TroChoi tc = getInputTroChoi();
        if (tc != null && controller.addTroChoi(tc)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void updateTroChoi() {
        TroChoi tc = getInputTroChoi();
        if (tc != null && controller.updateTroChoi(tc)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            loadData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
        }
    }

    private void deleteTroChoi() {
        String ma = txtMaTroChoi.getText();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trò chơi để xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa trò chơi có mã " + ma + "?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.deleteTroChoi(ma)) {
                JOptionPane.showMessageDialog(this, "Xoá thành công");
                loadData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xoá thất bại");
            }
        }
    }

    private TroChoi getInputTroChoi() {
            try {
                String maTroChoi = txtMaTroChoi.getText().trim();
                String tenDichVu = (String) comboDichVu.getSelectedItem();
                String maDichVu = "";

                for (DichVu dv : listDichVu) {
                    if (dv.getTenDichVu().equals(tenDichVu)) {
                        maDichVu = dv.getMaDichVu();
                        break;
                    }
                }

                String tenTroChoi = txtTenTroChoi.getText().trim();
                String moTa = txtMoTa.getText().trim();
                int gioiHanTuoi = Integer.parseInt(txtGioiHanTuoi.getText().trim());
                int sucChua = Integer.parseInt(txtSucChua.getText().trim());
                String thoiGianHoatDong = txtThoiGianHoatDong.getText().trim();

                if (maTroChoi.isEmpty() || maDichVu.isEmpty() || tenTroChoi.isEmpty() || moTa.isEmpty() || thoiGianHoatDong.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                    return null;
                }
                if (!thoiGianHoatDong.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    JOptionPane.showMessageDialog(this, "Thời gian hoạt động phải theo định dạng yyyy-MM-dd.");
                    return null;
                }

                return new TroChoi(maTroChoi, maDichVu, tenTroChoi, moTa, gioiHanTuoi, sucChua, thoiGianHoatDong);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tuổi và sức chứa phải là số nguyên!");
                return null;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi. Vui lòng kiểm tra lại dữ liệu!");
                ex.printStackTrace();
                return null;
            }
        }
    private void clearFields() {
        txtMaTroChoi.setText("");
        txtMaTroChoi.setEnabled(true);
        comboDichVu.setSelectedIndex(-1);
        txtTenTroChoi.setText("");
        txtMoTa.setText("");
        txtGioiHanTuoi.setText("");
        txtSucChua.setText("");
        txtThoiGianHoatDong.setText("");
        txtTimKiem.setText("");
        table.clearSelection();
    }
}
