package Controller;

import Model.SuKien;
import DAO.SuKienDAO;
import java.sql.Connection;
import java.util.ArrayList;

public class SuKienController {
    private SuKienDAO suKienDAO;

    public SuKienController(Connection conn) {
        this.suKienDAO = new SuKienDAO(conn);
    }

    public ArrayList<SuKien> getAllSuKien() {
        return suKienDAO.getAll();
    }

    public boolean addSuKien(SuKien sk) {
        return suKienDAO.add(sk);
    }

    public boolean updateSuKien(SuKien sk) {
        return suKienDAO.update(sk);
    }

    public boolean deleteSuKien(String maSuKien) {
        return suKienDAO.delete(maSuKien);
    }
    public ArrayList<SuKien> timKiemSuKien(String keyword) {
        return suKienDAO.timKiem(keyword);
    }
}
