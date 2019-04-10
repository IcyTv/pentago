package graphics2d;

import java.awt.Component;

import javax.swing.JFrame;

public class View extends JFrame {

    private static final long serialVersionUID = 1L;
    private Menu menu;
    private Game game;

    private int HEIGHT;
    private int WIDTH;

    public View(int width, int height) {
        super("Temporary View");

        HEIGHT = height;
        WIDTH = width;

        setSize(width, height + 30);

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
        game = new Game(WIDTH, HEIGHT);
        System.out.println(players);
        game.setPlayers(players);
        add(game);
        revalidate();
        setFocusable(true);
        game.grabFocus();
        repaint();
    }

}