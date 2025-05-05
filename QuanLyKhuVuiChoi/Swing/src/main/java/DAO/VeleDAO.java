package DAO;

import Model.Vele;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeleDAO {
    private Connection conn;

    public VeleDAO(Connection conn) {
        this.conn = conn;
    }

    // 1. Lấy toàn bộ danh sách
    public List<Vele> getAllVele() {
        List<Vele> list = new ArrayList<>();
        String sql = "SELECT * FROM vele";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Vele vele = new Vele(
                        rs.getString("maVele"),
                        rs.getString("maDatVe"),
                        rs.getString("doiTuong"),
                        rs.getInt("soLuongNguoi")
                );
                list.add(vele);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 2. Thêm vé lẻ mới
    public boolean addVele(Vele vele) {
        String sql = "INSERT INTO vele(maVele, maDatVe, doiTuong, soLuongNguoi) VALUES(?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vele.getMaVele());
            ps.setString(2, vele.getMaDatVe());
            ps.setString(3, vele.getDoiTuong());
            ps.setInt(4, vele.getSoLuongNguoi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 3. Cập nhật vé lẻ theo maVele
    public boolean updateVele(Vele vele) {
        String sql = "UPDATE vele SET maDatVe = ?, doiTuong = ?, soLuongNguoi = ? WHERE maVele = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vele.getMaDatVe());
            ps.setString(2, vele.getDoiTuong());
            ps.setInt(3, vele.getSoLuongNguoi());
            ps.setString(4, vele.getMaVele());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 4. Xóa vé lẻ theo maVele
    public boolean deleteVele(String maVele) {
        String sql = "DELETE FROM vele WHERE maVele = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVele);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // 5. Tìm vé lẻ theo mã đặt vé
    public List<Vele> findByMaDatVe(String maDatVe) {
        List<Vele> list = new ArrayList<>();
        String sql = "SELECT * FROM vele WHERE maDatVe LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + maDatVe + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vele vele = new Vele(
                        rs.getString("maVele"),
                        rs.getString("maDatVe"),
                        rs.getString("doiTuong"),
                        rs.getInt("soLuongNguoi")
                );
                list.add(vele);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 6. Tìm theo mã vé lẻ (duy nhất)
    public Vele findByMaVele(String maVele) {
        String sql = "SELECT * FROM vele WHERE maVele = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maVele);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Vele(
                        rs.getString("maVele"),
                        rs.getString("maDatVe"),
                        rs.getString("doiTuong"),
                        rs.getInt("soLuongNguoi")
                );
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
