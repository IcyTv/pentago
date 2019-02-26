 

import java.awt.Color;

public abstract class Player {
    
    protected Piece piece;
    protected String name;
    protected Board board;
    protected Color color;      //muss durch core.Constants.COLOR ersetzt werden
    protected Player nextPlayer;
    
    public Player(String name, Color color, Board board) { 
        this.board = board;
        this.name = name;
        this.color = color;
        piece = new Piece(this, color);
    }
    
    public void playRound(Turn turn){
        int pX = turn.getPiecePosition()[0];
        int pY = turn.getPiecePosition()[1];
        board.set(pX, pY, piece);
        
        int rX = turn.getRotatePosition()[0];
        int rY = turn.getRotatePosition()[1];
        boolean dir = turn.getRotateDirection();
        board.rotate(rX, rY, dir);
    }
    
    public Color getColor() {
        return color;
    }
    
    public String getName() {
       return name;
    }
    
    public Player getNextPlayer() {
        return nextPlayer;
    }
    
    public void setNextPlayer(Player p) {
        nextPlayer = p;
    }
}
