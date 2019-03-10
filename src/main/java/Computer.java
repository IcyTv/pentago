import java.awt.Color;

public class Computer extends Player
{
    Strategy strategy;
    public Computer(String name, Color color, Board board) {
        super(name, color, board, false);
        strategy = new Strategy();
    }
    
    
    public void playRound() {
        super.playRound(strategy.findBestTurn());   //findBestTurn() gibt bisher nur Dummy-Turn zurueck (0,0,0,0,true)
    }
}
