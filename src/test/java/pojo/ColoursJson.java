package pojo;

public class ColoursJson extends AbstractPojo {
    private String colour;

    public ColoursJson(String colour) {
        this.colour = colour;
    }

    public ColoursJson() {
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
