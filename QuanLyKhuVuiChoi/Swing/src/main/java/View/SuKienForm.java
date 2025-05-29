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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

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
    setLayout(new BorderLayout(10, 10));
    setBackground(new Color(0xE3F2FD));

    // -- Panel Thông tin Sự kiện --
    JPanel panelTop = new JPanel(new GridLayout(5, 4, 10, 10));
    // Border viền xanh, dày 2 px, bo tròn
    panelTop.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(0x1E88E5), 2, true),
        "Thông tin Sự Kiện",
        TitledBorder.LEFT,
        TitledBorder.TOP,
        new Font("Segoe UI", Font.BOLD, 16),
        new Color(0x1E88E5)
    ));
    panelTop.setBackground(new Color(0xE3F2FD));

    txtMaSuKien = createStyledTextField();
    comboDichVu = new JComboBox<>();
    txtTenSuKien = createStyledTextField();
    txtThoiGianBatDau = createStyledTextField();
    txtThoiGianKetThuc = createStyledTextField();
    txtTrangThaiHoatDong = createStyledTextField();
    txtMucDoCuonHut = createStyledTextField();
    txtGioiHanDoTuoi = createStyledTextField();
    txtSucChua = createStyledTextField();

    panelTop.add(new JLabel("Mã Sự Kiện:")); panelTop.add(txtMaSuKien);
    panelTop.add(new JLabel("Tên Dịch Vụ:")); panelTop.add(comboDichVu);
    panelTop.add(new JLabel("Tên Sự Kiện:")); panelTop.add(txtTenSuKien);
    panelTop.add(new JLabel("Thời Gian Bắt Đầu (dd/MM/yyyy):")); panelTop.add(txtThoiGianBatDau);
    panelTop.add(new JLabel("Thời Gian Kết Thúc (dd/MM/yyyy):")); panelTop.add(txtThoiGianKetThuc);
    panelTop.add(new JLabel("Trạng Thái Hoạt Động:")); panelTop.add(txtTrangThaiHoatDong);
    panelTop.add(new JLabel("Mức Độ Cuốn Hút:")); panelTop.add(txtMucDoCuonHut);
    panelTop.add(new JLabel("Giới Hạn Độ Tuổi:")); panelTop.add(txtGioiHanDoTuoi);
    panelTop.add(new JLabel("Sức Chứa:")); panelTop.add(txtSucChua);
    panelTop.add(new JLabel()); panelTop.add(new JLabel());

    add(panelTop, BorderLayout.NORTH);

    // -- Table Danh Sách Sự Kiện --
    tableModel = new DefaultTableModel(new Object[]{
        "Mã Sự Kiện", "Tên Dịch Vụ", "Tên Sự Kiện", "Thời Gian Bắt Đầu",
        "Thời Gian Kết Thúc", "Trạng Thái", "Mức Độ Cuốn Hút", "Giới Hạn Tuổi", "Sức Chứa"
    }, 0);
    table = new JTable(tableModel);
    styleTable(table);

    JScrollPane scrollPane = new JScrollPane(table);
    // Border viền xanh, dày 2 px, bo tròn cho scrollPane
    scrollPane.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(0x1E88E5), 2, true),
        "Danh Sách Sự Kiện",
            TitledBorder.LEFT,
        TitledBorder.TOP,
        new Font("Segoe UI", Font.BOLD, 15),
        new Color(0x1E88E5)
    ));
    scrollPane.setPreferredSize(new Dimension(900, 250));
    table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

    add(scrollPane, BorderLayout.CENTER);

    // -- Panel nút bấm --
    JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
    panelButtons.setBackground(new Color(0xE3F2FD));

    txtTimKiem = new JTextField(20);
    JButton btnTimKiem = createStyledButton("Tìm kiếm");
    JButton btnAdd = createStyledButton("Thêm");
    JButton btnUpdate = createStyledButton("Sửa");
    JButton btnDelete = createStyledButton("Xoá");
    JButton btnClear = createStyledButton("Làm mới");

    panelButtons.add(new JLabel("Mã Sự Kiện:"));
    panelButtons.add(txtTimKiem);
    panelButtons.add(btnTimKiem);
    panelButtons.add(btnAdd);
    panelButtons.add(btnUpdate);
    panelButtons.add(btnDelete);
    panelButtons.add(btnClear);
    add(panelButtons, BorderLayout.SOUTH);

    // -- Action listeners --
    btnTimKiem.addActionListener(e -> timKiemSuKien());
    btnAdd.addActionListener(e -> addSuKien());
    btnUpdate.addActionListener(e -> updateSuKien());
    btnDelete.addActionListener(e -> deleteSuKien());
    btnClear.addActionListener(e -> clearFields());

    // -- Bắt sự kiện chọn dòng bảng --
    table.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            int row = table.getSelectedRow();
            if (row >= 0) {
                txtMaSuKien.setText(tableModel.getValueAt(row, 0).toString());
                txtMaSuKien.setEnabled(false);
                comboDichVu.setSelectedItem(tableModel.getValueAt(row, 1).toString());
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

    private JTextField createStyledTextField() {
        JTextField txt = new JTextField();
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x90CAF9)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return txt;
    }

    private JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(0x1E88E5));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0x64B5F6));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(0x1E88E5));
            }
        });
        return btn;
    }

    private void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0x1976D2));
        header.setForeground(Color.WHITE);

        table.setRowHeight(22);
        table.setSelectionBackground(new Color(0xBBDEFB));
        table.setGridColor(new Color(0x90CAF9));
        table.setShowGrid(true);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xE3F2FD));
                }
                return c;
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
            String thoiGianBatDauDisplay = convertDateFormatToDisplay(sk.getThoiGianBatDau());
            String thoiGianKetThucDisplay = convertDateFormatToDisplay(sk.getThoiGianKetThuc());
            
            tableModel.addRow(new Object[]{
                    sk.getMaSuKien(),
                    tenDichVu,
                    sk.getTenSuKien(),
                    thoiGianBatDauDisplay,
                    thoiGianKetThucDisplay,
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
                String thoiGianBatDauDisplay = convertDateFormatToDisplay(sk.getThoiGianBatDau());
                String thoiGianKetThucDisplay = convertDateFormatToDisplay(sk.getThoiGianKetThuc());
                
                tableModel.addRow(new Object[]{
                        sk.getMaSuKien(),
                        tenDichVu,
                        sk.getTenSuKien(),
                        thoiGianBatDauDisplay,
                        thoiGianKetThucDisplay,
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
                String thoiGianBatDauDisplay = convertDateFormatToDisplay(sk.getThoiGianBatDau());
                String thoiGianKetThucDisplay = convertDateFormatToDisplay(sk.getThoiGianKetThuc());
                
                tableModel.addRow(new Object[]{
                        sk.getMaSuKien(),
                        tenDichVu,
                        sk.getTenSuKien(),
                        thoiGianBatDauDisplay,
                        thoiGianKetThucDisplay,
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

            if (!thoiGianBatDau.matches("\\d{2}/\\d{2}/\\d{4}")
                    || !thoiGianKetThuc.matches("\\d{2}/\\d{2}/\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Thời gian phải theo định dạng dd/MM/yyyy.");
                return null;
            }
            String thoiGianBatDauDB = convertDateFormat(thoiGianBatDau);
            String thoiGianKetThucDB = convertDateFormat(thoiGianKetThuc);

            return new SuKien(maSuKien, maDichVu, tenSuKien,
                    thoiGianBatDauDB, thoiGianKetThucDB,
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
    private String convertDateFormat(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "";
        }
        try {
            String[] parts = dateStr.split("/");
            if (parts.length != 3) {
                return dateStr;
            }
            String day = parts[0];
            String month = parts[1];
            String year = parts[2];
            return year + "-" + month + "-" + day;
        } catch (Exception e) {
            e.printStackTrace();
            return dateStr;
        }
    }
   
    private String convertDateFormatToDisplay(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return "";
        }
        try {
            String[] parts = dateStr.split("-");
            if (parts.length != 3) {
                return dateStr;
            }
            String year = parts[0];
            String month = parts[1];
            String day = parts[2];
            return day + "/" + month + "/" + year;
        } catch (Exception e) {
            e.printStackTrace();
            return dateStr;
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