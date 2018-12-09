import comp124graphics.CanvasWindow;
import comp124graphics.GraphicsObject;
import comp124graphics.GraphicsText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Board extends CanvasWindow implements MouseListener, ActionListener {
    private double width, height;
    private double heapWidth;
    private double sideLength;
    private int numGrid;
    private int selectedHeapId;
    private int selectedBeanId;
    private Heap[] heaps;
    private boolean isFirstPlayer;
    private Heap clickedHeap;
    private double x,y;
    private GraphicsText label;

    private static final double HEAP_GAP = 10;
    private static final double RATIO = 0.6;
    private static final int BEAN_UPPER_BOUND = 6;
    private static final double STARTING_X = 20;
    private static final String PLAYER_TEXT = "It is your turn: ";
    private static final String N_POSITION = "You have a winning move!";
    private static final String P_POSITION = "Computer will always win!";


    public Board(int width, int height, int numGrid) {
        super("Nim Game", width, height);
        this.width = width;
        this.height = height;
        this.numGrid = numGrid;
        this.heapWidth = width * RATIO;
        this.sideLength = calculateSideLength();
        this.heaps = new Heap[numGrid];
        this.selectedHeapId = -1;
        this.selectedBeanId = -1;
        this.isFirstPlayer = true;

        createText();
        createBoard();
        createButton();

        addMouseListener(this);
    }

    private void createText() {
        label = new GraphicsText(PLAYER_TEXT, (float) STARTING_X, 50.0f);
        label.setFont(new Font("SanSerif", Font.PLAIN, 24));
        add(label);
    }

    private void createBoard(){
        int id = 0;
        double xPos = 0.4 * (width - heapWidth);
        double yPos = 0.5 * (height - sideLength);
        for(int i = 0; i < numGrid; i++){
            Random random = new Random();
            int numBean = 1+random.nextInt(BEAN_UPPER_BOUND);
            Heap heap = new Heap(xPos, yPos, sideLength, id, numBean);
            heaps[i] = heap;
            add(heap);
            xPos += sideLength + HEAP_GAP;
            id++;
        }
    }

    private void createButton(){
        JButton b = new JButton("Finish My Turn");
        b.setBounds(50,100,200,30);
        add(b);
        b.addActionListener(this);
    }

    private double calculateSideLength() {
        return (heapWidth + numGrid * HEAP_GAP) / numGrid;
    }

    public static void main(String [] args) {
        Board b = new Board(1000, 1000, 5);
    }

    /**
     * Find the Heap the user is clicking and remove it
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
        GraphicsObject gObj = getElementAt(x, y);
        if (gObj instanceof Heap) {
            clickedHeap = (Heap) gObj;
            int index = clickedHeap.getId();
            if (index == -1) {
                System.out.println("ERROR");
            } else {
                selectedBeanId = index;
                clickedHeap.colorBeanInsideHeap(x, y, Color.RED);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        clickedHeap.removeBeanInsideHeap(x,y);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        isFirstPlayer = !isFirstPlayer;
        if(!isFirstPlayer) {
            NimSum n = new NimSum(heaps);
            n.makeNextMove();
        }
    }
}
