import comp124graphics.Ellipse;
import comp124graphics.GraphicsGroup;
import comp124graphics.Rectangle;

import java.util.ArrayList;
import java.util.Random;

public class Heap extends GraphicsGroup {
    private double width;
    private int id;
    private int numBean;
    private ArrayList<Bean> beans;

    private static final double X_BEAN_LEFT_BOUND = 3;
    private static final double Y_BEAN_UPPER_BOUND = 3;

    public Heap(double x, double y, double heapLength, int id, int numBean){
        super(x,y);
        this.width = heapLength;
        this.id = id;
        this.numBean = numBean;
        this.beans = new ArrayList<Bean>();
        createHeap();
    }

    private void createHeap(){
        Rectangle heap = new Rectangle(0, 0, width, width);
        add(heap);
        createBean();
    }

    private void createBean(){
        int beanId = 0;
        double xPos = X_BEAN_LEFT_BOUND, yPos = 0;
        for (int x = 0; x < numBean ; x++){
            yPos = calculateY();
            Bean bean = new Bean(xPos, yPos, beanId);
            add(bean);
            beans.add(bean);
            xPos += Bean.RADIUS_LENGTH;
        }
    }

    private double calculateY(){
        Random rand = new Random();
        return (width - Bean.RADIUS_LENGTH)*rand.nextDouble();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
