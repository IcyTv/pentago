/** Die Klasse Human modelliert den menschlichen Spieler.
  * Sie erbt alle Attribute und Methoden der Oberklasse Player.
  * Ein Human dient lediglich der Ausfuehrung uebergebener Spielzuege.
   **/

import java.awt.Color;
public class Human extends Player
{
    
    /**Konstruktor der Klasse Human
     * @param name      gewuenschter Spielername
     * @param color     gewuenschte Spielerfarbe
     * @param board     Spielbrett
       **/
    public Human(String name, Color color, Board board)
    {
        super(name, color, board, true);
        
    }
    
    /** Ueberschreibt die vererbte Methode playRound(Turn turn) des Players.
        Fuehrt einen uebergebenen Spielzug aus (setzt einen Stein und dreht das gewuenschte 
        Panel in die gewuenschte Richtung). 
        @param turn     Auszufuehrender Spielzug
        **/
    @Override
    public void playRound(Turn turn) {
        super.playRound(turn);
    }
}
