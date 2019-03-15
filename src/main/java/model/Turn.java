package model;

/**
 * Die Klasse Turn dient zum Kapseln aller Informationen, die zum Ausfuehren
 * eines Zugs noetig sind. Beim Konstruktoraufruf werden die Koordinaten
 * uebergeben, an die ein Piece gesetzt werden soll, sowie die Koordinaten des
 * zu drehenden Panels und die Drehrichtung. Der Turn ueberprueft sich dann
 * selbst auf Validitaet und wirft bei Regelmissachtung eine Exception.
 * 
 * Die Zugdaten koennen ueber get-Methoden abgefragt werden.
 **/

public class Turn {

    private int[] piecePosition;
    private int[] rotatePosition;
    private boolean rotateDirection;

    private Board board;

    public Turn(int pieceX, int pieceY, int rotX, int rotY, boolean dir, Board board, boolean softFail)
            throws IllegalArgumentException {
        piecePosition = new int[2];
        piecePosition[0] = pieceX; // Die gewuenschte X-Position des Pieces wird gespeichert
        piecePosition[1] = pieceY; // Die gewuenschte Y-Position des Pieces wird gespeichert

        rotatePosition = new int[2];
        rotatePosition[0] = rotX; // Die X-Position des zu drehenden Panels wird gespeichert
        rotatePosition[1] = rotY; // Die Y-Position des zu drehenden Panels wird gespeichert

        rotateDirection = dir;

        this.board = board;

        if (!valid() && !softFail) {
            throw new IllegalArgumentException("Illegal turn!");
        }
    }

    public Turn(int pieceX, int pieceY, int rotX, int rotY, boolean dir, Board board) {
        this(pieceX, pieceY, rotX, rotY, dir, board, false);
    }

    public boolean valid() {
        if (board.get(piecePosition[0], piecePosition[1]) != null) {
            return false;
        }
        return true;
    }

    public int[] getPiecePosition() {
        return piecePosition;
    }

    public int[] getRotatePosition() {
        return rotatePosition;
    }

    public boolean getRotateDirection() {
        return rotateDirection;
    }

}
