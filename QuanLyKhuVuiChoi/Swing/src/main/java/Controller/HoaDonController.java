package Controller;

import DAO.HoaDonDAO;
import Model.HoaDon;

import java.sql.Connection;
import java.util.List;

public class HoaDonController {
    private HoaDonDAO hoaDonDAO;

    public HoaDonController(Connection conn) {
        hoaDonDAO = new HoaDonDAO(conn);
    }

    public List<HoaDon> getAllHoaDon() {
        return hoaDonDAO.getAll();
    }
}
