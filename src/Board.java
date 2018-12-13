import comp124graphics.CanvasWindow;
import comp124graphics.GraphicsGroup;
import comp124graphics.GraphicsObject;
import comp124graphics.GraphicsText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board extends GraphicsGroup {
    private boolean isFirstPlayer;
    private double width, height;
    private double heapWidth;
    private double sideLength;
    private double x,y;
    private int numGrid;
    private Heap[] heaps;
    private Heap clickedHeap;
    private GraphicsText label;
    private NimSum n;
    private HashMap<Integer, Integer> heapBeanMap;

    private static final double HEAP_GAP = 10;
    private static final double RATIO = 0.6;
    private static final int BEAN_UPPER_BOUND = 6;
    private static final double STARTING_X = 20;
    private static final String PLAYER_TEXT = "It is your turn: ";


    public Board(double width, double height, int numGrid) {
//        super("Nim Game", width, height);
        super(0, 0);
        this.width = width;
        this.height = height;
        this.numGrid = numGrid;
        this.heapWidth = width * RATIO;
        this.sideLength = calculateSideLength();
        this.heaps = new Heap[numGrid];
        this.isFirstPlayer = true;
        this.clickedHeap = null;
        this.heapBeanMap = new HashMap<>();

        createBoard();
        updateMap();

        this.n = new NimSum(heapBeanMap);
    }

    /**
     * This is the main game board
     */
    private void createBoard(){
        int id = 0;
        double xPos = 0.4 * (width - heapWidth);
        double yPos = 0.5 * (height - sideLength);
        for(int i = 0; i < numGrid; i++){
            Random random = new Random();
            int numBean = 1+random.nextInt(((int)(sideLength/Bean.RADIUS_LENGTH)));
            Heap heap = new Heap(xPos, yPos, sideLength, id, numBean);
            heaps[i] = heap;
            add(heap);
            xPos += sideLength + HEAP_GAP;
            id++;
        }
    }

    public String manipulateBoard(double clickedX, double clickedY, boolean isColor){
        GraphicsObject currentGroup = getElementAt(clickedX, clickedY);
        if(currentGroup instanceof Heap) {
            if(clickedHeap == null || currentGroup.equals(clickedHeap)) {
                clickedHeap = (Heap) currentGroup;
                if (isColor) {
                    clickedHeap.colorBeanInsideHeap(clickedX, clickedY, Color.RED);
                } else {
                    clickedHeap.removeBeanInsideHeap(clickedX, clickedY);
                }
                clickedHeap.setClickable(false);
                return "You can click \' Finish My Turn \' to let the computer move";
            } else {
                return "You can only choose a heap";
            }
        }
        return "Please click on the black beans";
    }

    public void compMove() {
        updateMap();
        ArrayList<Integer> heapIdBeans = n.nextHeapIdBeans();
        heaps[heapIdBeans.get(0)].removeBeans(heapIdBeans.get(1));
    }

    /**
     * Update the number of beans in the map
     */
    private void updateMap() {
        for (Heap h : heaps) {
            int beanSizeInHeap = h.getbeanSizeInAHeap();
            int heapId = h.getId();
            heapBeanMap.put(heapId, beanSizeInHeap);
        }
    }

    public boolean isOver() {
        for(int i = 0; i < heaps.length; i++) {
            if(heaps[i].getbeanSizeInAHeap() != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isPlayWon() {
        int counter = 0;
        for(int i = 0; i < heaps.length; i++) {
            if(heaps[i].getbeanSizeInAHeap() != 0) {
                counter ++;
            }
        }
        if(counter == 1){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Calculate the side length of a CanvasWindow of a different size
     * @return double size
     */
    private double calculateSideLength() {
        return (heapWidth + numGrid * HEAP_GAP) / numGrid;
    }

    public Heap getClickedHeap() {
        return clickedHeap;
    }

    public void setClickedHeap(Heap heap) {
        clickedHeap = heap;
    }

//    /**
//     * When the user press the mouse
//     * Selects a graphicObject:
//     * 1. change the color of the color of the bean (interactivity with the user) if a bean is clicked
//     * 2. limit the user to one heap
//     * 3. do nothing when other parts of the screen is clicked
//     * @param e
//     */
//    @Override
//    public void mousePressed(MouseEvent e) {
//        x = e.getX();
//        y = e.getY();
//        GraphicsObject gObj = getElementAt(x, y);
//        if (gObj instanceof Heap) {
//            if(clickedHeap == null || gObj.equals(clickedHeap)) {
//                clickedHeap = (Heap) gObj;
//                clickedHeap.setClickable(false);
//                int index = clickedHeap.getId();
//                if (index == -1) {
//                    System.out.println("ERROR");
//                } else {
//                    clickedHeap.colorBeanInsideHeap(x, y, Color.RED);
//                }
//            } else {
//                label.setText("You can only choose a heap");
//            }
//        }
//    }
//
//    /**
//     * Remove the clicked bean after the mouse is released
//     * @param e
//     */
//    @Override
//    public void mouseReleased(MouseEvent e) {
//        clickedHeap.removeBeanInsideHeap(x,y);
//        isFirstPlayer = false;
//    }
//
//    /**
//     * Finish a player's turn and it is the computer's turn
//     * Finish a player's turn and it is the computer's turn
//     * @param e
//     */
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(isFirstPlayer) {
//            label.setText("You need to make a move");
//        } else {
//            n.updateMap();
//            n.makeNextMove();
//            clickedHeap.setClickable(true);
//            clickedHeap = null;
//            isFirstPlayer = true;
//        }
//    }
//
//    @Override
//    public void mouseClicked(MouseEvent e) {}
//
//    @Override
//    public void mouseEntered(MouseEvent e) {}
//
//    @Override
//    public void mouseExited(MouseEvent e) {}
//

//    public static void main(String [] args) {
//        Board b = new Board(1000, 500, 5);
//    }

}
