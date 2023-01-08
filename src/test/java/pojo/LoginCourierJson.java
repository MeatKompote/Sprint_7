package pojo;

public class LoginCourierJson extends AbstractPojo{
    private String login;
    private String password;

    public LoginCourierJson(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public LoginCourierJson() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
