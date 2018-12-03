import comp124graphics.CanvasWindow;

public class Board extends CanvasWindow {
    private double width, height;
    private double heapWidth;
    private double sideLength;
    private int numGrid;
    private Heap heaps[];

    private final double HEAP_GAP = 10;
    private final double RATIO = 0.6;

    public Board(int width, int height, int numGrid) {
        super("Nim Game", width, height);
        this.width = width;
        this.height = height;
        this.numGrid = numGrid;
        this.heapWidth = width * RATIO;
        this.sideLength = calculateSideLength();
        this.heaps = new Heap[numGrid];

        createBoard();
    }

    private void createBoard(){
        int id = 0;
        double xPos = 0.4 * (width - heapWidth);
        double yPos = 0.5 * (height - sideLength);
        for(int i = 0; i < numGrid; i++){
            Heap heap = new Heap(xPos, yPos, sideLength, id);
            heaps[i] = heap;
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
}
