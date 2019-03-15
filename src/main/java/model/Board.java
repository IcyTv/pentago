package model;

/**
 * Die Klasse Board modelliert das Spielbrett. Es hat mehrere, in einem 2D-Array
 * angeordnete Panels und kennt Gamemaster und Player. Das Board kann ein Piece
 * an einen bestimmten Platz setzen. Dazu ruft es die set()-Methode des
 * entsprechenden Panels auf. Das Board kann ausserdem feststellen, ob es eine
 * Fuenferreihe gibt, und speichert dann den Spieler, dem die Pieces dieser
 * Fuenferreihe gehoeren.
 **/

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

		panels = new Panel[bSize / pSize][bSize / pSize];
		for (int x = 0; x < bSize / pSize; x++) {
			for (int y = 0; y < bSize / pSize; y++) {
				panels[x][y] = new Panel(pSize);
			}
		}
	}

	public boolean won(Player currentPlayer) {
		if (rowIsFilled()) {
			winner = currentPlayer;
			return true;
		} else {
			return false;
		}
	}

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

	private boolean horizontalRow(int x, int y) {
		if (x > this.bSize - 5)
			return false;

		Piece testPiece = this.get(x, y);
		if (testPiece == null) {
			return false;
		} else {
			for (int k = 0; k < 5; k++) {
				if (!testPiece.equals(this.get(x + k, y))) {
					return false;
				}
			}
			System.out.println("Horizontal row");
			return true;
		}
	}

	private boolean verticalRow(int x, int y) {
		if (y > this.bSize - 5)
			return false;

		Piece testPiece = this.get(x, y);
		if (testPiece == null) {
			return false;
		} else {
			for (int k = 0; k < 5; k++) {
				if (!testPiece.equals(this.get(x, y + k))) {
					return false;
				}
			}
			System.out.println("vertical row");
			return true;
		}
	}

	private boolean upLeftRow(int x, int y) {
		if (x < 4 || y > this.bSize - 5)
			return false;

		Piece testPiece = this.get(x, y);
		if (testPiece == null) {
			return false;
		} else {
			for (int k = 0; k < 5; k++) {
				if (!testPiece.equals(this.get(x - k, y + k))) {
					return false;
				}
			}
			System.out.println("Up left");
			return true;
		}
	}

	private boolean upRightRow(int x, int y) {
		if (x > this.bSize - 5 || y > this.bSize - 5)
			return false;

		Piece testPiece = this.get(x, y);
		if (testPiece == null) {
			return false;
		} else {
			for (int k = 0; k < 5; k++) {
				if (!testPiece.equals(this.get(x + k, y + k))) {
					return false;
				}
			}
			System.out.println("Up right");
			return true;
		}
	}

	public void set(int x, int y, Piece piece) {
		panels[x / pSize][y / pSize].set(x % pSize, y % pSize, piece);
	}

	public Piece get(int x, int y) {
		return panels[x / pSize][y / pSize].get(x % pSize, y % pSize);
	}

	public void rotate(int x, int y, boolean dir) {
		panels[x][y].rotate(dir);
	}

	@Override
	public String toString() {
		String ret = "";
		int markerY = 0;
		int markerX = 0;
		for (int x = 0; x < bSize; x++) {
			for (int n = 0; n < bSize; n++) {
				markerX++;
				ret += getPlayerNumber(n, x) + " ";
				if (markerX == pSize) {
					ret += "| ";
					markerX = 0;
				}
			}
			ret += "\n";
			markerY++;
			if (markerY == pSize) {
				for (int k = 0; k < bSize; k++) {
					ret += "___";
				}
				markerY = 0;
				ret += "\n";
			}
		}
		return ret;
	}

	private int getPlayerNumber(int x, int y) {
		Piece piece = this.get(x, y);
		if (piece == null)
			return 0;

		Player playingP = piece.getPlayer();
		Node node = gm.getQueue().getFirstNode();

		for (int k = 0; k < gm.getAmountOfPlayers(); k++) {
			if (playingP == node.getPlayer()) {
				return k + 1;
			} else {
				node = node.getNext();
			}
		}

		return 0;
	}

	public Player getWinner() {
		return winner;
	}

	public int getPSize() {
		return pSize;
	}

	public int getBSize() {
		return bSize;
	}

}
