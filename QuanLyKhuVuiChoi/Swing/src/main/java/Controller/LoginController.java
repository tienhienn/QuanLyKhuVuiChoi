package Controller;

import Model.UserModel;
import View.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private UserModel model;
    private LoginView view;

    public LoginController(UserModel model, LoginView view) {
        this.model = model;
        this.view = view;

        // Chỉ cần listener cho nút đăng nhập
        this.view.addLoginListener(new LoginListener());
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = view.getUsername();
            String password = view.getPassword();

            model.setUsername(username);
            model.setPassword(password);

            if (model.isValid()) {
                JOptionPane.showMessageDialog(view, "Đăng nhập thành công!");
                // Chuyển đến giao diện chính ở đây nếu có
                // new MainAppForm().setVisible(true);
                view.dispose();  // Đóng form đăng nhập
            } else {
                view.showMessage("Sai tài khoản hoặc mật khẩu!");
            }
        }
    }
}
