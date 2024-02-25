import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Listener implements KeyListener {
    private final Game game;

    public Listener(Game game) {
        this.game = game;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (game.gameStarted) {
            game.setTitle(game.getTitle() + " - Game Over!");
            game.stopGame();
            System.out.println("Game Over!");
        }
    }
}
