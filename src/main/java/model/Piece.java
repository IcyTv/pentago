package model;

/** Die Klasse Piece stellt die Spielsteine dar. Jedes Piece hat eine Farbe und kennt seinen
    Spieler.
    
    Autor: Felix **/

import java.awt.Color;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Piece)) {
            return false;
        }
        Piece piece = (Piece) o;
        return Objects.equals(color, piece.color) && Objects.equals(player, piece.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, player);
    }
}
