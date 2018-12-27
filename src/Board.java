import comp124graphics.GraphicsGroup;
import comp124graphics.GraphicsObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Board extends GraphicsGroup {
    private double width, height;
    private double heapWidth;
    private double sideLength;
    private int numGrid;
    private Heap[] heaps;
    private Heap clickedHeap;
    private NimSum n;
    private HashMap<Integer, Integer> heapBeanMap;

    private static final double HEAP_GAP = 10;
    private static final double RATIO = 0.6;


    public Board(double width, double height, int numGrid) {
        super(0, 0);
        this.width = width;
        this.height = height;
        this.numGrid = numGrid;
        this.heapWidth = width * RATIO;
        this.sideLength = calculateSideLength();
        this.heaps = new Heap[numGrid];
        this.clickedHeap = null;
        this.heapBeanMap = new HashMap<>();

        createBoard();
        updateMap();

        this.n = new NimSum(heapBeanMap);
    }

    /**
     * This is the main game board by adding square Heaps inside
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

    /**
     * This gets the heap the user clicked inside the game board
     * Colors the clicked beans inside the heap by calling colorBeanInsideHeap() in Heap class
     * Removes the clicked beans inside the heap by removeBeanInsideHeap() in Heap class
     * Set Clickable of the heap so a user can only click on one heap
     *
     * @param clickedX
     * @param clickedY
     * @param isColor
     * @return
     */
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

    /**
     * The board makes its move
     *
     * It updates the current map layout
     * Use the NimSum Algorithm Class to calculate which heap and how many beans to remove
     * Calls the function in Heap to removes the corresponding beans in a heap
     */
    public void compMove() {
        updateMap();
        ArrayList<Integer> heapIdBeans = n.nextHeapIdBeans();
        heaps[heapIdBeans.get(0)].removeBeans(heapIdBeans.get(1));
    }

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

    private double calculateSideLength() {
        return (heapWidth + numGrid * HEAP_GAP) / numGrid;
    }

    public Heap getClickedHeap() {
        return clickedHeap;
    }

    public void setClickedHeap(Heap heap) {
        clickedHeap = heap;
    }

}
