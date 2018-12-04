import comp124graphics.GraphicsGroup;
import comp124graphics.GraphicsObject;
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

    /**
     * Create the heap layout and add on GraphicsGroup
     */
    private void createHeap(){
        Rectangle heap = new Rectangle(0, 0, width, width);
        add(heap);
        createBean();
    }

    /**
     * Create beans randomly on the heaps, called the Bean class
     */
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

    /**
     * Randomly generated a y position for the bean and
     * ensures it is not falling out of bounds
     * @return double: the bean's y position
     */
    private double calculateY(){
        Random rand = new Random();
        return (width - Bean.RADIUS_LENGTH)*rand.nextDouble();
    }

    /**
     * Remove clicked bean in a heap
     * @param clickedX
     * @param clickedY
     */
    public void removeBeanInsideHeap(double clickedX, double clickedY) {
        GraphicsObject currentObject = getElementAt(clickedX, clickedY);
        System.out.println(currentObject);
        if(currentObject instanceof Bean) {
            remove(currentObject);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
