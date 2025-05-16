package View;

import Controller.NhaHangController;
import DAO.DichVuDAO;
import Model.DichVu;
import Model.NhaHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;

public class NhaHangForm extends JPanel {
    private NhaHangController controller;
    private DichVuDAO dichVuDAO;
    private ArrayList<DichVu> listDichVu;

    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField txtMaNhaHang, txtGioMoCua, txtGioDongCua, txtLoaiNhaHang;
    private JComboBox<String> comboDichVu;
    private JTextField txtTimKiem;

    public NhaHangForm(Connection conn) {
        controller = new NhaHangController(conn);
        dichVuDAO = new DichVuDAO(conn);
        initComponents();
        loadDichVu();
        loadData();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panelTop = new JPanel(new GridBagLayout());
        panelTop.setBorder(BorderFactory.createTitledBorder("Thông tin Nhà Hàng"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10); 
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelTop.add(new JLabel("Mã Nhà Hàng:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelTop.add(txtMaNhaHang = new JTextField(20), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelTop.add(new JLabel("Tên Dịch Vụ:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelTop.add(comboDichVu = new JComboBox<>(), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelTop.add(new JLabel("Giờ Mở Cửa:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelTop.add(txtGioMoCua = new JTextField(20), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelTop.add(new JLabel("Giờ Đóng Cửa:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelTop.add(txtGioDongCua = new JTextField(20), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        panelTop.add(new JLabel("Loại Nhà Hàng:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        panelTop.add(txtLoaiNhaHang = new JTextField(20), gbc);

        add(panelTop);

        tableModel = new DefaultTableModel(new Object[]{
                "Mã Nhà Hàng", "Tên Dịch Vụ", "Giờ Mở Cửa", "Giờ Đóng Cửa", "Loại Nhà Hàng"
        }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900, 250));
        add(scrollPane);

        // Panel điều khiển
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        txtTimKiem = new JTextField(15);
        JButton btnTimKiem = new JButton("Tìm kiếm");
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xoá");
        JButton btnClear = new JButton("Làm mới");

        panelButtons.add(new JLabel("Mã hoặc Loại Nhà Hàng:"));
        panelButtons.add(txtTimKiem);
        panelButtons.add(btnTimKiem);

        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);

        add(panelButtons);

        // Sự kiện
        btnTimKiem.addActionListener(e -> timKiemNhaHang());
        btnAdd.addActionListener(e -> addNhaHang());
        btnUpdate.addActionListener(e -> updateNhaHang());
        btnDelete.addActionListener(e -> deleteNhaHang());
        btnClear.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaNhaHang.setText(tableModel.getValueAt(row, 0).toString());
                    txtMaNhaHang.setEnabled(false);

                    String tenDv = tableModel.getValueAt(row, 1).toString();
                    comboDichVu.setSelectedItem(tenDv);

                    txtGioMoCua.setText(tableModel.getValueAt(row, 2).toString());
                    txtGioDongCua.setText(tableModel.getValueAt(row, 3).toString());
                    txtLoaiNhaHang.setText(tableModel.getValueAt(row, 4).toString());
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
        comboDichVu.setSelectedIndex(-1);
    }

    private void loadData() {
        tableModel.setRowCount(0);
        ArrayList<NhaHang> list = controller.getAllNhaHang();
        for (NhaHang nh : list) {
            String tenDichVu = "";
            for (DichVu dv : listDichVu) {
                if (dv.getMaDichVu().equals(nh.getMaDichVu())) {
                    tenDichVu = dv.getTenDichVu();
                    break;
                }
            }
            tableModel.addRow(new Object[]{
                    nh.getMaNhaHang(),
                    tenDichVu,
                    nh.getGioMoCua(),
                    nh.getGioDongCua(),
                    nh.getLoaiNhaHang()
            });
        }
    }

    private void timKiemNhaHang() {
        String keyword = txtTimKiem.getText().trim();
        tableModel.setRowCount(0);

        if (keyword.isEmpty()) {
            ArrayList<NhaHang> list = controller.getAllNhaHang();
            for (NhaHang nh : list) {
                String tenDichVu = "";
                for (DichVu dv : listDichVu) {
                    if (dv.getMaDichVu().equals(nh.getMaDichVu())) {
                        tenDichVu = dv.getTenDichVu();
                        break;
                    }
                }
                tableModel.addRow(new Object[]{
                        nh.getMaNhaHang(),
                        tenDichVu,
                        nh.getGioMoCua(),
                        nh.getGioDongCua(),
                        nh.getLoaiNhaHang()
                });
            }
        } else {
            ArrayList<NhaHang> list = controller.searchNhaHang(keyword);
            for (NhaHang nh : list) {
                String tenDichVu = "";
                for (DichVu dv : listDichVu) {
                    if (dv.getMaDichVu().equals(nh.getMaDichVu())) {
                        tenDichVu = dv.getTenDichVu();
                        break;
                    }
                }
                tableModel.addRow(new Object[]{
                        nh.getMaNhaHang(),
                        tenDichVu,
                        nh.getGioMoCua(),
                        nh.getGioDongCua(),
                        nh.getLoaiNhaHang()
                });
            }
        }
    }

    private void addNhaHang() {
        NhaHang nh = getInputNhaHang();
        if (nh != null && controller.addNhaHang(nh)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void updateNhaHang() {
        NhaHang nh = getInputNhaHang();
        if (nh != null && controller.updateNhaHang(nh)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            loadData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
        }
    }

    private void deleteNhaHang() {
        String ma = txtMaNhaHang.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà hàng để xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa nhà hàng có mã " + ma + "?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.deleteNhaHang(ma)) {
                JOptionPane.showMessageDialog(this, "Xoá thành công");
                loadData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xoá thất bại");
            }
        }
    }

    private NhaHang getInputNhaHang() {
        try {
            String maNhaHang = txtMaNhaHang.getText().trim();
            String tenDichVu = (String) comboDichVu.getSelectedItem();
            String maDichVu = "";

            for (DichVu dv : listDichVu) {
                if (dv.getTenDichVu().equals(tenDichVu)) {
                    maDichVu = dv.getMaDichVu();
                    break;
                }
            }

            String gioMoCua = txtGioMoCua.getText().trim();
            String gioDongCua = txtGioDongCua.getText().trim();
            String loaiNhaHang = txtLoaiNhaHang.getText().trim();

            if (maNhaHang.isEmpty() || maDichVu.isEmpty() || gioMoCua.isEmpty() || gioDongCua.isEmpty() || loaiNhaHang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return null;
            }

            return new NhaHang(maNhaHang, maDichVu, gioMoCua, gioDongCua, loaiNhaHang);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi. Vui lòng kiểm tra lại dữ liệu!");
            ex.printStackTrace();
            return null;
        }
    }

    private void clearFields() {
        txtMaNhaHang.setText("");
        txtMaNhaHang.setEnabled(true);
        comboDichVu.setSelectedIndex(-1);
        txtGioMoCua.setText("");
        txtGioDongCua.setText("");
        txtLoaiNhaHang.setText("");
        txtTimKiem.setText("");
        table.clearSelection();
    }
}
