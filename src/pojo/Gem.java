package pojo;

/**
 * Created by azhadan on 11/2/14.
 */
public class Gem {
    String name;
    boolean preciousness;
    String origin;
    Visual visual;
    int value;
    public static final String nameStr = "name";
    public static final String preciousnessStr = "preciousness";
    public static final String rootStr = "gem";

    public Gem(String name, boolean preciousness, String origin, Visual visual, int value) {
        this.name = name;
        this.preciousness = preciousness;
        this.origin = origin;
        this.visual = visual;
        this.value = value;
    }

    public Gem() {

    }

    @Override
    public String toString() {
        return "Gem{" +
                "name='" + name + '\'' +
                ", preciousness=" + preciousness +
                ", origin='" + origin + '\'' +
                ", visual=" + visual +
                ", value=" + value +
                '}';
    }

    public Gem(String name, boolean preciousness) {
        this.name = name;
        this.preciousness = preciousness;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPreciousness() {
        return preciousness;
    }

    public void setPreciousness(boolean preciousness) {
        this.preciousness = preciousness;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Visual getVisual() {
        return visual;
    }

    public void setVisual(Visual visual) {
        this.visual = visual;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
