package pojo;

public class Gem {
    public static final String ROOT = "gem";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String VISUAL = "visual";
    public static final String ORIGIN = "origin";
    public static final String PRECIOUSNESS = "preciousness";
    private int value;
    private String name;
    private Visual visual;
    private String origin;
    private boolean preciousness;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setPreciousness(boolean preciousness) {
        this.preciousness = preciousness;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setVisual(Visual visual) {
        this.visual = visual;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
