package View;

import Model.KhachHang;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KhachHangPanel extends JPanel {
    public JTable table;
    private DefaultTableModel model;

    public JButton btnAdd, btnEdit, btnDelete, btnRefresh;

    public KhachHangPanel() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        table = new JTable(model);
        model.setColumnIdentifiers(new Object[]{"Mã", "Tên", "Mật khẩu", "SDT", "Email"});

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelButtons = new JPanel(new FlowLayout());
        btnAdd = new JButton("Thêm");
        btnEdit = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnRefresh = new JButton("Làm mới");

        panelButtons.add(btnAdd);
        panelButtons.add(btnEdit);
        panelButtons.add(btnDelete);
        panelButtons.add(btnRefresh);
        add(panelButtons, BorderLayout.SOUTH);
    }

    public void loadData(List<KhachHang> khachHangs) {
        model.setRowCount(0);
        for (KhachHang kh : khachHangs) {
            model.addRow(new Object[]{
                    kh.getMaKhachHang(),
                    kh.getTenKhachHang(),
                    kh.getMatKhau(),
                    kh.getSdt(),
                    kh.getEmail()
            });
        }
    }
}
