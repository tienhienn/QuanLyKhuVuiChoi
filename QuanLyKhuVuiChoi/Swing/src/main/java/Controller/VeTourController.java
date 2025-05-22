package Controller;

import DAO.DatVeDAO;
import DAO.TourDAO;
import DAO.VeTourDAO;
import Model.Tour;
import Model.VeTour;
import View.VeTourPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;

public class VeTourController {
    private VeTourPanel view;
    private VeTourDAO dao;
    private TourDAO tourDAO;
    private DatVeDAO datVeDAO;

    public VeTourController(VeTourPanel view, Connection conn) {
        this.view = view;
        this.dao = new VeTourDAO(conn);
        this.tourDAO = new TourDAO(conn);
        this.datVeDAO = new DatVeDAO(conn);

        loadTourToComboBox();
        loadDatVeToComboBox();
        loadDataToTable();

        view.btnThem.addActionListener(e -> them());
        view.btnSua.addActionListener(e -> sua());
        view.btnXoa.addActionListener(e -> xoa());
        view.btnTim.addActionListener(e -> tim());
        view.btnClear.addActionListener(e -> clearForm());

        view.table.getSelectionModel().addListSelectionListener(e -> fillForm());
    }

    private void loadTourToComboBox() {
        List<Tour> list = tourDAO.getAllTours();
        view.cbTour.removeAllItems();
        for (Tour t : list) {
            view.cbTour.addItem(t);
        }
    }

    private void loadDatVeToComboBox() {
        List<String> list = datVeDAO.getAllMaDatVe(); // Đúng rồi
        view.cbMaDat.removeAllItems();
        for (String maDatVe : list) {
            view.cbMaDat.addItem(maDatVe);
        }
    }

    private void loadDataToTable() {
        List<VeTour> list = dao.getAllVeTour();
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);
        for (VeTour vt : list) {
            model.addRow(new Object[]{
                    vt.getMaVeTour(), vt.getMaDatVe(), vt.getMaTour(),
                    vt.getNguoiLon(), vt.getTreEm(), vt.getNguoiGia()
            });
        }
    }

    private void fillForm() {
        int row = view.table.getSelectedRow();
        if (row != -1) {
            view.tfMaVeTour.setText(view.table.getValueAt(row, 0).toString());

            // Set selected MaDatVe (String)
            String maDatVe = view.table.getValueAt(row, 1).toString();
            view.cbMaDat.setSelectedItem(maDatVe);

            // Set selected Tour (Tour object)
            String maTour = view.table.getValueAt(row, 2).toString();
            for (int i = 0; i < view.cbTour.getItemCount(); i++) {
                Tour t = view.cbTour.getItemAt(i);
                if (t.getMaTour().equals(maTour)) {
                    view.cbTour.setSelectedIndex(i);
                    break;
                }
            }

            view.tfNguoiLon.setText(view.table.getValueAt(row, 3).toString());
            view.tfTreEm.setText(view.table.getValueAt(row, 4).toString());
            view.tfNguoiGia.setText(view.table.getValueAt(row, 5).toString());
            view.tfMaVeTour.setEditable(false);
        }
    }

    private VeTour layDuLieuForm() {
        try {
            String maVeTour = view.tfMaVeTour.getText().trim();

            String maDatVe = (String) view.cbMaDat.getSelectedItem();
            if (maDatVe == null || maDatVe.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Chưa chọn mã đặt vé!");
                return null;
            }

            Tour selectedTour = (Tour) view.cbTour.getSelectedItem();
            if (selectedTour == null) {
                JOptionPane.showMessageDialog(view, "Chưa chọn tour!");
                return null;
            }
            String maTour = selectedTour.getMaTour();

            int nguoiLon = Integer.parseInt(view.tfNguoiLon.getText());
            int treEm = Integer.parseInt(view.tfTreEm.getText());
            int nguoiGia = Integer.parseInt(view.tfNguoiGia.getText());

            return new VeTour(maVeTour, maDatVe, maTour, nguoiLon, treEm, nguoiGia);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Dữ liệu không hợp lệ!");
            return null;
        }
    }

    private void them() {
        VeTour vt = layDuLieuForm();
        if (vt == null) return;
        if (dao.findById(vt.getMaVeTour()) != null) {
            JOptionPane.showMessageDialog(view, "Mã vé tour đã tồn tại!");
            return;
        }
        if (dao.insert(vt)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!");
        }
    }

    private void sua() {
        VeTour vt = layDuLieuForm();
        if (vt != null && dao.update(vt)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
        }
    }

    private void xoa() {
        String maVeTour = view.tfMaVeTour.getText();
        int confirm = JOptionPane.showConfirmDialog(view, "Xóa vé tour này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(maVeTour)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadDataToTable();
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!");
            }
        }
    }

    private void tim() {
        String keyword = view.tfTimKiem.getText();
        List<VeTour> list = dao.getAllVeTour();
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);

        for (VeTour vt : list) {
            if (vt.getMaVeTour().toLowerCase().contains(keyword.toLowerCase())
                    || vt.getMaDatVe().toLowerCase().contains(keyword.toLowerCase())
                    || vt.getMaTour().toLowerCase().contains(keyword.toLowerCase())) {
                model.addRow(new Object[]{
                        vt.getMaVeTour(), vt.getMaDatVe(), vt.getMaTour(),
                        vt.getNguoiLon(), vt.getTreEm(), vt.getNguoiGia()
                });
            }
        }
    }

    private void clearForm() {
        view.tfMaVeTour.setText("");
        view.cbMaDat.setSelectedIndex(-1);
        view.cbTour.setSelectedIndex(-1);
        view.tfNguoiLon.setText("");
        view.tfTreEm.setText("");
        view.tfNguoiGia.setText("");
        view.tfMaVeTour.setEditable(true);
        view.table.clearSelection();
    }
}
