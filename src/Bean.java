import comp124graphics.Ellipse;
import comp124graphics.GraphicsGroup;

public class Bean extends GraphicsGroup {
    public static final double RADIUS_LENGTH = 20;

    private int id;

    public Bean(double x, double y, int id){
        super(x,y);
        this.id = id;
        createBean();
    }

    private void createBean(){
        Ellipse bean = new Ellipse(0, 0, RADIUS_LENGTH, RADIUS_LENGTH);
        add(bean);
    }

    public void setId(int id) {
        this.id = id;
    }
}
