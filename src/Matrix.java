public class Matrix {
	
	private Piece[][] array;
	
	public Matrix(Piece[][] data) {
		array = data;
	}
	
	public Matrix(Piece[] data, int sx) {
		array = new Piece[array.length][array[0].length];
		for(int i = 0; i < data.length; i++) {
			array[i%sx][i/sx] = data[i]; 
		}
	}
	
	/**
	 * Function to rotate the Matrix counterClockwise
	 * 
	**/
	public void rotateLeft() {
		int N = array.length;
		
		for(int x = 0; x < N / 2; x++) {
			for(int y = x; y < N-x-1; y++) {
				Piece tmp = array[x][y];
				
				// move values from right to top 
				array[x][y] = array[y][N-x-1];
				
			       
                // move values from bottom to right 
                array[y][N-1-x] = array[N-1-x][N-1-y]; 
       
                // move values from left to bottom 
                array[N-1-x][N-1-y] = array[N-1-y][x]; 
       
                // assign temporary value to left 
                array[N-1-y][x] = tmp; 
			}
		}
	}
	
	
	/**
	 * Rotate to right by rotating to left three times
	 * Potential for improvement by changing rotateLeft function to rotate Right
	 */
	public void rotateRight() {
		for(int i = 0; i < 3; i++) {
			this.rotateLeft();
		}
	}
	
	public void set(int x, int y, Piece data) throws Exception {
		if(array[x][y] != null) {
			throw new Exception("Piece already there");
		}
		array[x][y] = (Piece)data;
	}
	
	public Piece get(int x, int y) {
		return array[x][y];
	}
	
	public int size() {
		return array.length;
	}
	
	public String toString() {
		String ret = "";
		for(int i = 0; i < array.length; i++) {
			for(int j = 0; j < array[0].length; j++) {
				if(array[i][j] != null) {
					ret += " " + array[i][j] + " ";
				} else {
					ret += "   ";
				}
			}
			ret += "\n";
		}
		return ret;
	}
	
	public String getLine(int line) {
		String ret = "";
		for(int i = 0; i < array.length; i++) {
			//System.out.print(i + " " + line + ": " + array[i][line] + ", ");
			Piece c = array[i][line];
			if(c != null) {
				ret += " " + array[i][line].toString() + " ";
				//System.out.println("Char detected");
			} else {
				ret += "   ";
			}
		}
		return ret;
	}
	
	//-------------------------------------------------------------
	//Static Methods
	
	
	public static Matrix getEmptyMatrix(int size) {
		
		Piece[][] ret = new Piece[size][size];
		
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				ret[i][j] = null;
			}
		}
		
		return new Matrix(ret);
	}
}
