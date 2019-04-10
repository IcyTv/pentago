package model;

/** Die Klasse Human modelliert den menschlichen Spieler.
  * Sie erbt alle Attribute und Methoden der Oberklasse Player.
  * Ein Human dient lediglich der Ausfuehrung uebergebener Spielzuege.
   **/

import static core.Constants.COLOR;

public class Human extends Player {

    /**
     * Konstruktor der Klasse Human
     * 
     * @param name  gewuenschter Spielername
     * @param color gewuenschte Spielerfarbe
     * @param board Spielbrett
     **/
    public Human(String name, COLOR color, Board board, int number) {
        super(name, color, board, true, number);

    }

    /**
     * Ueberschreibt die vererbte Methode playRound(Turn turn) des Players. Fuehrt
     * einen uebergebenen Spielzug aus (setzt einen Stein und dreht das gewuenschte
     * Panel in die gewuenschte Richtung).
     * 
     * @param turn Auszufuehrender Spielzug
     **/
    @Override
    public void playRound(Turn turn) {
        super.playRound(turn);
    }
}
