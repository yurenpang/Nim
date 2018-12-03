import comp124graphics.Ellipse;
import comp124graphics.GraphicsGroup;
import comp124graphics.Rectangle;

public class Heap extends GraphicsGroup {
    private double width;
    private int id;
    private int numBean;

    public Heap(double x, double y, double heapLength, int id){
        super(x,y);
        this.width=heapLength;
        this.id=id;
        createHeap();
    }

    private void createHeap(){
        Rectangle heap = new Rectangle(0, 0, width, width);
        add(heap);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
