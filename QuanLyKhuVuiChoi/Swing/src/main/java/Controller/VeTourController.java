package Controller;

import DAO.VeTourDAO;
import Model.VeTour;
import View.VeTourForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;

public class VeTourController {
    private VeTourForm view;
    private VeTourDAO dao;
    private Connection conn;

    public VeTourController(VeTourForm view, Connection conn) {
        this.view = view;
        this.dao = new VeTourDAO(conn);
        this.conn= conn;
        loadTable();

        // Gắn sự kiện
        view.getBtnAdd().addActionListener(e -> themVeTour());
        view.getBtnEdit().addActionListener(e -> suaVeTour());
        view.getBtnDelete().addActionListener(e -> xoaVeTour());
        view.getBtnSearch().addActionListener(e -> timKiemVeTour());

        // Sự kiện click bảng -> đổ dữ liệu lên form
        view.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = view.getTable().getSelectedRow();
                if (row >= 0) {
                    DefaultTableModel model = view.getModel();
                    view.getTxtMaVeTour().setText(model.getValueAt(row, 0).toString());
                    view.getTxtMaDatVe().setText(model.getValueAt(row, 1).toString());
                    view.getTxtMaTour().setText(model.getValueAt(row, 2).toString());
                    view.getTxtSoLuongNguoi().setText(model.getValueAt(row, 3).toString());
                    view.getTxtPhuongTien().setText(model.getValueAt(row, 4).toString());
                    view.getTxtGioDi().setText(model.getValueAt(row, 5).toString());
                }
            }
        });
    }

    private void loadTable() {
        List<VeTour> list = dao.getAllVeTour();
        view.setTableData(list);
    }

    private void themVeTour() {
        try {
            VeTour vt = getVeTourFromForm();
            if (dao.insert(vt)) {
                JOptionPane.showMessageDialog(view, "Thêm thành công!");
                loadTable();
                view.clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Thêm thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + e.getMessage());
        }
    }

    private void suaVeTour() {
        try {
            VeTour vt = getVeTourFromForm();
            if (dao.update(vt)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadTable();
                view.clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + e.getMessage());
        }
    }

    private void xoaVeTour() {
        String ma = view.getTxtMaVeTour().getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập Mã Vé Tour cần xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(ma)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadTable();
                view.clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!");
            }
        }
    }

    private void timKiemVeTour() {
        String ma = view.getTxtMaVeTour().getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập Mã Vé Tour cần tìm.");
            return;
        }

        VeTour vt = dao.findById(ma);
        if (vt != null) {
            view.clearForm();
            view.getTxtMaVeTour().setText(vt.getMaVeTour());
            view.getTxtMaDatVe().setText(vt.getMaDatVe());
            view.getTxtMaTour().setText(vt.getMaTour());
            view.getTxtSoLuongNguoi().setText(String.valueOf(vt.getSoLuongNguoi()));
            view.getTxtPhuongTien().setText(vt.getPhuongTien());
            view.getTxtGioDi().setText(vt.getGioDi());
        } else {
            JOptionPane.showMessageDialog(view, "Không tìm thấy vé tour!");
        }
    }

    private VeTour getVeTourFromForm() {
        return new VeTour(
                view.getTxtMaVeTour().getText().trim(),
                view.getTxtMaDatVe().getText().trim(),
                view.getTxtMaTour().getText().trim(),
                Integer.parseInt(view.getTxtSoLuongNguoi().getText().trim()),
                view.getTxtPhuongTien().getText().trim(),
                view.getTxtGioDi().getText().trim()
        );
    }
}
