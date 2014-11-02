package pojo;

/**
 * Created by azhadan on 11/2/14.
 */
public class Visual {
    String color;
    Integer transparency;
    Integer way;

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
