import comp124graphics.Ellipse;
import comp124graphics.GraphicsGroup;
import comp124graphics.Rectangle;
import java.util.Random;

public class Heap extends GraphicsGroup {
    private double width;
    private int id;
    private int numBean = 3;

    public Heap(double x, double y, double heapLength, int id){
        super(x,y);
        this.width=heapLength;
        this.id=id;
        createHeap();
    }

    private void createHeap(){
        Rectangle heap = new Rectangle(0, 0, width, width);
        add(heap);
        createBean();
    }

    private void createBean(){
        int beanId = 0;
        double xPos = 30, yPos = 5;
        for (int x = 0; x < numBean ; x++){
            Bean bean = new Bean(xPos, yPos,beanId);
            add(bean);
            xPos += 20 + 5;
            yPos = calculateY();
        }
    }

    private double calculateY(){
        Random rand = new Random();
        return (width - 20)*rand.nextDouble();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
