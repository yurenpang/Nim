import comp124graphics.CanvasWindow;

public class Board extends CanvasWindow {
    private double width;
    private int n;
    private Heap heap[];

    private final double HEAP_GAP = 10;

    public Board(int width, int length) {
        super("Nim Game", width, length);
    }

    public static void main(String [] args) {
        Board b = new Board(1000, 1000);
    }
}
