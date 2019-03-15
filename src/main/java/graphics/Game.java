package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.Timer;

import model.Gamemaster;
import model.Piece;

public class Game extends JPanel implements MouseListener {

    private static final long serialVersionUID = 1L;

    private Gamemaster gm;

    private Timer tm;

    private int[] rotPos;
    private int[] mousePressedPos;

    private static int WIDTH;
    private static int HEIGHT;

    public Game(int width, int height) {
        setSize(width, height);
        WIDTH = width;
        HEIGHT = height;

        gm = new Gamemaster(9, 3, 2, false);

        addMouseListener(this);

        tm = new Timer(50, new AbstractAction() {

            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // System.out.println("Running");
                repaint();
            }
        });
        tm.start();

    }

    public void setPlayers(int players) {
        gm = new Gamemaster(9, 3, 2, false);
    }

    // Gameloop
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.clearRect(0, 0, WIDTH, HEIGHT);
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        if (!gm.won()) {
            int bSize = gm.getBoard().getBSize();
            int pSize = gm.getBoard().getPSize();
            for (int i = 0; i < bSize * bSize; i++) {
                if (i % 2 == 0) {
                    g2.setColor(new Color(200, 200, 200));
                } else {
                    g2.setColor(Color.WHITE);
                }
                // System.out.println(i / bSize);
                g2.fillRect((i % bSize) * (WIDTH / bSize), (i / bSize) * (HEIGHT / bSize), (WIDTH / bSize),
                        (HEIGHT / bSize));
            }

            for (int i = 0; i < pSize * pSize; i++) {
                if (rotPos != null && rotPos[0] == i % pSize && rotPos[1] == i / pSize) {
                    g2.setColor(new Color(0, 198, 72, 20));
                    g2.fillRect((i % pSize) * (WIDTH / pSize), (i / pSize) * (HEIGHT / pSize), (WIDTH / pSize),
                            (HEIGHT / pSize));

                    // g2.setColor(Color.RED);

                } else {
                    g2.setColor(Color.BLUE);
                    g2.drawRect((i % pSize) * (WIDTH / pSize), (i / pSize) * (HEIGHT / pSize), (WIDTH / pSize),
                            (HEIGHT / pSize));
                }
            }

            for (int i = 0; i < bSize * bSize; i++) {
                Piece tmp = gm.getBoard().get((i % bSize), (i / bSize));
                if (tmp != null) {
                    g2.setColor(tmp.getColor());
                    g2.fillOval((i % bSize) * (WIDTH / bSize), (i / bSize) * (HEIGHT / bSize), (WIDTH / bSize),
                            (HEIGHT / bSize));
                }
            }
            g2.setColor(gm.getCurrentPlayer().getColor());
            g2.drawRect(0, 0, WIDTH - 1, HEIGHT - 2);
        } else {
            g2.drawString(gm.getWinner().getName() + " won", WIDTH / 2, HEIGHT / 2);
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {
        System.out.println(gm.won() ? "won" : "not won");
        if (gm.currentTurnIsPlaceTurn()) {
            int bSize = gm.getBoard().getBSize();
            int x = arg0.getX() / (WIDTH / bSize);
            int y = arg0.getY() / (HEIGHT / bSize);

            gm.placePiece(x, y);
        } else {
            if (rotPos == null) {
                int pSize = gm.getBoard().getPSize();
                int x = arg0.getX() / (WIDTH / pSize);
                int y = arg0.getY() / (HEIGHT / pSize);
                rotPos = new int[] { x, y };
            } else {
                mousePressedPos = new int[] { arg0.getX(), arg0.getY() };
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {
        if (!gm.currentTurnIsPlaceTurn() && rotPos != null && mousePressedPos != null) {
            int dx = mousePressedPos[0] - arg0.getX();
            // System.out.println(dx);

            if (dx > -50 && dx < 50) {
                return;
            }

            if (dx < 0) {
                gm.rotPanel(rotPos[0], rotPos[1], false);
            } else {
                gm.rotPanel(rotPos[0], rotPos[1], true);
            }

            rotPos = null;
            mousePressedPos = null;
        }
    }

}