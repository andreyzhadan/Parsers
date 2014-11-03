package pojo;

public class Visual {
    public static final String WAY = "way";
    public static final String COLOR = "color";
    public static final String TRANSPARENCY = "transparency";
    private Integer way;
    private String color;
    private Integer transparency;

    public Visual(String color, Integer transparency, Integer way) {
        this.color = color;
        this.transparency = transparency;
        this.way = way;
    }

    @Override
    public String toString() {
        return "Visual{" +
                "color='" + color + '\'' +
                ", transparency=" + transparency +
                ", way=" + way +
                '}';
    }

    public Visual() {
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setTransparency(Integer transparency) {
        this.transparency = transparency;
    }

    public void setWay(Integer way) {
        this.way = way;
    }
}
