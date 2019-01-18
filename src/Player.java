
public class Player {

	private String name;
	private Piece piece;
	
	public Player(String name) {
		this.name = name;
		this.piece = new Piece();
	}
	
	public String getName() {
		return name;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
}
