package strategy;

import java.util.BitSet;

public class BitPanel {

    private BitSet bitPieces;
    private int size;
    private int amountOfPlayers;

    /**
     * Konstruktor Erzeugt ein Panel ohne besetzte Felder
     * 
     * @param pSize Seitenlaenge des Panels
     * @param amountOfPlayers anzahl der spieler
     **/
    public BitPanel(int pSize, int amountOfPlayers) {
        this.size = pSize;
        this.bitPieces = new BitSet(pSize * pSize * amountOfPlayers);
        this.amountOfPlayers = amountOfPlayers;
    }
    
    public BitPanel(BitSet pieces, int am, int size) {
    	bitPieces = pieces;
    	amountOfPlayers = am;
    	this.size = size;
    }

    public int getSize() {
        return size;
    }

    public boolean emptyBitSet(int x, int y) {
        int rx = x * amountOfPlayers;
        int ry = y * amountOfPlayers * size;
        
        return bitPieces.get(rx + ry, rx + ry + amountOfPlayers).cardinality() == 0;
    }

    /**
     * Setzt das uebergebene Piece an die uebergebenen Koordinaten
     * 
     * @param x     X-Positoin des Piece
     * @param y     Y-Position des Piece
     * @param index zu platzierendes Piece (0 = Player 1, 1 = Player 2...)
     **/
     public void set(int x, int y, int index) {
         int rx = x * amountOfPlayers;
         int ry = y * amountOfPlayers * size;
         
    	 bitPieces.set(rx + ry + index, true);
     }
     
     public void set(int x, int y, boolean b, int index) {
    	 int rx = x * amountOfPlayers;
         int ry = y * amountOfPlayers * size;
         
    	 bitPieces.set(rx + ry + index, b);
     }
     
     public void set(int x, int y, BitSet piece) {
    	 int rx = x * amountOfPlayers;
    	 int ry = y * amountOfPlayers * size;
    	 
    	 for(int i = 0; i < amountOfPlayers; i++) {
    		 bitPieces.set(rx + ry + i, piece.get(i));
    	 }
     }

    /**
     * Gibt das Piece an den uebergebenen Koordinaten zurueck
     * 
     * @param x: X-Koordinate
     * @param y: Y-Koordianate
     **/
    public BitSet get(int x, int y) {
        int rx = x * amountOfPlayers;
        int ry = y * amountOfPlayers * size;
        
        return bitPieces.get(rx + ry, rx + ry + amountOfPlayers);
    }

    /**
     * Rotiert das Panel
     * 
     * @param dir gibt die Drehrichtung an; 1 = im UZS, 0 = gegen den UZS
     **/
    public void rotate(boolean dir) {
    	
    	BitPanel tmp = new BitPanel((BitSet)bitPieces.clone(), amountOfPlayers, this.size);
    	
    	bitPieces.clear();
    	
    	
    	//Transpose
    	for(int x = 0; x < size; x++) {
    		for(int y = 0; y < size; y++) {
    			set(y, x, tmp.get(x, y));
    		}
    	}
    	
    	tmp = new BitPanel((BitSet)bitPieces.clone(), amountOfPlayers, this.size);
    	bitPieces.clear();
    	
    	if(dir) {
    		//Clockwise (right) = Change columns
    		for(int x = 0; x < size; x++) {
    			for(int y = 0; y < size; y++) {
    				set(size - x - 1, y, tmp.get(x, y));
    			}
    		}
    	} else {
    		//Counter-Clockwise (left) = Change rows
    		for(int x = 0; x < size; x++) {
    			for(int y = 0; y < size; y++) {
    				set(x, size - y - 1, tmp.get(x, y));
    			}
    		}
    	}
    }
    
    @Override
    public String toString() {
    	return bitPieces.toString();
    }

    @Override
    public boolean equals(Object o) {
    	if(o.getClass().equals(this.getClass())) {
    		BitPanel pther = (BitPanel) o;
    		for(int x = 0; x < size; x++) {
    			for(int y = 0; y < size; y++) {
    				if(!this.get(x, y).equals(pther.get(x, y))) {
    					return false;
    				}
    			}
    		}
    		return true;
    	} else {
    		return false;
    	}
    }

}
