
public class Board {
	
	Matrix[] matrices;
	int size;
	
	/**
	 * 
	 * @param s Board size
	 * @param ps Piece size MUST be factor of boardSize
	 */
	
	public Board(int s, int ps) {
		this.size = s;
		matrices = new Matrix[s*s];
		for(int i = 0; i < s * s; i++) {
			matrices[i] = Matrix.getEmptyMatrix(ps);
		}
	}
	
	public Board(int s) {
		this(s, 3);
	}
	
	public Board() {
		this(3,3);
	}
	
	
	public void set(int x, int y, Piece p) throws Exception {
		int psize = matrices[0].size();
		int mx = x / psize;
		int my = y / psize;
		
		//System.out.println(x - mx * psize + " " + (y - my * psize));
		
		matrices[my * size + mx].set(x - mx * psize, y - my * psize, p);
	}
	
	public boolean isDone() {
		return false;
	}
	
	public String toString() {
		String ret = "";
		
		String[] rows = new String[this.size * matrices[0].size()];
		
		for(int i = 0; i < this.size * matrices[0].size(); i++) {
			String tmp = "";
			for(int j= 0; j < this.size; j++) {
				tmp += matrices[i/matrices[0].size()*this.size + j].getLine(i%matrices[0].size());
				//System.out.println(i%matrices[0].size());
			}
			rows[i] = tmp;
		}
		
		for(String r: rows) {
			ret += r + "\n";
		}
		
		//System.out.println(matrices[0]);
		
		return ret;
	}

	public void turn(int x, int y, boolean dirb) {
		if(dirb) {
			matrices[y * this.size + x].rotateLeft();
		} else {
			matrices[y * this.size + x].rotateRight();
		}
		
	}
	
}
