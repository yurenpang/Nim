import comp124graphics.CanvasWindow;
import comp124graphics.GraphicsObject;
import comp124graphics.GraphicsText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NimGame extends CanvasWindow implements MouseListener {

    private double width, height;
    private int numGrid;
    private boolean isFirstPlayer;
    private JButton replayBtn;
    private JButton finishBtn;
    public GraphicsText label;
    private Board board;

    private static final int CANVAS_WIDTH = 1000;
    private static final int CANVAS_HEIGHT = 500;
    private static final double STARTING_X = 20;
    private static final String PLAYER_TEXT = "You can click \' Finish My Turn \' to let the computer move";
    private static final String WIN_MESSAGE = "Congratualation! You win.";
    private static final String LOSE_MESSAGE = "Sorry, you lost. But you can try again";

    /**
     * This is the game interface including the CanvasWindow
     * Inside it has a game board, a "Finish My Turn" Button, a "Play Again Button", a game info label
     * @param width
     * @param height
     * @param numGrid
     */
    public NimGame(int width, int height, int numGrid) {
        super("Nim Game", width, height);
        this.width = width;
        this.height = height;
        this.numGrid = numGrid;
        this.isFirstPlayer = true;

        createLabel();
        createBoard();

        //Finish a player's turn and it is the computer's turn
        finishBtn = new JButton("Finish My Turn");
        finishBtn.setBounds(200, 400, 200, 30);
        add(finishBtn);
        finishBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(isFirstPlayer) {
                            label.setText("You need to make a move");
                        } else {
                            board.compMove();
                            isFirstPlayer = true;
                            board.getClickedHeap().setClickable(true);
                            board.setClickedHeap(null);
                            if(board.isOver()) {
                                label.setText(LOSE_MESSAGE);  //Game is over
                            } else if (board.isPlayWon()) {
                                label.setText(WIN_MESSAGE);   //A special circumstance when the user wins
                            }
                        }
                    }
                }
        );

        //This is the button the player chooses to click after a game to restart the game
        replayBtn = new JButton("Play Again");
        replayBtn.setBounds(550, 400, 200, 30);
        add(replayBtn);
        replayBtn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        removeAll();
                        createLabel();
                        createBoard();
                        System.out.println("Replay");
                    }
                }
        );

        //This add the mouselistener to click event
        addMouseListener(this);
    }

    /**
     * This creates the initial Game Text at the top of the screen
     */
    private void createLabel(){
        label = new GraphicsText(PLAYER_TEXT, (float) STARTING_X, 50.0f);
        label.setFont(new Font("SanSerif", Font.PLAIN, 24));
        add(label);
    }

    /**
     * This creates the Game Board and adds it to the screen
     */
    private void createBoard(){
        board = new Board(width, height, numGrid);
        add(board);
    }

    /**
      * When the user press the mouse
      * Selects a graphicObject:
      * 1. get the Board => then the heap => then the beans
        change the color of the color of the bean (interactivity with the user) if a bean is clicked
      * 2. limit the user to one heap
      * 3. do nothing when other parts of the screen is clicked
      * @param e
      */
    @Override
    public void mousePressed(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        GraphicsObject gObj = getElementAt(x, y);
        if(gObj instanceof Board) {
            String message = ((Board) gObj).manipulateBoard(x, y, true);
            label.setText(message);
        }
    }

    /**
      * Remove the clicked bean after the mouse is released
      * @param e
      */
    @Override
    public void mouseReleased(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();
        GraphicsObject gObj = getElementAt(x, y);
        if(gObj instanceof Board) {
            String message = ((Board) gObj).manipulateBoard(x, y, false);
            label.setText(message);
            isFirstPlayer = false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    public static void main(String [] args) {
        NimGame b = new NimGame(CANVAS_WIDTH, CANVAS_HEIGHT, 6);
    }
}
