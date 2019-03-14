package model;

/** Die Klasse Piece stellt die Spielsteine dar. Jedes Piece hat eine Farbe und kennt seinen
    Spieler.
    
    Autor: Felix **/

import java.awt.Color;

public class Piece {

    private Color color;
    private Player player;

    /**
     * Konstruktor der Klasse Piece
     * 
     * @param player Spieler, dem das Piece gehoert
     * @param color  Farbe des Pieces
     **/
    public Piece(Player player, Color color) {
        this.player = player;
        this.color = color;
    }

    public Player getPlayer() {
        return player;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return Integer.toString(color.getRed());
    }

}
