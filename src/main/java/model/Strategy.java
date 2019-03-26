package model;

import java.util.BitSet;
import java.util.Random;

public class Strategy {
	
	public static final int DEPTH = 3;
	
    private Random r;
    private Board  board;
    private Turn   bestTurn;
    private BitSet test;
    
    private BitSet[] currentState;

    public Strategy(Board board) {
        this.r = new Random();
        this.board = board;
        this.bestTurn = null;
    }

    /** Gibt bisher nur einen Dummy-Turn zurueck **/
    public Turn findBestTurn() {
        alphaBeta(0, true, Integer.MAX_VALUE, Integer.MIN_VALUE);
        Turn temp = bestTurn;
        bestTurn = null;
        return temp;
    }
    
    public void encode(Gamemaster gm) {
    	
    	Board board = gm.getBoard();
    	
    	currentState = new BitSet[(board.getBSize() / board.getPSize()) * (board.getBSize() / board.getPSize())];
    	
    	for(int i = 0; i < currentState.length; i++) {
    		currentState[i] = new BitSet(board.getPSize() * board.getPSize() * gm.getAmountOfPlayers());
    		for(int n = 0; n < currentState[i].size(); n++) {
    			Piece tmp = board.get(n * i, (n % board.getPSize()) * i);
    			if(tmp == null) {
    				addBits(currentState[i], n * gm.getAmountOfPlayers(), false, false, false, false);
    			} else {
    				int num = tmp.getPlayer().getNumber();
					addBits(currentState[i], n * gm.getAmountOfPlayers(), 
							num == 1, 
							num == 2, 
							num == 3, 
							num == 4);
    			}
    		}
    	}
    }
    
    private void addBits(BitSet bitSet, int index, boolean a, boolean b, boolean c, boolean d) {
    	bitSet.set(index    , a);
    	bitSet.set(index + 1, b);
    	bitSet.set(index + 2, c);
    	bitSet.set(index + 3, d);
    }
    
    public int alphaBeta(int depth, boolean maximizingPlayer, int alpha, int beta)
    {
    	if (board.won())     //Man soll nicht weiter probieren, wenn das Board schon gewonnen ist...
    	{
    		if (maximizingPlayer)
    		{
    			return -10000;   //Gegner hat gewonnen, weil das Board gewonnen war, als der Bot am Zug war
    		}
    		else
    		{
    			return 10000;    //Bot hat gewonnen, weil das Board gewonnen war, als der Gegner am Zug war
    		}
    	}
    	else if (board.draw())
    	{
    		return 0;      //Unentschieden hat die Wertung 0
    	}
    	else if (depth == DEPTH)   //Abbruch, wenn der Bot zu tief im Baum ist
    	{
    		return 0; //staticEvaluation();       //Der Bot bewertet die momentane Lage
    	} 
    	
    	int maxEval, minEval, eval;
    	if (maximizingPlayer)   //Je nach dem ob der maximierende Spieler dran ist, werden Variablen anders gespeichert...
    	{
    		maxEval = Integer.MIN_VALUE;   //Man geht vom schlimmsten Fall aus
    		for (int x = 0; x < board.getBSize(); x++)   //Man iteriert durch die Zeilen
    		{
    			for (int y = 0; y < board.getBSize(); y++)  //Man iteriert durch die Spalten einer Zeile
    			{
    				if (board.get(x, y) != null)   //Ist der anzuschauende Platz schon besetzt?
    				{
    					continue;   //OK ich guck weiter
    				}
    				
    				board.set(x, y, piece);
    			}
    		}
    	}
    	else
    	{
    		
    	}
    }
}
