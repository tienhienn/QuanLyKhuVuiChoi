package View;

import Controller.SuKienController;
import DAO.DichVuDAO;
import Model.DichVu;
import Model.SuKien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;

public class SuKienForm extends JPanel {
    private SuKienController controller;
    private DichVuDAO dichVuDAO;
    private ArrayList<DichVu> listDichVu;

    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField txtMaSuKien, txtTenSuKien, txtThoiGianBatDau, txtThoiGianKetThuc;
    private JTextField txtTrangThaiHoatDong, txtMucDoCuonHut, txtGioiHanDoTuoi, txtSucChua;
    private JComboBox<String> comboDichVu;
    private JTextField txtTimKiem;

    public SuKienForm(Connection conn) {
        controller = new SuKienController(conn);
        dichVuDAO = new DichVuDAO(conn);
        initComponents();
        loadDichVu();
        loadData();
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel panelTop = new JPanel(new GridLayout(5, 4, 10, 10));
        panelTop.setBorder(BorderFactory.createTitledBorder("Thông tin Sự Kiện"));

        txtMaSuKien = new JTextField();
        comboDichVu = new JComboBox<>();
        txtTenSuKien = new JTextField();
        txtThoiGianBatDau = new JTextField();
        txtThoiGianKetThuc = new JTextField();
        txtTrangThaiHoatDong = new JTextField();
        txtMucDoCuonHut = new JTextField();
        txtGioiHanDoTuoi = new JTextField();
        txtSucChua = new JTextField();

        panelTop.add(new JLabel("Mã Sự Kiện:"));
        panelTop.add(txtMaSuKien);
        panelTop.add(new JLabel("Tên Dịch Vụ:"));
        panelTop.add(comboDichVu);

        panelTop.add(new JLabel("Tên Sự Kiện:"));
        panelTop.add(txtTenSuKien);
        panelTop.add(new JLabel("Thời Gian Bắt Đầu (yyyy-MM-dd):"));
        panelTop.add(txtThoiGianBatDau);

        panelTop.add(new JLabel("Thời Gian Kết Thúc (yyyy-MM-dd):"));
        panelTop.add(txtThoiGianKetThuc);
        panelTop.add(new JLabel("Trạng Thái Hoạt Động:"));
        panelTop.add(txtTrangThaiHoatDong);

        panelTop.add(new JLabel("Mức Độ Cuốn Hút:"));
        panelTop.add(txtMucDoCuonHut);
        panelTop.add(new JLabel("Giới Hạn Độ Tuổi:"));
        panelTop.add(txtGioiHanDoTuoi);

        panelTop.add(new JLabel("Sức Chứa:"));
        panelTop.add(txtSucChua);
        panelTop.add(new JLabel());
        panelTop.add(new JLabel());

        add(panelTop);

        tableModel = new DefaultTableModel(new Object[]{
                "Mã Sự Kiện", "Tên Dịch Vụ", "Tên Sự Kiện", "Thời Gian Bắt Đầu",
                "Thời Gian Kết Thúc", "Trạng Thái", "Mức Độ Cuốn Hút", "Giới Hạn Tuổi", "Sức Chứa"
        }, 0);
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

        panelButtons.add(new JLabel("   Mã Sự Kiện:"));
        panelButtons.add(txtTimKiem);
        panelButtons.add(btnTimKiem);

        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);

        add(panelButtons);

        // Sự kiện
        btnTimKiem.addActionListener(e -> timKiemSuKien());
        btnAdd.addActionListener(e -> addSuKien());
        btnUpdate.addActionListener(e -> updateSuKien());
        btnDelete.addActionListener(e -> deleteSuKien());
        btnClear.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaSuKien.setText(tableModel.getValueAt(row, 0).toString());
                    txtMaSuKien.setEnabled(false);

                    String tenDv = tableModel.getValueAt(row, 1).toString();
                    comboDichVu.setSelectedItem(tenDv);

                    txtTenSuKien.setText(tableModel.getValueAt(row, 2).toString());
                    txtThoiGianBatDau.setText(tableModel.getValueAt(row, 3).toString());
                    txtThoiGianKetThuc.setText(tableModel.getValueAt(row, 4).toString());
                    txtTrangThaiHoatDong.setText(tableModel.getValueAt(row, 5).toString());
                    txtMucDoCuonHut.setText(tableModel.getValueAt(row, 6).toString());
                    txtGioiHanDoTuoi.setText(tableModel.getValueAt(row, 7).toString());
                    txtSucChua.setText(tableModel.getValueAt(row, 8).toString());
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
        ArrayList<SuKien> list = controller.getAllSuKien();
        for (SuKien sk : list) {
            String tenDichVu = "";
            for (DichVu dv : listDichVu) {
                if (dv.getMaDichVu().equals(sk.getMaDichVu())) {
                    tenDichVu = dv.getTenDichVu();
                    break;
                }
            }
            tableModel.addRow(new Object[]{
                    sk.getMaSuKien(),
                    tenDichVu,
                    sk.getTenSuKien(),
                    sk.getThoiGianBatDau(),
                    sk.getThoiGianKetThuc(),
                    sk.getTrangThaiHoatDong(),
                    sk.getMucDoCuonHut(),
                    sk.getGioiHanDoTuoi(),
                    sk.getSucChua()
            });
        }
    }

    private void timKiemSuKien() {
        String maTim = txtTimKiem.getText().trim();
        tableModel.setRowCount(0);

        if (maTim.isEmpty()) {
            ArrayList<SuKien> list = controller.getAllSuKien();
            for (SuKien sk : list) {
                String tenDichVu = "";
                for (DichVu dv : listDichVu) {
                    if (dv.getMaDichVu().equals(sk.getMaDichVu())) {
                        tenDichVu = dv.getTenDichVu();
                        break;
                    }
                }
                tableModel.addRow(new Object[]{
                        sk.getMaSuKien(),
                        tenDichVu,
                        sk.getTenSuKien(),
                        sk.getThoiGianBatDau(),
                        sk.getThoiGianKetThuc(),
                        sk.getTrangThaiHoatDong(),
                        sk.getMucDoCuonHut(),
                        sk.getGioiHanDoTuoi(),
                        sk.getSucChua()
                });
            }
        } else {
            ArrayList<SuKien> list = controller.timKiemSuKien(maTim);
            for (SuKien sk : list) {
                String tenDichVu = "";
                for (DichVu dv : listDichVu) {
                    if (dv.getMaDichVu().equals(sk.getMaDichVu())) {
                        tenDichVu = dv.getTenDichVu();
                        break;
                    }
                }
                tableModel.addRow(new Object[]{
                        sk.getMaSuKien(),
                        tenDichVu,
                        sk.getTenSuKien(),
                        sk.getThoiGianBatDau(),
                        sk.getThoiGianKetThuc(),
                        sk.getTrangThaiHoatDong(),
                        sk.getMucDoCuonHut(),
                        sk.getGioiHanDoTuoi(),
                        sk.getSucChua()
                });
            }
        }
    }
    private void addSuKien() {
        SuKien sk = getInputSuKien();
        if (sk != null && controller.addSuKien(sk)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại");
        }
    }

    private void updateSuKien() {
        SuKien sk = getInputSuKien();
        if (sk != null && controller.updateSuKien(sk)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            loadData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
        }
    }

    private void deleteSuKien() {
        String ma = txtMaSuKien.getText();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sự kiện để xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa sự kiện có mã " + ma + "?", "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.deleteSuKien(ma)) {
                JOptionPane.showMessageDialog(this, "Xoá thành công");
                loadData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Xoá thất bại");
            }
        }
    }

    private SuKien getInputSuKien() {
        try {
            String maSuKien = txtMaSuKien.getText().trim();
            String tenDichVu = (String) comboDichVu.getSelectedItem();
            String maDichVu = "";

            for (DichVu dv : listDichVu) {
                if (dv.getTenDichVu().equals(tenDichVu)) {
                    maDichVu = dv.getMaDichVu();
                    break;
                }
            }

            String tenSuKien = txtTenSuKien.getText().trim();
            String thoiGianBatDau = txtThoiGianBatDau.getText().trim();
            String thoiGianKetThuc = txtThoiGianKetThuc.getText().trim();
            String trangThaiHoatDong = txtTrangThaiHoatDong.getText().trim();
            String mucDoCuonHut = txtMucDoCuonHut.getText().trim();
            String gioiHanDoTuoi = txtGioiHanDoTuoi.getText().trim();
            int sucChua = Integer.parseInt(txtSucChua.getText().trim());

            if (maSuKien.isEmpty() || maDichVu.isEmpty() || tenSuKien.isEmpty()
                    || thoiGianBatDau.isEmpty() || thoiGianKetThuc.isEmpty()
                    || trangThaiHoatDong.isEmpty() || mucDoCuonHut.isEmpty()
                    || gioiHanDoTuoi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.");
                return null;
            }

            if (!thoiGianBatDau.matches("\\d{4}-\\d{2}-\\d{2}")
                    || !thoiGianKetThuc.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Thời gian phải theo định dạng yyyy-MM-dd.");
                return null;
            }

            return new SuKien(maSuKien, maDichVu, tenSuKien,
                    thoiGianBatDau, thoiGianKetThuc,
                    trangThaiHoatDong, mucDoCuonHut,
                    gioiHanDoTuoi, sucChua);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Sức chứa phải là số nguyên!");
            return null;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi. Vui lòng kiểm tra lại dữ liệu!");
            ex.printStackTrace();
            return null;
        }
    }

    private void clearFields() {
        txtMaSuKien.setText("");
        txtMaSuKien.setEnabled(true);
        comboDichVu.setSelectedIndex(-1);
        txtTenSuKien.setText("");
        txtThoiGianBatDau.setText("");
        txtThoiGianKetThuc.setText("");
        txtTrangThaiHoatDong.setText("");
        txtMucDoCuonHut.setText("");
        txtGioiHanDoTuoi.setText("");
        txtSucChua.setText("");
    }
}
