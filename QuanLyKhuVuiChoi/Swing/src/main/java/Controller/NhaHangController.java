package Controller;

import DAO.NhaHangDAO;
import Model.NhaHang;

import java.sql.Connection;
import java.util.ArrayList;

public class NhaHangController {
    private NhaHangDAO dao;

    public NhaHangController(Connection conn) {
        dao = new NhaHangDAO(conn);
    }

    public ArrayList<NhaHang> getAllNhaHang() {
        return dao.getAll();
    }

    public boolean addNhaHang(NhaHang nh) {
        return dao.add(nh);
    }

    public boolean updateNhaHang(NhaHang nh) {
        return dao.update(nh);
    }

    public boolean deleteNhaHang(String maNhaHang) {
        return dao.delete(maNhaHang);
    }

    public ArrayList<NhaHang> searchNhaHang(String keyword) {
        return dao.search(keyword);
    }
    
}
