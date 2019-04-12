package model;

import java.util.BitSet;

public class BitPanel {

    private BitSet[][] bitPieces;
    private int size;
    private int amountOfPlayers;

    /**
     * Konstruktor Erzeugt ein Panel ohne besetzte Felder
     * 
     * @param n Seitenlaenge des Panels
     **/
    public BitPanel(int pSize, int amountOfPlayers) {
        this.size = pSize;
        bitPieces = new BitSet[size][size];
        this.amountOfPlayers = amountOfPlayers;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                bitPieces[i][j] = new BitSet(amountOfPlayers);
            }
        }
    }

    public int getSize() {
        return size;
    }

    public boolean emptyBitSet(int x, int y) {
        return bitPieces[x][y].isEmpty();
    }

    /**
     * Setzt das uebergebene Piece an die uebergebenen Koordinaten
     * 
     * @param x     X-Positoin des Piece
     * @param y     Y-Position des Piece
     * @param piece zu platzierendes Piece
     **/
    public void set(int x, int y, boolean[] piece) {
        for (int i = 0; i < piece.length; i++) {
            bitPieces[x][y].set(i, piece[i]);
        }
    }

    public void set(int x, int y, boolean b, int index) {
        bitPieces[x][y].set(index, b);
    }

    /**
     * Gibt das Piece an den uebergebenen Koordinaten zurueck
     * 
     * @param x: X-Koordinate
     * @param y: Y-Koordianate
     **/
    public BitSet get(int x, int y) {
        return bitPieces[x][y];
    }

    /**
     * Rotiert das Panel
     * 
     * @param dir gibt die Drehrichtung an; 1 = im UZS, 0 = gegen den UZS
     **/
    public void rotate(boolean dir) {
        BitSet[][] tmp;
        tmp = new BitSet[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tmp[i][j] = new BitSet(amountOfPlayers);
            }
        }

        if (dir == true) {
            // fuer Drehung im den Uhrzeigersinn
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    tmp[y][size - x - 1] = bitPieces[x][y];
                }
            }
        } else {
            // fuer Drehung gegen Uhrzeigersinn
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    tmp[size - y - 1][x] = bitPieces[x][y];
                }
            }
        }

        // kopiert die Eintraege des temporaeren Arrays in das permanente
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                bitPieces[x][y] = tmp[x][y];
            }
        }
    }
}
