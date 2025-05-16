package Controller;

import Model.TroChoi;
import DAO.TroChoiDAO;
import java.sql.Connection;
import java.util.ArrayList;

public class TroChoiController {
    private TroChoiDAO troChoiDAO;

    public TroChoiController(Connection conn) {
        this.troChoiDAO = new TroChoiDAO(conn);
    }

    public ArrayList<TroChoi> getAllTroChoi() {
        return troChoiDAO.getAll();
    }

    public boolean addTroChoi(TroChoi tc) {
        return troChoiDAO.add(tc);
    }

    public boolean updateTroChoi(TroChoi tc) {
        return troChoiDAO.update(tc);
    }

    public boolean deleteTroChoi(String maTroChoi) {
        return troChoiDAO.delete(maTroChoi);
    }
    public ArrayList<TroChoi> timKiemTroChoi(String keyword) {
        return troChoiDAO.timKiem(keyword);
    }
}
