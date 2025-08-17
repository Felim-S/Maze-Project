import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class Panel extends JPanel {
    public static final int GRID_SIZE = 35;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;
    public static final int ROWS = WIDTH / GRID_SIZE;
    public static final int COLS = HEIGHT / GRID_SIZE;

    public static final Random rand = new Random();
    Cell current;
    Cell[][] grid;
    Stack<Cell> stack;

    public Panel(){
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.GRAY);
        setup();
        setupKeyListener();
    }

    public void setup(){
        stack = new Stack<Cell>();

        // Create a grid of Cells
        grid = new Cell[ROWS][COLS];
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                grid[i][j] = new Cell(i,j);
            }
        }
        // Initialise current to the first Cell
        current = grid[0][0];
    }

    // Used to restart the whole scene
    private void setupKeyListener() {
        setFocusable(true);
        requestFocusInWindow();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    setup();
                    repaint();
                }
            }
        });
    }

    public void paint(Graphics g){
        super.paint(g);
        // Mark the current Cell as visited
        current.setVisited(true);
        // Choose (at random) one of the unvisited neighbours
        Cell next = current.getNeighbour(grid);
        // While there are (unvisited) neighbours
        if(next != null){
            // Mark the next Cell as visited
            next.setVisited(true);
            next.highlight(g);
            // Push the current Cell to the stack
            stack.push(current);

            // Remove the wall(s) between current and next
            Cell.removeWalls(current, next);

            // Set the current Cell to be the next Cell
            current = next;
        } else if(!stack.empty()){
            current = stack.pop();
            current.highlight(g);
        }

        // Display each Cell
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                grid[i][j].show(g, grid);
            }
        }
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        repaint();
    }
}
