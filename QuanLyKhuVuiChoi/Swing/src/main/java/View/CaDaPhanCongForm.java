package View;

import DAO.PhanCongCaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class CaDaPhanCongForm extends JDialog {
    private JTable table;
    private DefaultTableModel model;
    private Connection conn;
    private PhanCongCaDAO phanCongCaDAO;

    public CaDaPhanCongForm(JFrame parent, String maNhanVien, Connection conn) {
        super(parent, "Ca đã phân công", true);
        this.conn = conn;
        this.phanCongCaDAO = new PhanCongCaDAO(conn);

        setSize(600, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"Mã NV", "Tên ca", "Ngày làm", "Giờ bắt đầu", "Giờ kết thúc"});

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData(maNhanVien);
    }

    private void loadData(String maNV) {
        List<Object[]> list = phanCongCaDAO.getCaDaPhanCongTheoNhanVien(maNV);
        model.setRowCount(0);
        for (Object[] row : list) {
            model.addRow(row);
        }
    }
}
