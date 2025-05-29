
package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

import Controller.HoaDonController;
import Model.HoaDon;

public class HoaDonPanel extends JPanel {

    private JTable tblHoaDon;
    private DefaultTableModel model;
    private JButton btnXemChiTiet, btnXuatPhieu, btnLamMoi;
    private HoaDonController hoaDonController;

    public HoaDonPanel(Connection conn) {
        setLayout(new BorderLayout());

        hoaDonController = new HoaDonController(conn);

        JPanel pnlTableWrapper = new JPanel(new BorderLayout());
        pnlTableWrapper.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(30, 136, 229)),
                "Thông tin danh sách hóa đơn",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                new Color(30, 136, 229)
        ));
        pnlTableWrapper.setBackground(new Color(227, 242, 253));

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Mã Đặt Vé", "Tên Khách Hàng", "Tháng/Năm Đặt", "Số Người", "Tổng Tiền (VND)", "Sử Dụng Voucher"
        });

        tblHoaDon = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    Color evenColor = new Color(245, 250, 255);
                    Color oddColor = Color.WHITE;
                    comp.setBackground(row % 2 == 0 ? evenColor : oddColor);
                } else {
                    comp.setBackground(new Color(179, 212, 255));
                }
                return comp;
            }
        };

        tblHoaDon.setFont(new Font("Arial", Font.PLAIN, 14));
        tblHoaDon.setRowHeight(24);

        JTableHeader header = tblHoaDon.getTableHeader();
        header.setBackground(new Color(25, 118, 210)); 
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        tblHoaDon.setBackground(new Color(227, 242, 253));

        pnlTableWrapper.add(new JScrollPane(tblHoaDon), BorderLayout.CENTER);
        add(pnlTableWrapper, BorderLayout.CENTER);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButtons.setBackground(new Color(227, 242, 253));

        btnXemChiTiet = new JButton("Xem chi tiết");
        btnXuatPhieu = new JButton("Xuất phiếu");
        btnLamMoi = new JButton("Làm mới");

        pnlButtons.add(btnXemChiTiet);
        pnlButtons.add(btnXuatPhieu);
        pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);

        btnXemChiTiet.addActionListener(e -> xuLyXemChiTiet());
        btnLamMoi.addActionListener(e -> loadData());
        btnXuatPhieu.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Chức năng xuất phiếu sẽ được phát triển sau.");
        });

        loadData();
    }

    private void xuLyXemChiTiet() {
        int selectedRow = tblHoaDon.getSelectedRow();
        List<HoaDon> list = hoaDonController.getAllHoaDon();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn để xem chi tiết.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maDatVe = model.getValueAt(selectedRow, 0).toString();

        HoaDon hd = null;
        for (HoaDon h : list) {
            if (h.getMaDatVe().equals(maDatVe)) {
                hd = h;
                break;}
        }
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin chi tiết cho hóa đơn đã chọn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        java.text.SimpleDateFormat sdfDate = new java.text.SimpleDateFormat("dd/MM/yyyy");
        String message = "Mã Đặt Vé: " + hd.getMaDatVe() +
                "\nTên Khách Hàng: " + hd.getTenKhachHang() +
                "\nNgày Đặt: " + (hd.getNgayDat() != null ? sdfDate.format(hd.getNgayDat()) : "") +
                "\nNgày Đi: " + (hd.getNgayDi() != null ? sdfDate.format(hd.getNgayDi()) : "") +
                "\nSố Người Lớn: " + hd.getNguoiLon() +
                "\nSố Trẻ Em: " + hd.getTreEm() +
                "\nSố Người Già: " + hd.getNguoiGia() +
                "\nGiá Tour (1 người): " + String.format("%,.0f", hd.getGiaTour()) + " VND" +
                "\nThành Tiền Tour: " + String.format("%,.0f", hd.getThanhTienTour()) + " VND" +
                "\nThành Tiền Dịch Vụ: " + String.format("%,.0f", hd.getThanhTienDichVu()) + " VND" +
                "\nTổng Tiền: " + String.format("%,.0f", hd.getTongTien()) + " VND";

        JTextArea textArea = new JTextArea(message);
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        textArea.setEditable(false);
        textArea.setBackground(new Color(240, 240, 240));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(360, 280));

        JOptionPane.showMessageDialog(this, scrollPane, "Chi tiết hóa đơn", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadData() {
        model.setRowCount(0);
        List<HoaDon> list = hoaDonController.getAllHoaDon();
        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu hóa đơn để hiển thị.");
            return;
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM");
        for (HoaDon hd : list) {
            String thangNam = "";
            if (hd.getNgayDat() != null) {
                thangNam = sdf.format(hd.getNgayDat());
            }
            String suDungVoucher = "Không";
            model.addRow(new Object[]{
                    hd.getMaDatVe(),
                    hd.getTenKhachHang(),
                    thangNam,
                    hd.getNguoiLon() + hd.getTreEm() + hd.getNguoiGia(), 
                    String.format("%,.0f", hd.getTongTien()), 
                    suDungVoucher
            });
        }
    }
}
