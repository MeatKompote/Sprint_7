package pojo;

public class LoginCourierResponseJson extends AbstractPojo {
    private String id;

    public LoginCourierResponseJson(String id) {
        this.id = id;
    }

    public LoginCourierResponseJson() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
