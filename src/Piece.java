
public class Piece {

	public static String[] PIECE_TYPES = new String[] {"*", "+", "~", "#"};
	private static int counter = 0;
	
	private String symbol;
	
	public Piece() {
		symbol = getType();
		
	}
	
	public String toString() {
		return symbol;
	}
	
	
	public static String getType() {
		return PIECE_TYPES[counter++];
	}
}
