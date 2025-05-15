package DAO;


import Model.CaLamViec;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CaLamViecDAO {
    private Connection conn;

    public CaLamViecDAO(Connection conn) {
        this.conn = conn;
    }

    // Lấy tất cả ca làm
    public List<CaLamViec> layTatCaCa() {
        List<CaLamViec> dsCa = new ArrayList<>();
        String sql = "SELECT * FROM calamViec";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CaLamViec ca = new CaLamViec(
                    rs.getString("maCa"),
                    rs.getString("tenca"),
                    rs.getTime("gio_batdau"),
                    rs.getTime("gio_ketthuc")
                );
                dsCa.add(ca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsCa;
    }
}
