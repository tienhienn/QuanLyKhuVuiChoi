package controller;


import controller.DatabaseConnection;
import java.sql.Connection;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import model.KhachHang;


public class MyUtils {

    public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
    
    // Lưu trữ Connection vào attribute của request.
    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME_CONNECTION, conn);
    }

    // Lấy đối tượng Connection đã được lưu trữ trong attribute của request.
    public static Connection getStoredConnection(HttpServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
        if (conn == null) {
            conn = DatabaseConnection.getConnection();
            storeConnection(request, conn);
        }
        return conn;
    }

    // Lưu trữ thông tin khách hàng đã login vào Session.
    public static void storeLoginedUser(HttpSession session, KhachHang loginedCustomer) {
        session.setAttribute("loginedCustomer", loginedCustomer);
    }

    // Lấy thông tin khách hàng lưu trữ trong Session.
    public static KhachHang getLoginedUser(HttpSession session) {
        return (KhachHang) session.getAttribute("loginedCustomer");
    }

    // Lưu thông tin khách hàng vào Cookie.
    public static void storeUserCookie(HttpServletResponse response, KhachHang customer) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, customer.getEmail());
        cookieUserName.setMaxAge(24 * 60 * 60);  // Lưu cookie trong 1 ngày
        response.addCookie(cookieUserName);
    }

    public static String getUserNameInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // Xóa Cookie của khách hàng
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
        cookieUserName.setMaxAge(0);  // Hết hạn ngay lập tức
        response.addCookie(cookieUserName);
    }
}
