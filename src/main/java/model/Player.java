package model;

/** Abstrakte Oberklasse fuer die beiden Spielertypen Human und Computer.
    Jeder Player kennt das Board und hat ein Piece.
    Außerdem hat jeder Player einen Namen, eine Farbe und ist entweder ein Mensch oder kein Mensch.**/

import java.awt.Color;

public abstract class Player {

    protected Piece piece;
    protected String name;
    protected Board board;
    protected Color color; // muss durch core.Constants.COLOR ersetzt werden
    protected boolean human;

    /**
     * Konstruktor der Klasse Player
     * 
     * @param name  gewuenschter Spielername
     * @param color gewuenschte Spielerfarbe
     * @param board Spielbrett
     * @param human 0=Spieler ist Computer, 1=Spieler ist menschlich
     **/
    public Player(String name, Color color, Board board, boolean human) {
        this.board = board;
        this.name = name;
        this.color = color;
        this.human = human;
        piece = new Piece(this, color);
    }

    /**
     * Setzt einen Stein an die im Turn festgelegte Stelle und dreht das festgelegte
     * Panel in die festgelegte Richtung
     **/
    public void playRound(Turn turn) {
        int pX = turn.getPiecePosition()[0];
        int pY = turn.getPiecePosition()[1];
        board.set(pX, pY, piece);

        int rX = turn.getRotatePosition()[0];
        int rY = turn.getRotatePosition()[1];
        boolean dir = turn.getRotateDirection();
        board.rotate(rX, rY, dir);
    }

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public boolean isHuman() {
        return this.human;
    }

    public Piece getPiece() {
        return this.piece;
    }
}
