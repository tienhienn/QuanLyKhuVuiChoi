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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

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
        Color mainBackground = new Color(0xE3F2FD);
        setBackground(mainBackground);

        Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font boldFont = new Font("Segoe UI", Font.BOLD, 14);

        JPanel panelTop = new JPanel(new GridLayout(4, 4, 10, 10));
        panelTop.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0x1E88E5), 2, true), "Thông tin Trò Chơi", TitledBorder.LEFT, TitledBorder.TOP, boldFont, new Color(0x1E88E5)));
        panelTop.setBackground(mainBackground);

        txtMaTroChoi = new JTextField(); txtMaTroChoi.setFont(mainFont);
        comboDichVu = new JComboBox<>(); comboDichVu.setFont(mainFont);
        txtTenTroChoi = new JTextField(); txtTenTroChoi.setFont(mainFont);
        txtMoTa = new JTextField(); txtMoTa.setFont(mainFont);
        txtGioiHanTuoi = new JTextField(); txtGioiHanTuoi.setFont(mainFont);
        txtSucChua = new JTextField(); txtSucChua.setFont(mainFont);
        txtThoiGianHoatDong = new JTextField(); txtThoiGianHoatDong.setFont(mainFont);

        JLabel[] labels = {
            new JLabel("Mã Trò Chơi:"), new JLabel("Tên Dịch Vụ:"), new JLabel("Tên Trò Chơi:"),
            new JLabel("Mô Tả:"), new JLabel("Giới Hạn Tuổi:"), new JLabel("Sức Chứa:"),
            new JLabel("Thời Gian Hoạt Động (dd/MM/yyyy):"), new JLabel(), new JLabel(), new JLabel()
        };

        for (JLabel lbl : labels) lbl.setFont(mainFont);

        panelTop.add(labels[0]); panelTop.add(txtMaTroChoi);
        panelTop.add(labels[1]); panelTop.add(comboDichVu);
        panelTop.add(labels[2]); panelTop.add(txtTenTroChoi);
        panelTop.add(labels[3]); panelTop.add(txtMoTa);
        panelTop.add(labels[4]); panelTop.add(txtGioiHanTuoi);
        panelTop.add(labels[5]); panelTop.add(txtSucChua);
        panelTop.add(labels[6]); panelTop.add(txtThoiGianHoatDong);
        panelTop.add(labels[7]); panelTop.add(labels[8]);

        add(panelTop);
        
        tableModel = new DefaultTableModel(new Object[]{
            "Mã Trò Chơi", "Tên Dịch Vụ", "Tên Trò Chơi", "Mô Tả",
            "Giới Hạn Tuổi", "Sức Chứa", "Thời Gian Hoạt Động"
        }, 0);
        table = new JTable(tableModel);
        table.setFont(mainFont);
        table.setRowHeight(24);
        table.getTableHeader().setFont(boldFont);
        table.getTableHeader().setBackground(new Color(0x1E88E5));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFillsViewportHeight(true);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private final Color evenColor = Color.WHITE;
            private final Color oddColor = new Color(0xD6F0F9);
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(new Color(0x1E88E5));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? evenColor : oddColor);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(mainBackground);
        tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0x1E88E5), 2, true), "Danh sách Trò Chơi", TitledBorder.LEFT, TitledBorder.TOP, boldFont, new Color(0x1E88E5)));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.setPreferredSize(new Dimension(900, 250));

        add(tablePanel);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelButtons.setBackground(mainBackground);

        txtTimKiem = new JTextField(15); txtTimKiem.setFont(mainFont);
        JLabel lblTim = new JLabel("Mã Trò Chơi:"); lblTim.setFont(mainFont);

        JButton btnTimKiem = new JButton("Tìm kiếm");
        JButton btnAdd = new JButton("Thêm");
        JButton btnUpdate = new JButton("Sửa");
        JButton btnDelete = new JButton("Xoá");
        JButton btnClear = new JButton("Làm mới");

        JButton[] buttons = {btnTimKiem, btnAdd, btnUpdate, btnDelete, btnClear};
        Color normalBtnBg = new Color(0x3366FF);
        Color hoverBtnBg = new Color(135,206,250);
        for (JButton btn : buttons) {
            btn.setBackground(normalBtnBg);
            btn.setFocusPainted(false);
            btn.setOpaque(true);
            btn.setBorderPainted(false);

            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(hoverBtnBg);
                    btn.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    btn.setBackground(normalBtnBg);
                    btn.setForeground(Color.BLACK);
                }
            });
        }

        panelButtons.add(lblTim);
        panelButtons.add(txtTimKiem);
        panelButtons.add(btnTimKiem);
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);

        add(panelButtons);

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
                    comboDichVu.setSelectedItem(tableModel.getValueAt(row, 1).toString());
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
                if (!thoiGianHoatDong.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    JOptionPane.showMessageDialog(this, "Thời gian hoạt động phải theo định dạng dd/MM/yyyy.");
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
