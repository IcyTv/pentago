package model;

import java.util.BitSet;

public class Strategy {
	
	public int MAXDEPTH;
	public static final int SCOREMULTIPLIER = 3;
	
    private Board  board;
    private Turn   bestTurn;
    private int    amountOfPlayers;
    private int    ownNumber;
    private BitSet ownPiece;
    private BitBoard currentState;

    public Strategy(Board board, int amountOfPlayers, int ownNumber) 
    {
    	this.amountOfPlayers = amountOfPlayers;
    	this.ownNumber = ownNumber;
        this.board = board;
        this.bestTurn = null;
        this.currentState = new BitBoard(board.getBSize(), board.getPSize(), amountOfPlayers);
        
        this.ownPiece = new BitSet(amountOfPlayers);
        for (int i = 0; i < ownPiece.size(); i++)
        {
        	ownPiece.set(i, (i+1 == ownNumber));
        }
        
        this.MAXDEPTH = 3 + ownNumber;
    }

    /** Gibt bisher nur einen Dummy-Turn zurueck **/
    public Turn findBestTurn() 
    {
    	encode();
    	System.out.println(".");
    	for (int i = 0; i < currentState.getBSize(); i++)
    	{
    		for (int j = 0; j < currentState.getBSize(); j++)
    		{
    			if (!currentState.emptyBitSet(i, j))
    			{
    				if (currentState.get(i, j).get(ownNumber-1) == ownPiece.get(ownNumber-1))
    				{
    					System.out.println("Mein Piece auf "+ i + ", "+ j);
    				}
    				else
    				{
    					System.out.println("Gegner Piece auf "+ i + ", "+ j);
    				}
    			}
    		}
    	}
        alphaBeta(ownNumber, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        Turn temp = bestTurn;
        bestTurn = null;
        return temp;
    }
    
    
    private int staticEvaluation(boolean maximizing)
    {
    	int boardSize = currentState.getBSize();
    	int score[][] = new int[boardSize][boardSize];
    	
    	for (int x = 0; x < boardSize; x++)
    	{
    		for (int y = 0; y < boardSize; y++)
    		{
    			score[x][y] += horizontalBitAlignment(x, y);
    			score[x][y] += verticalBitAlignment(x, y);
    			score[x][y] += upLeftBitAlignment(x, y);
    			score[x][y] += upRightBitAlignment(x, y);
    		}
    	}
    	
    	if (maximizing)
    	{
    		int max = Integer.MIN_VALUE;
    		for (int x = 0; x < boardSize; x++)
    		{
    			for (int y = 0; y < boardSize; y++)
    			{
    				max = Math.max(max, score[x][y]);
    			}
    		}
    		return max;
    	}
    	else
    	{
    		int min = Integer.MAX_VALUE;
    		for (int x = 0; x < boardSize; x++)
    		{
    			for (int y = 0; y < boardSize; y++)
    			{
    				min = Math.min(min, score[x][y]);
    			}
    		}
    		return min;
    	}
    }
    
    private int horizontalBitAlignment(int x, int y)
    {
    	int score = 1;
    	BitSet pieceOfReference = currentState.get(x, y);
    	
    	if (!currentState.emptyBitSet(x, y))
    	{
    		for (int i = 0; i < 4 && x+i < currentState.getBSize(); i++)
    		{
    			if (currentState.get(x+i, y) == pieceOfReference)
    			{
    				score *= SCOREMULTIPLIER;
    			}
    		}
    	}
    	
    	if (pieceOfReference.get(ownNumber-1) == ownPiece.get(ownNumber-1))
    	{
    		score *= -1;
    	}
    	return score;
    }
    
    private int verticalBitAlignment(int x, int y)
    {
    	int score = 1;
    	BitSet pieceOfReference = currentState.get(x, y);
    	
    	if (!currentState.emptyBitSet(x, y))
    	{
    		for (int i = 0; i < 4 && y+i < currentState.getBSize(); i++)
    		{
    			if (currentState.get(x, y+i) == pieceOfReference)
    			{
    				score *= SCOREMULTIPLIER;
    			}
    		}
    	}
    	
    	if (pieceOfReference.get(ownNumber-1) == ownPiece.get(ownNumber-1))
    	{
    		score *= -1;
    	}
    	return score;
    }
    
    private int upLeftBitAlignment(int x, int y)
    {
    	int score = 1;
    	BitSet pieceOfReference = currentState.get(x, y);
    	
    	if (!currentState.emptyBitSet(x, y))
    	{
    		for (int i = 0; i < 4 && x-i > -1 && y+i < currentState.getBSize(); i++)
    		{
    			if (currentState.get(x-i, y+i) == pieceOfReference)
    			{
    				score *= SCOREMULTIPLIER;
    			}
    		}
    	}
    	
    	if (pieceOfReference.get(ownNumber-1) == ownPiece.get(ownNumber-1))
    	{
    		score *= -1;
    	}
    	return score;
    }
    
    private int upRightBitAlignment(int x, int y)
    {
    	int score = 1;
    	BitSet pieceOfReference = currentState.get(x, y);
    	
    	if (!currentState.emptyBitSet(x, y))
    	{
    		for (int i = 0; i < 4 && x+i < currentState.getBSize() && y+i < currentState.getBSize(); i++)
    		{
    			if (currentState.get(x+i, y+i) == pieceOfReference)
    			{
    				score *= SCOREMULTIPLIER;
    			}
    		}
    	}
    	
    	if (pieceOfReference.get(ownNumber-1) == ownPiece.get(ownNumber-1))
    	{
    		score *= -1;
    	}
    	return score;
    }
    
    private void encode() 
    {
    	
    	for (int x = 0; x < currentState.getBSize(); x++)    //BitSet Array wird befüllt
    	{   
    		for (int y = 0; y < currentState.getBSize(); y++)
    		{
    			Piece realPiece = board.get(x,y);   //Chip im Board im Platz (Es wird durch das Board durchiteriert)
    			boolean codedPiece[] = new boolean[amountOfPlayers];   //Array in dem der Wert der Stelle codiert wird
    					
    			if(realPiece == null)              //Gibt es überhaupt einen Chip im Board an dieser spezifischen Stelle?   
    			{                        
    				for (int i = 0; i < codedPiece.length; i++)
    				{
    					codedPiece[i] = false;   //Niemandem gehört der Chip an der Stelle
    				}
    			} 
    			else
    			{
    				int num = realPiece.getPlayer().getNumber();      //Nummer des Spielers, dem der Chip gehört
    				for (int i = 0; i < codedPiece.length; i++)
    				{
    					codedPiece[i] = (num == i+1);       //Einem gehört der Chip und das wird dann als true gespeichert im Array
    				}
    			}
    			currentState.set(x, y, codedPiece);
    		}
    	}
    }
    
    
    private void findHighestScoreAndSaveBestTurn(int[][][][][] scores)
    {
    	System.out.println("");
    	int max = Integer.MIN_VALUE;
    	for (int x = 0; x < scores.length; x++)
    	{
    		for (int y = 0; y < scores[x].length; y++)
    		{
    			for (int rotX = 0; rotX < scores[x][y].length; rotX++)
    			{
    				for (int rotY = 0; rotY < scores[x][y][rotX].length; rotY++)
    				{
    					for (int dir = 0; dir < scores[x][y][rotX][rotY].length; dir++)
    					{
    						//System.out.println(scores[x][y][rotX][rotY][dir]);
    						if (scores[x][y][rotX][rotY][dir] > max && scores[x][y][rotX][rotY][dir] != -1)
    						{
    							max = scores[x][y][rotX][rotY][dir];
    							this.bestTurn = new Turn(x, y, rotX, rotY, (dir > 0), this.board);
    						}
    					}
    				}
    			}
    		}
    	}
    }
    
    public int alphaBeta(int depth, boolean maximizingPlayer, int alpha, int beta)
    {
    	System.out.println(depth);
    	//System.out.println(depth-ownNumber);
    	if (currentState.won())     //Man soll nicht weiter probieren, wenn das Board schon gewonnen ist...
    	{
    		if (maximizingPlayer)
    		{
    			//System.out.println("lost");
    			return Integer.MIN_VALUE;   //Gegner hat gewonnen, weil das Board gewonnen war, als der Bot am Zug war
    		}
    		else
    		{
    			//System.out.println("won");
    			return Integer.MAX_VALUE;    //Bot hat gewonnen, weil das Board gewonnen war, als der Gegner am Zug war
    		}
    	}
    	else if (currentState.draw())
    	{
    		//System.out.println("draw");
    		return 0;      //Unentschieden hat die Wertung 0
    	}
    	else if (depth >= MAXDEPTH)   //Abbruch, wenn der Bot zu tief im Baum ist
    	{
    		//System.out.println("ratter ratter ratter");
    		return staticEvaluation((depth-ownNumber)%amountOfPlayers == 0);       //Der Bot bewertet die momentane Lage
    	}
    	
    	//Index 1: Panelzeile
    	//Index 2: Panelspalte
    	//Index 3: Zeile im jeweiligen Panel
    	//Index 4: Spalte im jeweiligen Panel
    	//Index 5: Links oder Rechtsrotation
    	int scores[][][][][] = null;
    	int amountOfPanels = currentState.getBSize() / currentState.getPSize();
    	int boardSize      = currentState.getBSize();
    	if (depth == ownNumber)
    	{
    		scores = new int[boardSize][boardSize][amountOfPanels][amountOfPanels][2];
    	}
    	int maxEval, minEval, eval;
    	if (maximizingPlayer)   //Je nach dem ob der maximierende Spieler dran ist, werden Variablen anders gespeichert...
    	{
    		maxEval = Integer.MIN_VALUE;   //Man geht vom schlimmsten Fall aus
    		for (int x = 0; x < boardSize; x++)
    		{
    			for (int y = 0; y < boardSize; y++)
    			{
    				for (int rotX = 0; rotX < amountOfPanels; rotX++)
    				{
    					for (int rotY = 0; rotY < amountOfPanels; rotY++)
    					{
    						for (int rot = 0; rot < 2; rot++)
    						{
    	    					boolean rotDir = rot > 0;
    	    							
    	    					if (currentState.emptyBitSet(x, y))
    	    					{
    	    						boolean[] codedPiece = new boolean[amountOfPlayers];
    	    						int num = depth % amountOfPlayers;
    	    						for (int k = 0; k < codedPiece.length; k++)
    	    						{
    	    							codedPiece[k] = (num == k);
    	    						}
    	    						currentState.set(x, y, codedPiece);
    	    						currentState.rotate(rotX, rotY, rotDir);
    	    						
    	    						eval = alphaBeta(depth+1, !maximizingPlayer, alpha, beta);
    	    						if (depth == ownNumber)
    	    						{
    	    							scores[x][y][rotX][rotY][rot] = eval;
    	    						}
    	    								
    	    						currentState.rotate(rotX, rotY, !rotDir);
    	    						boolean[] undoChip = new boolean[amountOfPlayers];
    	    						for (int k = 0; k < undoChip.length; k++)
    	    						{
    	    							undoChip[k] = false;
    	    						}
    	    						maxEval = Math.max(eval, maxEval);
    	    						alpha = Math.max(alpha, maxEval);
    	    						currentState.set(x, y, undoChip);
    	    						if (beta <= alpha)
    	    						{
    	    							break;
    	    						}
    	    					}
    	    					else
    	    					{
    	    						if (depth == ownNumber)
    	    						{
    	    							scores[x][y][rotX][rotY][rot] = -1;
    	    						}
    	    					}
    						}
    						if (beta <= alpha)
    						{
    							break;
    						}
    					}
    					if (beta <= alpha)
						{
							break;
						}
    				}
    				if (beta <= alpha)
					{
						break;
					}
    			}
    			if (beta <= alpha)
				{
					break;
				}
    		}
    		
    		if (depth == ownNumber)
    		{
    			findHighestScoreAndSaveBestTurn(scores);
    		}
    		return maxEval;
    	}
    	else
    	{
    		minEval = Integer.MAX_VALUE;   //Man geht vom schlimmsten Fall aus
    		for (int x = 0; x < boardSize; x++)
    		{
    			for (int y = 0; y < boardSize; y++)
    			{
    				for (int rotX = 0; rotX < amountOfPanels; rotX++)
    				{
    					for (int rotY = 0; rotY < amountOfPanels; rotY++)
    					{
    						for (int rot = 0; rot < 2; rot++)
    						{
    	    					boolean rotDir = rot > 0;
    	    							
    	    					if (currentState.emptyBitSet(x, y))
    	    					{
    	    						boolean[] codedPiece = new boolean[amountOfPlayers];
    	    						int num = depth % amountOfPlayers;
    	    						for (int k = 0; k < codedPiece.length; k++)
    	    						{
    	    							codedPiece[k] = (num == k);
    	    						}
    	    						currentState.set(x, y, codedPiece);
    	    						currentState.rotate(rotX, rotY, rotDir);
    	    						
    	    						boolean nextPlayerMaximizingPlayer = (((depth+1-ownNumber) % amountOfPlayers) == 0);
    	    						eval = alphaBeta(depth+1, nextPlayerMaximizingPlayer, alpha, beta);
    	    						if (depth == 0)
    	    						{
    	    							scores[x][y][rotX][rotY][rot] = eval;
    	    						}
    	    								
    	    						currentState.rotate(rotX, rotY, !rotDir);
    	    						boolean[] undoChip = new boolean[amountOfPlayers];
    	    						for (int k = 0; k < undoChip.length; k++)
    	    						{
    	    							undoChip[k] = false;
    	    						}
    	    						minEval = Math.min(eval, minEval);
    	    						beta = Math.min(beta, minEval);
    	    						currentState.set(x, y, undoChip);
    	    						if (beta <= alpha)
    	    						{
    	    							break;
    	    						}
    	    					}
    	    					else
    	    					{
    	    						if (depth == 0)
    	    						{
    	    							scores[x][y][rotX][rotY][rot] = -1;
    	    						}
    	    					}
    						}
    						if (beta <= alpha)
    						{
    							break;
    						}
    					}
    					if (beta <= alpha)
						{
							break;
						}
    				}
    				if (beta <= alpha)
					{
						break;
					}
    			}
    			if (beta <= alpha)
				{
					break;
				}
    		}
    		return minEval;
    	}
    }
}
