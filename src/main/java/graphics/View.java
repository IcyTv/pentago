package graphics;

import javax.swing.JFrame;
import java.awt.Component;

public class View extends JFrame {

    private static final long serialVersionUID = 1L;
    private Menu menu;
    private Game game;

    public View(int width, int height) {
        super("Temporary View");

        setSize(width, height);

        menu = new Menu(this);
        game = new Game(width, height);

        add(game);
        ((Component) game).setFocusable(true);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void toMenu() {
        removeAll();
        invalidate();
        add(menu);
        revalidate();
        setFocusable(true);
        menu.grabFocus();
        repaint();
    }

    public void toGame(int players) {
        removeAll();
        invalidate();
        game.setPlayers(players);
        add(game);
        revalidate();
        setFocusable(true);
        game.grabFocus();
        repaint();
    }

}