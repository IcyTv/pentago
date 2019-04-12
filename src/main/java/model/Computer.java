package model;

import core.Constants.COLOR;

public class Computer extends Player {
    Strategy strategy;

    /**
     * Konstruktor der Klasse Computer
     * 
     * @param name  gewuenschter Spielername
     * @param color gewuenschte Spielerfarbe
     * @param board Spielbrett
     **/
    public Computer(String name, COLOR color, Board board, int amountOfPlayers, int number) {
        super(name, color, board, false, number);
        strategy = new Strategy(board, amountOfPlayers, number);
    }

    public void playRound() {
        super.playRound(strategy.findBestTurn()); // findBestTurn() gibt bisher nur Dummy-Turn zurueck (0,0,0,0,true)
    }

    public Turn getTurn() {
        return strategy.findBestTurn();
    }
}
