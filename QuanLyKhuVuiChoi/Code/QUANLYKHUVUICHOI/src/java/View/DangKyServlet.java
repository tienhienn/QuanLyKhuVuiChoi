package View;

import controller.MyUtils;
import controller.KhachHangDAO;
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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
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
            // Gọi phương thức từ KhachHangDAO để đăng ký khách hàng
            boolean success = KhachHangDAO.registerCustomer(conn, fullname, email, phone, password);

            if (success) {
                // Tạo đối tượng khách hàng mới với thông tin đã đăng ký
                KhachHang newCustomer = new KhachHang(null, fullname, password, phone, email);

                // Lưu thông tin khách hàng vào session và cookie
                MyUtils.storeLoginedUser(request.getSession(), newCustomer);
                MyUtils.storeUserCookie(response, newCustomer);

                // Redirect đến trang đăng nhập với trạng thái thành công
                response.sendRedirect(request.getContextPath() + "/View/DangNhap.jsp?status=success");
            } else {
                // Nếu có lỗi, redirect về trang đăng nhập với trạng thái lỗi
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