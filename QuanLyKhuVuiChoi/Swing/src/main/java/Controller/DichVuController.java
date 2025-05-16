package Controller;

import DAO.DichVuDAO;
import Model.DichVu;

import java.sql.Connection;
import java.util.ArrayList;

public class DichVuController {
    private DichVuDAO dichVuDAO;

    public DichVuController(Connection conn) {
        this.dichVuDAO = new DichVuDAO(conn);
    }

    public ArrayList<DichVu> layTatCaDichVu() {
        return dichVuDAO.getAll();
    }

    public boolean themDichVu(DichVu dichVu) {
        return dichVuDAO.addDichVu(dichVu);
    }

    public boolean capNhatDichVu(DichVu dichVu) {
        return dichVuDAO.updateDichVu(dichVu);
    }

    public boolean xoaDichVu(String maDichVu) {
        return dichVuDAO.deleteDichVu(maDichVu);
    }

    public DichVu timDichVuTheoMa(String maDichVu) {
        return dichVuDAO.getById(maDichVu);
    }
}
