 

import java.awt.Color;

public abstract class Player {
    
    protected Piece piece;
    protected String name;
    protected Board board;
    protected Color color;      //muss durch core.Constants.COLOR ersetzt werden
    protected boolean human;
    
    public Player(String name, Color color, Board board, boolean human) { 
        this.board = board;
        this.name = name;
        this.color = color;
        this.human = human;
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
    
    public boolean isHuman()
    {
    	return this.human;
    }
    
    public Piece getPiece()
    {
    	return this.piece;
    }
}
