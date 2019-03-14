package model;

import java.util.Random;

public class Strategy {
    private Random r;

    public Strategy() {
        this.r = new Random();
    }

    /** Gibt bisher nur einen Dummy-Turn zurueck **/
    public Turn findBestTurn() {
        return new Turn(0, 0, 0, 0, true, null);
    }
}
