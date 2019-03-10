import java.awt.Color;
public class Human extends Player
{
   
    public Human(String name, Color color, Board board)
    {
        super(name, color, board, true);
        
    }
    
    @Override
    public void playRound(Turn turn) {
        super.playRound(turn);
    }
}
