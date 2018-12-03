import comp124graphics.CanvasWindow;
import comp124graphics.Ellipse;
import comp124graphics.GraphicsGroup;
import comp124graphics.GraphicsObject;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Board extends CanvasWindow implements MouseListener {
    private double width, height;
    private double heapWidth;
    private double sideLength;
    private int numGrid;
    private int selectedHeapId;
    private int selectedBeanId;
//    private Heap[] heaps;    uncommented this
    private ArrayList<Heap> heaps;

    private static final double HEAP_GAP = 10;
    private static final double RATIO = 0.6;
    private static final int BEAN_UPPER_BOUND = 6;

    public Board(int width, int height, int numGrid) {
        super("Nim Game", width, height);
        this.width = width;
        this.height = height;
        this.numGrid = numGrid;
        this.heapWidth = width * RATIO;
        this.sideLength = calculateSideLength();
//        this.heaps = new Heap[numGrid];    uncommented this
        this.heaps = new ArrayList<Heap>();
        this.selectedHeapId = -1;
        this.selectedBeanId = -1;

        createBoard();

        addMouseListener(this);
    }

    private void createBoard(){
        int id = 0;
        double xPos = 0.4 * (width - heapWidth);
        double yPos = 0.5 * (height - sideLength);
        for(int i = 0; i < numGrid; i++){
            Random random = new Random();
            int numBean = 1+random.nextInt(BEAN_UPPER_BOUND);
            Heap heap = new Heap(xPos, yPos, sideLength, id, numBean);
//            heaps[i] = heap;  uncommented this
            heaps.add(heap);
            add(heap);
            xPos += sideLength + HEAP_GAP;
            id++;
        }
    }

    private double calculateSideLength() {
        return (heapWidth + numGrid * HEAP_GAP) / numGrid;
    }

    public static void main(String [] args) {
        Board b = new Board(1000, 1000, 5);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        GraphicsObject gObj = getElementAt(x, y);
        if(gObj != null && gObj instanceof Heap) {
            System.out.println("Cli");
            Heap clickedHeap = (Heap) gObj;
            int index = clickedHeap.getId();
            if(index == -1) {
                System.out.println("ERROr");
            } else {
                selectedBeanId = index;
                remove(clickedHeap);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
