package strategy;

import java.util.BitSet;

public class BitPanel {

    private BitSet bitPieces;        //Eine riesige Ansammlung an Bits, die ein Panel resembliert
    private int    size;             //Ist die Größe des Panels
    private int    amountOfPlayers;  //Ist die Anzahl der Spieler

    /**
     * Konstruktor: Erzeugt ein Panel ohne besetzte Felder
     * 
     * @param pSize ist die Seitenlänge des Panels
     * @param amountOfPlayers ist die Anzahl an Spieler
     **/
    public BitPanel(int pSize, int amountOfPlayers) {
        this.size = pSize;
        this.bitPieces = new BitSet(pSize * pSize * amountOfPlayers);
        this.amountOfPlayers = amountOfPlayers;
    }
    
    /**
     * Konstruktor: Erzeugt ein Panel mit möglicherweise besetzten Feldern.
     * 
     * @param pieces überschreibt das Panel
     * @param am ist die Anzahl an Spieler
     * @param size ist die Seitenlänge des Panels
     */
    public BitPanel(BitSet pieces, int am, int size) {
    	bitPieces = pieces;
    	amountOfPlayers = am;
    	this.size = size;
    }

    /**
     * Get-Methode des Attributs <code> size </code>
     * 
     * @return die Seitenlänge des Panels
     */
    public int getSize() {
        return size;
    }

    /**
     * Es wird überprüft, ob ein bestimmtes Feld leer ist
     * 
     * @param x ist die X-Position des Felds
     * @param y ist die Y-Position des Felds
     * @return true falls ja, ansonsten false
     */
    public boolean emptyBitSet(int x, int y) {
        int rx = x * amountOfPlayers;
        int ry = y * amountOfPlayers * size;
        
        return bitPieces.get(rx + ry, rx + ry + amountOfPlayers).cardinality() == 0;
    }
     
     /**
 	 * Setzt den Boolean-Wert <code> b </code> in das BitSet <code> bitPieces </code> an die Stelle <code> index </code>
 	 * Zusammengefasst: An einem bestimmten Feld wird ein Spielstein gesetzt.
 	 * 
 	 * @param x ist die X-Position des Felds
 	 * @param y ist die Y-Position des Felds
 	 * @param b ist der Boolean-Wert, der an der Stelle <code> index </code> stehen soll
 	 * @param index zeigt an, zu wem der Spielstein gehört (0 = Player 1, 1 = Player 2...)
 	 */
     public void set(int x, int y, boolean b, int index) {
    	 int rx = x * amountOfPlayers;
         int ry = y * amountOfPlayers * size;
         
    	 bitPieces.set(rx + ry + index, b);
     }
     
     /**
 	 * Setzt das uebergebene Piece an die uebergebenen Koordinaten
 	 * Wird benutzt für die Methode <code> rotate(boolean dir) </code>.
 	 * Um ein Spielstein zu setzen, verwendet man die Methode: <code> set(int x, int y, int index) </code>
 	 * 
 	 * @param x ist die X-Position des Spielfelds
 	 * @param y ist die Y-Position des Spielfelds
 	 * @param b ist der Boolean-Wert, der an der Stelle <code> index </code> stehen soll
 	 * @param index zu platzierendes Piece (0 = Player 1, 1 = Player 2...)
 	 */
     private void set(int x, int y, BitSet piece) {
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
    	
    	if(dir) {
    		//Clockwise (right) = Change columns
    		for(int x = 0; x < size; x++) {
    			for(int y = 0; y < size; y++) {
    				set(y, size - x - 1, tmp.get(x, y));
    			}
    		}
    	} else {
    		//Counter-Clockwise (left) = Change rows
    		for(int x = 0; x < size; x++) {
    			for(int y = 0; y < size; y++) {
    				set(size - y - 1, x, tmp.get(x, y));
    			}
    		}
    	}
    }

    /**
	 * Diese Methode überprüft, ob das als Parameter übergebene Objekt dasselbe Objekt ist, wie 
	 * die Instanz dieser Klasse, die diese Methode durcharbeitet.
	 */
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
