import comp124graphics.GraphicsGroup;
import comp124graphics.GraphicsObject;
import comp124graphics.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Heap extends GraphicsGroup {
    private double width;
    private int id;
    private int numBean;
    private ArrayList<Bean> beans;
    private int beanSize;
    private boolean isClickable;

    private static final double X_BEAN_LEFT_BOUND = 3;
    private static final double Y_BEAN_UPPER_BOUND = 3;

    public Heap(double x, double y, double heapLength, int id, int numBean){
        super(x,y);
        this.width = heapLength;
        this.id = id;
        this.numBean = numBean;
        this.beans = new ArrayList<Bean>();
        this.isClickable = true;
        createHeap();

        beanSize = beans.size();
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
            beans.remove(currentObject);
        }
        beanSize = beans.size();
    }

    public void colorBeanInsideHeap(double clickedX, double clickedY, Color color) {
        GraphicsObject currentObject = getElementAt(clickedX, clickedY);
        System.out.println(currentObject);
        if(currentObject instanceof Bean) {
            Bean b = (Bean) currentObject;
            b.setFillColor(color);
        }
    }

    public void removeBeansAI() {
        Iterator<Bean> iter = beans.listIterator(beanSize);
        while(iter.hasNext()){
            Bean b = iter.next();
            remove(b);
            iter.remove();
        }
        System.out.println(getbeanSizeInAHeap());
    }

    public ArrayList<Bean> getBeansInAHeap() {
        return beans;
    }

    public int getbeanSizeInAHeap(){
        return beans.size();
    }

    public void setBeanSize(int beanSize){
        this.beanSize = beanSize;
    }

    public void removeLastBean() {
        Bean b = beans.remove(beans.size()-1);
        remove(b);
    }

    public int getId() {
        return id;
    }

    public void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    public void setId(int id) {
        this.id = id;
    }
}
