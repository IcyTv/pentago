package model;

/** Die Klasse Computer modelliert den Computer-Spieler
 *  Sie erbt alle Attribute und Methoden der Oberklasse Player.
 *  Der Computer wird aufgefordert, einen Spielzug zu machen. Er findet dann mithilfe seiner 
 *  Strategie einen guenstigen Zug und fuehrt ihn dann aus.
   **/
import java.awt.Color;

public class Computer extends Player {
    Strategy strategy;

    /**
     * Konstruktor der Klasse Computer
     * 
     * @param name  gewuenschter Spielername
     * @param color gewuenschte Spielerfarbe
     * @param board Spielbrett
     **/
    public Computer(String name, Color color, Board board) {
        super(name, color, board, false);
        strategy = new Strategy();
    }

    public void playRound() {
        super.playRound(strategy.findBestTurn()); // findBestTurn() gibt bisher nur Dummy-Turn zurueck (0,0,0,0,true)
    }
}
