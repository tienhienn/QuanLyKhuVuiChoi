package Model;

public class UserModel {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Kiểm tra username và password "admin"/"123"
    public boolean isValid() {
        return "admin".equals(username) && "123".equals(password);
    }
}
