package DAO;

import Model.NhaHang;

import java.sql.*;
import java.util.ArrayList;

public class NhaHangDAO {
    private Connection conn;

    public NhaHangDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<NhaHang> getAll() {
        ArrayList<NhaHang> list = new ArrayList<>();
        String sql = "SELECT nh.*, dv.tenDichVu FROM NhaHang nh JOIN DichVu dv ON nh.maDichVu = dv.maDichVu";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                NhaHang nh = new NhaHang();
                nh.setMaNhaHang(rs.getString("maNhaHang"));
                nh.setMaDichVu(rs.getString("maDichVu"));
                nh.setGioMoCua(rs.getString("TGianMoCua"));
                nh.setGioDongCua(rs.getString("TgianDongCua"));
                nh.setLoaiNhaHang(rs.getString("loaiNhaHang"));
                nh.setTenDichVu(rs.getString("tenDichVu")); // từ bảng DichVu
                list.add(nh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean add(NhaHang nh) {
        String sql = "INSERT INTO NhaHang(maNhaHang, maDichVu, TGianMoCua, TgianDongCua, loaiNhaHang) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nh.getMaNhaHang());
            ps.setString(2, nh.getMaDichVu());
            ps.setString(3, nh.getGioMoCua());
            ps.setString(4, nh.getGioDongCua());
            ps.setString(5, nh.getLoaiNhaHang());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(NhaHang nh) {
        String sql = "UPDATE NhaHang SET maDichVu=?, TGianMoCua=?, TgianDongCua=?, loaiNhaHang=? WHERE maNhaHang=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nh.getMaDichVu());
            ps.setString(2, nh.getGioMoCua());
            ps.setString(3, nh.getGioDongCua());
            ps.setString(4, nh.getLoaiNhaHang());
            ps.setString(5, nh.getMaNhaHang());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String maNhaHang) {
        String sql = "DELETE FROM NhaHang WHERE maNhaHang=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maNhaHang);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<NhaHang> search(String keyword) {
        ArrayList<NhaHang> list = new ArrayList<>();
        String sql = "SELECT nh.*, dv.tenDichVu FROM NhaHang nh JOIN DichVu dv ON nh.maDichVu = dv.maDichVu WHERE maNhaHang LIKE ? OR loaiNhaHang LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhaHang nh = new NhaHang();
                nh.setMaNhaHang(rs.getString("maNhaHang"));
                nh.setMaDichVu(rs.getString("maDichVu"));
                nh.setGioMoCua(rs.getString("TGianMoCua"));
                nh.setGioDongCua(rs.getString("TgianDongCua"));
                nh.setLoaiNhaHang(rs.getString("loaiNhaHang"));
                nh.setTenDichVu(rs.getString("tenDichVu"));
                list.add(nh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

