package graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;
import javax.swing.JPanel;
import javax.swing.AbstractAction;

import model.Gamemaster;
import model.Piece;

public class Game extends JPanel implements MouseListener {

    private static final long serialVersionUID = 1L;

    private Gamemaster gm;

    private Timer tm;

    private int[] rotPos;

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

        int height = HEIGHT - 20;
        int width = WIDTH - 20;

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
                g2.fillRect((i % bSize) * (width / bSize), (i / bSize) * (height / bSize), (width / bSize),
                        (height / bSize));
            }

            for (int i = 0; i < pSize * pSize; i++) {
                if (rotPos != null && rotPos[0] == i % pSize && rotPos[1] == i / pSize) {
                    g2.setColor(new Color(0, 198, 72, 20));
                    g2.fillRect((i % pSize) * (width / pSize), (i / pSize) * (height / pSize), (width / pSize),
                            (height / pSize));

                } else {
                    g2.setColor(Color.BLUE);
                    g2.drawRect((i % pSize) * (width / pSize), (i / pSize) * (height / pSize), (width / pSize),
                            (height / pSize));
                }
            }

            for (int i = 0; i < bSize * bSize; i++) {
                Piece tmp = gm.getBoard().get((i % bSize), (i / bSize));
                if (tmp != null) {
                    g2.setColor(tmp.getColor());
                    g2.fillOval((i % bSize) * (width / bSize), (i / bSize) * (height / bSize), (width / bSize),
                            (height / bSize));
                }
            }
        } else {
            // TODO Display winner
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
        if (gm.currentTurnIsPlaceTurn()) {
            int bSize = gm.getBoard().getBSize();
            int x = arg0.getX() / ((WIDTH - 20) / bSize);
            int y = arg0.getY() / ((HEIGHT - 20) / bSize);

            gm.placePiece(x, y);

            rotPos = null;
        } else {
            if (rotPos == null) {
                int pSize = gm.getBoard().getPSize();
                int x = arg0.getX() / ((WIDTH - 20) / pSize);
                int y = arg0.getY() / ((WIDTH - 20) / pSize);
                rotPos = new int[] { x, y };
            } else {
                System.out.println("Rot dirt");
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {

    }

    @Override
    public void mouseExited(MouseEvent arg0) {

    }

    @Override
    public void mousePressed(MouseEvent arg0) {

    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

}