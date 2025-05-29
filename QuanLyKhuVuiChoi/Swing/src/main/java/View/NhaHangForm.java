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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

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
        setBackground(new Color(0xE3F2FD));

        JPanel panelTop = new JPanel(new GridBagLayout());
        LineBorder lineBorder = new LineBorder(new Color(0x1E88E5), 2);
        TitledBorder titledBorderTop = BorderFactory.createTitledBorder(lineBorder, "Thông tin Nhà Hàng");
        titledBorderTop.setTitleColor(new Color(0x1E88E5));
        
        panelTop.setBorder(titledBorderTop);
        panelTop.setBackground(new Color(0xE3F2FD));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panelTop.add(new JLabel("Mã Nhà Hàng:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelTop.add(txtMaNhaHang = new JTextField(20), gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelTop.add(new JLabel("Tên Dịch Vụ:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelTop.add(comboDichVu = new JComboBox<>(), gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelTop.add(new JLabel("Giờ Mở Cửa:"), gbc);
        gbc.gridx = 1;
        panelTop.add(txtGioMoCua = new JTextField(20), gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelTop.add(new JLabel("Giờ Đóng Cửa:"), gbc);
        gbc.gridx = 1;
        panelTop.add(txtGioDongCua = new JTextField(20), gbc);

        gbc.gridx = 0; gbc.gridy++;
        panelTop.add(new JLabel("Loại Nhà Hàng:"), gbc);
        gbc.gridx = 1;
        panelTop.add(txtLoaiNhaHang = new JTextField(20), gbc);

        add(panelTop);

        JPanel panelTable = new JPanel(new BorderLayout());
        TitledBorder titledBorderTable = BorderFactory.createTitledBorder(lineBorder, "Danh Sách Nhà Hàng");
        titledBorderTable.setTitleColor(new Color(0x1E88E5));
        panelTable.setBorder(titledBorderTable);

        panelTable.setBackground(new Color(0xE3F2FD));

        tableModel = new DefaultTableModel(new Object[]{
            "Mã Nhà Hàng", "Tên Dịch Vụ", "Giờ Mở Cửa", "Giờ Đóng Cửa", "Loại Nhà Hàng"
        }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(0x90CAF9));
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(0xE1F5FE));
                }
                return c;
            }
        });

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0x1E88E5));
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 13f));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panelTable.add(scrollPane, BorderLayout.CENTER);
        panelTable.setPreferredSize(new Dimension(950, 250));
        add(panelTable);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelButtons.setBackground(new Color(0xE3F2FD));

        txtTimKiem = new JTextField(20);
        JButton btnTimKiem = createStyledButton("Tìm kiếm");
        JButton btnAdd = createStyledButton("Thêm");
        JButton btnUpdate = createStyledButton("Sửa");
        JButton btnDelete = createStyledButton("Xoá");
        JButton btnClear = createStyledButton("Làm mới");

        panelButtons.add(new JLabel("Mã hoặc Loại Nhà Hàng:"));
        panelButtons.add(txtTimKiem);
        panelButtons.add(btnTimKiem);
        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);
        add(panelButtons);

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
                    comboDichVu.setSelectedItem(tableModel.getValueAt(row, 1).toString());
                    txtGioMoCua.setText(tableModel.getValueAt(row, 2).toString());
                    txtGioDongCua.setText(tableModel.getValueAt(row, 3).toString());
                    txtLoaiNhaHang.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(new Color(0x1E88E5)); 
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(100, 30));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0x64B5F6)); 
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0x1E88E5)); 
            }
        });

        return button;
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
