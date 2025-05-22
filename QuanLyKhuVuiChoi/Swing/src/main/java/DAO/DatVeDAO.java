package DAO;


import Model.DatVe;
import java.sql.*;
import java.util.*;

public class DatVeDAO {
    private Connection conn;

    public DatVeDAO(Connection conn) {
        this.conn = conn;
    }

    public List<String> getAllMaDatVe() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT MaDatVe FROM DatVe";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("MaDatVe"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
