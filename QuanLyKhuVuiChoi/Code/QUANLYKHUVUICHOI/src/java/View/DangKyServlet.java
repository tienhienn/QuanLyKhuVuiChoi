package View;

import controller.MyUtils;
import model.KhachHang;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(urlPatterns = {"/DangKy"})
public class DangKyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra mật khẩu nhập lại
        if (!password.equals(confirmPassword)) {
            response.sendRedirect(request.getContextPath() + "/View/DangNhap.jsp?status=mismatch");
            return;
        }

        try (Connection conn = MyUtils.getStoredConnection(request)) {
            String sql = "INSERT INTO khachhang (maKhachHang, tenKhachHang, email, SDT, matkhau, diaChi, gioiTinh) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            String maKhachHang = "KH" + System.currentTimeMillis(); // Tạo mã khách hàng tạm thời

            stmt.setString(1, maKhachHang);
            stmt.setString(2, fullname);
            stmt.setString(3, email);
            stmt.setString(4, phone);
            stmt.setString(5, password);
            stmt.setNull(6, java.sql.Types.VARCHAR); // diaChi null
            stmt.setNull(7, java.sql.Types.VARCHAR); // gioiTinh null

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                KhachHang newCustomer = new KhachHang(maKhachHang, fullname, null, null, null, password, phone, email);
                MyUtils.storeLoginedUser(request.getSession(), newCustomer);
                MyUtils.storeUserCookie(response, newCustomer);

                response.sendRedirect(request.getContextPath() + "/View/DangNhap.jsp?status=success");
            } else {
                response.sendRedirect(request.getContextPath() + "/View/DangNhap.jsp?status=error");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/View/DangNhap.jsp?status=error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/View/DangNhap.jsp");
    }
}
