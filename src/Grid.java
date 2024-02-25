import javax.swing.*;
import java.awt.*;

public class Grid {
    private final JButton[][] buttons;
    private final Game game;


    public Grid(Game game) {
        boolean[][] grid = game.getGrid();
        this.buttons = new JButton[grid.length][grid[0].length];
        this.game = game;
    }

    public void init() {
        boolean[][] grid = game.getGrid();
        game.setLayout(new GridLayout(grid.length, grid[0].length));

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                JButton button = new JButton();
                button.setEnabled(false);
                button.setBackground(grid[i][j] ? Color.BLACK : Color.WHITE);
                buttons[i][j] = button;
                game.add(button);
            }
        }

    }

    public void updateButtons() {
        boolean[][] grid = game.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                buttons[i][j].setBackground(grid[i][j] ? Color.BLACK : Color.WHITE);
            }
        }
    }
}
