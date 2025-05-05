package DAO;

import Model.Tour;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourDAO {
    private Connection conn;

    public TourDAO(Connection conn) {
        this.conn = conn;
    }

    // Lấy danh sách tất cả các tour
    public List<Tour> getAllTours() {
        List<Tour> list = new ArrayList<>();
        String sql = "SELECT * FROM Tour";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Tour tour = new Tour(
                        rs.getString("maTour"),
                        rs.getString("tenTour"),
                        rs.getString("moTa"),
                        rs.getDouble("giaTour"),
                        rs.getDate("tg_batDau"),
                        rs.getDate("tg_ketThuc"),
                        rs.getInt("soLuongMax"),
                        rs.getInt("soLuongConLai")
                );
                list.add(tour);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm một tour mới
    public boolean themTour(Tour tour) {
        String sql = "INSERT INTO Tour (maTour, tenTour, moTa, giaTour, tg_batDau, tg_ketThuc, soLuongMax, soLuongConLai) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tour.getMaTour());
            ps.setString(2, tour.getTenTour());
            ps.setString(3, tour.getMoTa());
            ps.setDouble(4, tour.getGiaTour());
            ps.setDate(5, tour.getTgBatDau());
            ps.setDate(6, tour.getTgKetThuc());
            ps.setInt(7, tour.getSoLuongMax());
            ps.setInt(8, tour.getSoLuongConLai()); // mới thêm
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cập nhật thông tin tour
    public boolean suaTour(Tour tour) {
        String sql = "UPDATE Tour SET tenTour=?, moTa=?, giaTour=?, tg_batDau=?, tg_ketThuc=?, soLuongMax=?, soLuongConLai=? WHERE maTour=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tour.getTenTour());
            ps.setString(2, tour.getMoTa());
            ps.setDouble(3, tour.getGiaTour());
            ps.setDate(4, tour.getTgBatDau());
            ps.setDate(5, tour.getTgKetThuc());
            ps.setInt(6, tour.getSoLuongMax());
            ps.setInt(7, tour.getSoLuongConLai());
            ps.setString(8, tour.getMaTour());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một tour
    public boolean xoaTour(String maTour) {
        String sql = "DELETE FROM Tour WHERE maTour=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTour);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Tìm kiếm tour theo từ khóa
    public List<Tour> timKiemTour(String keyword) {
        List<Tour> list = new ArrayList<>();
        String sql = "SELECT * FROM Tour WHERE maTour LIKE ? OR tenTour LIKE ? OR moTa LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Tour tour = new Tour(
                            rs.getString("maTour"),
                            rs.getString("tenTour"),
                            rs.getString("moTa"),
                            rs.getDouble("giaTour"),
                            rs.getDate("tg_batDau"),
                            rs.getDate("tg_ketThuc"),
                            rs.getInt("soLuongMax"),
                            rs.getInt("soLuongConLai")
                    );
                    list.add(tour);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tổng số vé đã đặt theo mã tour
    public int tongVeTourDaDat(String maTour) {
        String sql = "SELECT T.maTour, SUM(DV.SoLuong) AS TongSoLuongDat " +
                "FROM Tour T " +
                "JOIN VeTour VT ON T.maTour = VT.maTour " +
                "JOIN DatVe DV ON VT.MaDatVe = DV.MaDatVe " +
                "WHERE T.maTour = ? " +
                "GROUP BY T.maTour";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTour);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("TongSoLuongDat");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Lấy tất cả mã tour
    public List<String> getAllMaTour() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT maTour FROM Tour";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("maTour"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Tour getTourById(String maTour) {
        String sql = "SELECT * FROM Tour WHERE maTour = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTour);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Tour(
                        rs.getString("maTour"),
                        rs.getString("tenTour"),
                        rs.getString("moTa"),
                        rs.getDouble("giaTour"),
                        rs.getDate("tg_batDau"),
                        rs.getDate("tg_ketThuc"),
                        rs.getInt("soLuongMax"),
                        rs.getInt("soLuongConLai")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
