package pojo;

/**
 * Created by Yehor_Yeshchenko on 11/3/2014.
 */
public class Visual {
    String color;
    Integer transparency;
    Integer way;

    public static final String colorStr = "visual";
    public static final String transparencyStr = "transparency";
    public static final String wayStr = "way";

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getTransparency() {
        return transparency;
    }

    public void setTransparency(Integer transparency) {
        this.transparency = transparency;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }
}
