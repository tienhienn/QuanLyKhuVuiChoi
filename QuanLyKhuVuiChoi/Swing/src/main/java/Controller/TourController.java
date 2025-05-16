package Controller;

import Model.Tour;
import DAO.TourDAO;
import View.TourPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.util.List;

public class TourController {
    private TourPanel view;
    private TourDAO tourDAO;

    public TourController(TourPanel view, Connection conn) {
        this.view = view;
        this.tourDAO = new TourDAO(conn);
        loadDataToTable();

        view.btnThem.addActionListener(e -> themTour());
        view.btnSua.addActionListener(e -> suaTour());
        view.btnXoa.addActionListener(e -> xoaTour());
        view.btnClear.addActionListener(e -> clearForm());
        view.btnTim.addActionListener(e -> timKiemTour());     

        view.table.getSelectionModel().addListSelectionListener(e -> fillFormTuBang());
    }

    private void loadDataToTable() {
        List<Tour> list = tourDAO.getAllTours();
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);
        for (Tour tour : list) {
            model.addRow(new Object[]{
                    tour.getMaTour(),
                    tour.getTenTour(),
                    tour.getMoTa(),
                    tour.getGiaTour(),
                    tour.getTgBatDau() != null ? tour.getTgBatDau().toString() : "",
                    tour.getTgKetThuc() != null ? tour.getTgKetThuc().toString() : "",
                    tour.getSoLuongMax()
            });
        }
    }

    private void fillFormTuBang() {
        int row = view.table.getSelectedRow();
        if (row != -1) {
            view.tfMaTour.setText(view.table.getValueAt(row, 0).toString());
            view.tfTenTour.setText(view.table.getValueAt(row, 1).toString());
            view.tfMieuTa.setText(view.table.getValueAt(row, 2).toString());
            view.tfGiaTour.setText(view.table.getValueAt(row, 3).toString());
            view.tfNgayBatDau.setText(view.table.getValueAt(row, 4).toString());
            view.tfNgayKetThuc.setText(view.table.getValueAt(row, 5).toString());
            view.tfSoLuongMax.setText(view.table.getValueAt(row, 6).toString());
            view.tfMaTour.setEditable(false);
        }
    }

    private void themTour() {
        Tour tour = layDuLieuForm();
        if (tour == null) return;

        if (tourDAO.getTourById(tour.getMaTour()) != null) {
            JOptionPane.showMessageDialog(view, "Mã tour đã tồn tại!");
            return;
        }

        if (tourDAO.themTour(tour)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            loadDataToTable();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!");
        }
    }


    private void suaTour() {
        Tour tour = layDuLieuForm();
        if (tour != null && tourDAO.suaTour(tour)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadDataToTable();
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
        }
    }

    private void xoaTour() {
        String maTour = view.tfMaTour.getText();
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xoá tour này?", "Xác nhận xoá", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (tourDAO.xoaTour(maTour)) {
                JOptionPane.showMessageDialog(view, "Xoá thành công!");
                loadDataToTable();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(view, "Xoá thất bại!");
            }
        }
    }

    private void resetForm() {
        view.tfMaTour.setText("");
        view.tfTenTour.setText("");
        view.tfMieuTa.setText("");
        view.tfGiaTour.setText("");
        view.tfNgayBatDau.setText("");
        view.tfNgayKetThuc.setText("");
        view.tfSoLuongMax.setText("");
    }



    private void timKiemTour() {
        String keyword = view.tfTimKiem.getText();
        List<Tour> list = tourDAO.timKiemTour(keyword);
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);
        for (Tour tour : list) {
            model.addRow(new Object[]{
                    tour.getMaTour(),
                    tour.getTenTour(),
                    tour.getMoTa(),
                    tour.getGiaTour(),
                    tour.getTgBatDau() != null ? tour.getTgBatDau().toString() : "",
                    tour.getTgKetThuc() != null ? tour.getTgKetThuc().toString() : "",
                    tour.getSoLuongMax()
            });
        }
    }

    private Tour layDuLieuForm() {
        try {
            return new Tour(
                    view.tfMaTour.getText(),
                    view.tfTenTour.getText(),
                    view.tfMieuTa.getText(),
                    Double.parseDouble(view.tfGiaTour.getText()),
                    java.sql.Date.valueOf(view.tfNgayBatDau.getText()),
                    java.sql.Date.valueOf(view.tfNgayKetThuc.getText()),
                    Integer.parseInt(view.tfSoLuongMax.getText())
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Dữ liệu không hợp lệ! Vui lòng kiểm tra lại.");
            return null;
        }
    }
    private void clearForm() {
        view.tfMaTour.setText("");
        view.tfTenTour.setText("");
        view.tfMieuTa.setText("");
        view.tfGiaTour.setText("");
        view.tfNgayBatDau.setText("");
        view.tfNgayKetThuc.setText("");
        view.tfSoLuongMax.setText("");
        view.tfMaTour.setEditable(true);  // Cho phép nhập lại mã tour
        view.table.clearSelection();      // Bỏ chọn bảng
    }
}
