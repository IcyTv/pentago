package strategy;

import java.util.BitSet;

public class BitBoard {

	private BitPanel[][] bitPanels;
	private int bSize;
	private int pSize;

	public BitBoard(int bSize, int pSize, int amountOfPlayers) {
		this.bSize = bSize;
		this.pSize = pSize;

		bitPanels = new BitPanel[bSize / pSize][bSize / pSize];
		for (int x = 0; x < bSize / pSize; x++) {
			for (int y = 0; y < bSize / pSize; y++) {
				bitPanels[x][y] = new BitPanel(pSize, amountOfPlayers);
			}
		}
		
		System.out.println("IsEmpty: " + bitPanels[0][0].emptyBitSet(0, 0));
	}

	public boolean won() {
		if (rowIsFilled()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean draw() {
		for (int x = 0; x < this.bSize; x++) {
			for (int y = 0; y < this.bSize; y++) {
				if (this.emptyBitSet(x, y)) {
					return false;
				}
			}
		}
		// System.out.println("Draw");
		return true;
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

		BitSet testPiece = this.get(x, y);
		if (this.emptyBitSet(x, y)) {
			return false;
		} else {
			for (int k = 0; k < 5; k++) {
				if (!testPiece.equals(this.get(x + k, y))) {
					return false;
				}
			}
			// System.out.println("Horizontal row");
			return true;
		}
	}

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
			// System.out.println("vertical row");
			return true;
		}
	}

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
			// System.out.println("Up left");
			return true;
		}
	}

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
			// System.out.println("Up right");
			return true;
		}
	}

	public boolean emptyBitSet(int x, int y) {
		return bitPanels[x / pSize][y / pSize].emptyBitSet(x % pSize, y % pSize);
	}

	public void set(int x, int y, int index) {
		bitPanels[x / pSize][y / pSize].set(x % pSize, y % pSize, index);
	}

	public void set(int x, int y, boolean b, int index) {
		bitPanels[x / pSize][y / pSize].set(x % pSize, y % pSize, b, index);
	}

	public BitSet get(int x, int y) {
		return bitPanels[x / pSize][y / pSize].get(x % pSize, y % pSize);
	}

	public void rotate(int x, int y, boolean dir) {
		bitPanels[x][y].rotate(dir);
	}

	public int getPSize() {
		return pSize;
	}

	public int getBSize() {
		return bSize;
	}
	
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
