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
    
    public BitPanel(BitSet pieces, int am) {
    	bitPieces = pieces;
    	amountOfPlayers = am;
    }

    public int getSize() {
        return size;
    }

    public boolean emptyBitSet(int x, int y) {
        int rx = x * amountOfPlayers;
        int ry = y * amountOfPlayers * size;
//        System.out.println("rx + ry: " + rx + " " + ry); 
//        
//        System.out.println(bitPieces.get(rx + ry, rx + ry + 4).cardinality());
//        System.out.println(bitPieces);
//        
//        System.out.println();
        
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
         
         System.out.println(x + " " + y + " " + index); 
         
    	 bitPieces.set(rx + ry + index, true);
     }
     
     public void set(int x, int y, boolean b, int index) {
    	 int rx = x * amountOfPlayers;
         int ry = y * amountOfPlayers * size;
         
    	 bitPieces.set(rx + ry + index, b);
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
        
        return bitPieces.get(rx + ry, rx + ry + 4);
    }

    /**
     * Rotiert das Panel
     * 
     * @param dir gibt die Drehrichtung an; 1 = im UZS, 0 = gegen den UZS
     **/
    public void rotate(boolean dir) {
    	BitPanel tmp = new BitPanel((BitSet)bitPieces.clone(), amountOfPlayers);
        if (dir == true) {
            // fuer Drehung im den Uhrzeigersinn
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                	for(int pl = 0; pl < amountOfPlayers; pl++) {
                		set(y, size - x - 1, tmp.get(x, y).get(pl), pl);
                	}
                }
            }
        } else {
            // fuer Drehung gegen Uhrzeigersinn
            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                	for(int pl = 0; pl < amountOfPlayers; pl++) {
                		set(size - y - 1, x, tmp.get(x, y).get(pl), pl);
                	}
                }
            }
        }
    }
}
