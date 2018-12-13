import comp124graphics.Ellipse;

public class Bean extends Ellipse {
    public static final double RADIUS_LENGTH = 17;

    private int id;

    public Bean(double x, double y, int id){
        super(x, y, RADIUS_LENGTH, RADIUS_LENGTH);
        this.id = id;
        this.setFilled(true);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
