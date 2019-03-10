 


public class Board {

    private Panel[][] panels;
    private boolean done;
    private Player winner;
    private int bSize;
    private int pSize;
    private Gamemaster gm;
    
    public Board(int bSize, int pSize, Gamemaster gm) {
        this.bSize = bSize;
        this.pSize = pSize;
        this.gm = gm;
        done = false;
        winner = null;
        
        panels = new Panel[bSize/pSize][bSize/pSize];
        for (int x = 0; x< bSize/pSize; x++) {
            for (int y = 0; y< bSize/pSize; y++) {
                panels[x][y] = new Panel(pSize);
            }
        }
    }
    
    public boolean won(Player currentPlayer)
    {
    	if (rowIsFilled())
    	{
    		winner = currentPlayer;
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    private boolean rowIsFilled()
    {
    	for (int i = 0; i < bSize; i++)
    	{
    		for (int j = 0; j < bSize; j++)
    		{
    			if (horizontalRow(i,j) || verticalRow(i,j) || upLeftRow(i,j) || upRightRow(i,j))
    			{
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    private boolean horizontalRow(int i, int j)
    {
    	if (j > this.bSize-5) return false;
    	
    	Piece testPiece = this.get(i, j);
    	if (testPiece == null)
    	{
    		return false;
    	}
    	else
    	{
    		for (int k = 0; k < 5; k++)
    		{
    			if (testPiece != this.get(j+k, i))
    			{
    				return false;
    			}
    		}
    		return true;
    	}
    }
    
    private boolean verticalRow(int i, int j)
    {
    	if (i > this.bSize-5) return false;
    	
    	Piece testPiece = this.get(i, j);
    	if (testPiece == null)
    	{
    		return false;
    	}
    	else
    	{
    		for (int k = 0; k < 5; k++)
    		{
    			if (testPiece != this.get(j, i+k))
    			{
    				return false;
    			}
    		}
    		return true;
    	}
    }
    
    private boolean upLeftRow(int i, int j)
    {
    	if (j < 4 || i > this.bSize-5) return false;
    	
    	Piece testPiece = this.get(i, j);
    	if (testPiece == null)
    	{
    		return false;
    	}
    	else
    	{
    		for (int k = 0; k < 5; k++)
    		{
    			if (testPiece != this.get(j-k, i+k))
    			{
    				return false;
    			}
    		}
    		return true;
    	}
    }
    
    private boolean upRightRow(int i, int j)
    {
    	if (j > this.bSize-5 || i > this.bSize-5) return false;
    	
    	Piece testPiece = this.get(i, j);
    	if (testPiece == null)
    	{
    		return false;
    	}
    	else
    	{
    		for (int k = 0; k < 5; k++)
    		{
    			if (testPiece != this.get(j+k, i+k))
    			{
    				return false;
    			}
    		}
    		return true;
    	}
    }
    
    public void set(int x, int y, Piece piece) {
       int size = panels[0][0].getSize();
       panels[x/size][y/size].set(x%size, y%size, piece);
    }
    
    public Piece get(int x, int y) {
       int size = panels[0][0].getSize();
       return panels[x/size][y/size].get(x%size, y%size);
    }
    
    public void rotate(int x, int y, boolean dir) {
        panels[x][y].rotate(dir);
    }
    
    @Override
    public String toString() {
        String ret = "";
        int markerY = 0;
        int markerX = 0;
        for (int i = 0; i < bSize; i++) {
            for (int n = 0; n < bSize; n++) {
            	markerX++;
                ret += getPlayerNumber(i, n) + " ";
                if (markerX == pSize)
                {
                	ret += "| ";
                	markerX = 0;
                }
            }
            ret += "\n";
            markerY++;
            if (markerY == pSize)
            {
            	for (int k = 0; k < bSize; k++)
            	{
            		ret += "___";
            	}
            	markerY = 0;
            	ret += "\n";
            }
        }
        return ret;
    }
    
    private int getPlayerNumber(int i, int j)
    {
    	Piece piece = this.get(i, j);
    	if (piece == null) return 0;
    	
    	Player playingP  = piece.getPlayer();
    	Node   node      = gm.getQueue().getFirstNode();
    	
    	for (int k = 0; k < gm.getAmountOfPlayers(); k++)
    	{
    		if (playingP == node.getPlayer())
    		{
    			return k+1;
    		}
    		else
    		{
    			node = node.getNext();
    		}
    	}
    	
    	return 0;
    }
    
}
