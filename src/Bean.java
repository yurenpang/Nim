import comp124graphics.Ellipse;

public class Bean extends Ellipse {
    public static final double RADIUS_LENGTH = 20;

    private int id;

    public Bean(double x, double y, int id){
        super(x, y, RADIUS_LENGTH, RADIUS_LENGTH);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
