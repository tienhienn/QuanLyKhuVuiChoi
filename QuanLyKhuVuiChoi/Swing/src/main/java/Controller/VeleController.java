package Controller;

import DAO.VeleDAO;
import Model.Vele;
import View.VelePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.util.List;

public class VeleController {
    private VeleDAO veleDAO;
    private VelePanel velePanel;
    private Connection conn;

    public VeleController(VelePanel velePanel, Connection conn) {
        this.veleDAO = new VeleDAO(conn);
        this.velePanel = velePanel;
        this.conn=conn;

        initController();
        loadAllData();
    }

    private void initController() {
        velePanel.getBtnThem().addActionListener(e -> themVele());
        velePanel.getBtnSua().addActionListener(e -> suaVele());
        velePanel.getBtnXoa().addActionListener(e -> xoaVele());
        velePanel.getBtnTim().addActionListener(e -> timKiemVele());

        velePanel.getTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = velePanel.getTable().getSelectedRow();
                DefaultTableModel model = velePanel.getModel();
                velePanel.getTxtMaVele().setText(model.getValueAt(row, 0).toString());
                velePanel.getTxtMaDatVe().setText(model.getValueAt(row, 1).toString());
                velePanel.getTxtDoiTuong().setText(model.getValueAt(row, 2).toString());
                velePanel.getTxtSoLuong().setText(model.getValueAt(row, 3).toString());
            }
        });
    }

    public void loadAllData() {
        List<Vele> list = veleDAO.getAllVele();
        velePanel.setTableData(list);
    }

    private void themVele() {
        Vele v = getInputFromForm();
        if (v == null) return;

        if (veleDAO.addVele(v)) {
            showMessage("Thêm vé lẻ thành công!");
            loadAllData();
            clearForm();
        } else {
            showMessage("Thêm thất bại! Mã vé lẻ đã tồn tại?");
        }
    }

    private void suaVele() {
        Vele v = getInputFromForm();
        if (v == null) return;

        if (veleDAO.updateVele(v)) {
            showMessage("Cập nhật thành công!");
            loadAllData();
        } else {
            showMessage("Cập nhật thất bại!");
        }
    }

    private void xoaVele() {
        String maVele = velePanel.getTxtMaVele().getText().trim();
        if (maVele.isEmpty()) {
            showMessage("Vui lòng nhập mã vé lẻ cần xoá!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xoá?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (veleDAO.deleteVele(maVele)) {
                showMessage("Xoá thành công!");
                loadAllData();
                clearForm();
            } else {
                showMessage("Xoá thất bại!");
            }
        }
    }

    private void timKiemVele() {
        String keyword = velePanel.getTxtMaVele().getText().trim();
        if (keyword.isEmpty()) {
            showMessage("Nhập mã vé lẻ cần tìm!");
            return;
        }

        Vele v = veleDAO.findByMaVele(keyword);
        Vele vl = (Vele) veleDAO.findByMaDatVe(keyword);
        if (v != null) {
            velePanel.setTableData(List.of(v));
        } else if (vl != null) {
            velePanel.setTableData(List.of(vl));
        } else {
            showMessage("Không tìm thấy vé lẻ!");
        }
    }

    private Vele getInputFromForm() {
        try {
            String maVele = velePanel.getTxtMaVele().getText().trim();
            String maDatVe = velePanel.getTxtMaDatVe().getText().trim();
            String doiTuong = velePanel.getTxtDoiTuong().getText().trim();
            int soLuong = Integer.parseInt(velePanel.getTxtSoLuong().getText().trim());

            if (maVele.isEmpty() || maDatVe.isEmpty() || doiTuong.isEmpty()) {
                showMessage("Vui lòng nhập đầy đủ thông tin!");
                return null;
            }

            return new Vele(maVele, maDatVe, doiTuong, soLuong);
        } catch (NumberFormatException e) {
            showMessage("Số lượng người phải là số nguyên!");
            return null;
        }
    }

    private void clearForm() {
        velePanel.getTxtMaVele().setText("");
        velePanel.getTxtMaDatVe().setText("");
        velePanel.getTxtDoiTuong().setText("");
        velePanel.getTxtSoLuong().setText("");
    }

    private void showMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
}
