package strategy;

import java.util.BitSet;

public class BitBoard {

	/**
	 * Eine Klasse, die ein Board repräsentiert. Das Board besteht jedoch aus Bits und nicht aus Piece-Objekten wie beim "normalen" Board
	 * Man kann darauf operieren, wie beim "normalen" Board, jedoch sind die Parameter verändert.
	 */
	
	private BitPanel[][] bitPanels;      //Das Board besteht aus Panels wie das normale Board, aber hier aus BitPanels
	private int bSize;        //Länge des Boards
	private int pSize;        //Länge, die ein Panel haben soll

	/**
	 * Konstruktor der Klasse.
	 * Es werden lediglich Attribute initialisiert.
	 * 
	 * @param bSize ist die Größe, die das Board haben soll
	 * @param pSize ist die Größe, die jedes Panel haben soll
	 * @param amountOfPlayers ist die Anzahl der Spieler, die es gibt
	 */
	public BitBoard(int bSize, int pSize, int amountOfPlayers) {
		this.bSize = bSize;
		this.pSize = pSize;

		bitPanels = new BitPanel[bSize / pSize][bSize / pSize];   //(bSize / pSize) = Anzahl der Panels
		for (int x = 0; x < bSize / pSize; x++) {
			for (int y = 0; y < bSize / pSize; y++) {
				bitPanels[x][y] = new BitPanel(pSize, amountOfPlayers);
			}
		}
	}

	/**
	 * Es wird überprüft, ob ein Spieler gewonnen hat.
	 * Ein Spieler hat gewonnen, wenn er eine Fünferreihe belegt.
	 * Diese Reihe kann entweder vertikal, horizontal oder diagonal orientiert sein.
	 * 
	 * @return true falls ja, ansonsten false
	 */
	public boolean won() {
		return rowIsFilled();
	}

	/**
	 * Es wird geschaut, ob das Board voll ist
	 * 
	 * @return
	 */
	public boolean draw() {
		for (int x = 0; x < this.bSize; x++) {
			for (int y = 0; y < this.bSize; y++) {
				if (this.emptyBitSet(x, y)) {   //Wenn ein Feld frei ist, kann es kein Unentschieden geben
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Diese Methode überprüft, ob es eine Fünferreihe mit denselben Spielsteinen gibt.
	 * Diese Reihe kann entweder vertikal, horizontal oder diagonal orientiert sein.
	 * 
	 * @return true falls ja, ansonsten false
	 */
	private boolean rowIsFilled() {
		for (int x = 0; x < bSize; x++) {
			for (int y = 0; y < bSize; y++) {
				if (horizontalRow(x, y) || verticalRow(x, y) || upLeftRow(x, y) || upRightRow(x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Diese Methode überprüft, ob es von einem bestimmten Feld aus eine horizontal orientierte Fünferreihe gibt.
	 * 
	 * @param x ist die X-Koordinate des Feld
	 * @param y ist die Y-Koordinate des Feld
	 * @return true falls ja, ansonsten false
	 */
	private boolean horizontalRow(int x, int y) {
		if (x > this.bSize - 5)
			return false;

		BitSet testPiece = this.get(x, y);
		if (this.emptyBitSet(x, y)) {
			return false;
		} else {
			for (int k = 0; k < 5; k++) {
				if (!testPiece.equals(this.get(x + k, y))) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Diese Methode überprüft, ob es von einem bestimmten Feld aus eine vertikal orientierte Fünferreihe gibt.
	 * 
	 * @param x ist die X-Koordinate des Feld
	 * @param y ist die Y-Koordinate des Feld
	 * @return true falls ja, ansonsten false
	 */
	private boolean verticalRow(int x, int y) {
		if (y > this.bSize - 5)
			return false;

		BitSet testPiece = this.get(x, y);
		if (this.emptyBitSet(x, y)) {
			return false;
		} else {
			for (int k = 0; k < 5; k++) {
				if (!testPiece.equals(this.get(x, y + k))) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Diese Methode überprüft, ob es von einem bestimmten Feld aus eine diagonal orientierte Fünferreihe gibt.
	 * Diagonal bedeutet in diesem Kontext nach oben links verlaufend
	 * 
	 * @param x ist die X-Koordinate des Feld
	 * @param y ist die Y-Koordinate des Feld
	 * @return true falls ja, ansonsten false
	 */
	private boolean upLeftRow(int x, int y) {
		if (x < 4 || y > this.bSize - 5)
			return false;

		BitSet testPiece = this.get(x, y);
		if (this.emptyBitSet(x, y)) {
			return false;
		} else {
			for (int k = 0; k < 5; k++) {
				if (!testPiece.equals(this.get(x - k, y + k))) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Diese Methode überprüft, ob es von einem bestimmten Feld aus eine diagonal orientierte Fünferreihe gibt.
	 * Diagonal bedeutet in diesem Kontext nach oben rechts verlaufend
	 * 
	 * @param x ist die X-Koordinate des Feld
	 * @param y ist die Y-Koordinate des Feld
	 * @return true falls ja, ansonsten false
	 */
	private boolean upRightRow(int x, int y) {
		if (x > this.bSize - 5 || y > this.bSize - 5)
			return false;

		BitSet testPiece = this.get(x, y);
		if (this.emptyBitSet(x, y)) {
			return false;
		} else {
			for (int k = 0; k < 5; k++) {
				if (!testPiece.equals(this.get(x + k, y + k))) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * Es wird überprüft, ob ein Feld durch ein Spielstein besetzt ist.
	 * 
	 * @param x ist die X-Koordinate des Feld
	 * @param y ist die Y-Koordinate des Feld
	 * @return true falls ja, ansonsten false
	 */
	public boolean emptyBitSet(int x, int y) {
		return bitPanels[x / pSize][y / pSize].emptyBitSet(x % pSize, y % pSize);
	}

	/**
 	 * Setzt den Boolean-Wert <code> b </code> in das BitSet <code> bitPieces </code>, das sich
 	 * in der Klasse BitPanel befindet an die Stelle <code> index </code>
 	 * Zusammengefasst: An einem bestimmten Feld wird ein Spielstein gesetzt.
 	 * 
 	 * @param x ist die X-Position des Felds
 	 * @param y ist die Y-Position des Felds
 	 * @param b ist der Boolean-Wert, der an der Stelle <code> index </code> stehen soll
 	 * @param index zeigt an, zu wem der Spielstein gehört (0 = Player 1, 1 = Player 2...)
 	 */
	public void set(int x, int y, boolean b, int index) {
		bitPanels[x / pSize][y / pSize].set(x % pSize, y % pSize, b, index);
	}
	
	/**
	 * Diese Methode gibt das Piece an einem bestimmten Feld zurück
	 * 
	 * @param x ist die X-Position des Felds
	 * @param y ist die Y-Position des Felds
	 * @return das Piece am Feld
	 */
	public BitSet get(int x, int y) {
		return bitPanels[x / pSize][y / pSize].get(x % pSize, y % pSize);
	}

	/**
	 * Dreht ein bestimmtes Panel in eine Richtung
	 * 
	 * @param x ist die X-Koordinate des Panels
	 * @param y ist die Y-Koordinate des Panels
	 * @param dir gibt die Drehrichtung an (false = links herum, true = rechts herum)
	 */
	public void rotate(int x, int y, boolean dir) {
		bitPanels[x][y].rotate(dir);
	}

	/**
	 * Gibt die Größe eines Panels zurück
	 * 
	 * @return die Größe eines Panels
	 */
	public int getPSize() {
		return pSize;
	}

	/**
	 * Gibt die Größe des Boards zurück
	 * 
	 * @return die Größe des Boards
	 */
	public int getBSize() {
		return bSize;
	}
	
	/**
	 * Diese Methode überprüft, ob das als Parameter übergebene Objekt dasselbe Objekt ist, wie 
	 * die Instanz dieser Klasse, die diese Methode durcharbeitet.
	 */
	@Override
	public boolean equals(Object other) {
		if(other.getClass().equals(this.getClass())) {
			BitBoard o = (BitBoard) other;
			
			for(int x = 0; x < o.getBSize(); x++) {
				for(int y = 0; y < o.getBSize(); y++) {
					if(!this.get(x, y).equals(o.get(x, y))) {
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
