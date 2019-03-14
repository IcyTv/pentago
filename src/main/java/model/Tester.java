package model;

import java.awt.Color;

/**
 * Tester
 */
public class Tester {

    public static void main(String[] args) {
        Gamemaster gm = new Gamemaster(9, 3, 2);
        gm.addPlayer(gm.generatePlayer("Test", Color.BLACK, false));

        gm.playRound(Gamemaster.getTurn(0, 0, 0, 0, false, gm.getBoard()));
        gm.playRound(Gamemaster.getTurn(0, 0, 0, 0, false, gm.getBoard()));
        gm.playRound(Gamemaster.getTurn(0, 0, 0, 0, false, gm.getBoard()));
        gm.playRound(Gamemaster.getTurn(0, 0, 0, 0, false, gm.getBoard()));
        gm.playRound(Gamemaster.getTurn(0, 0, 0, 0, false, gm.getBoard()));
    }
}