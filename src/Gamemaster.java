import java.util.Arrays;
import java.util.Scanner;

public class Gamemaster {
	
	private Board board;
	
	private Player[] players;
	private int current;
	
	public Gamemaster() {
		
		players = new Player[2];
		
		Scanner in = new Scanner(System.in);
		
		for(int i = 0; i < players.length; i++) {
			System.out.print("Name player " + (i+1) + ": ");
			players[i] = new Player((String)in.nextLine());
		}
		
		current = 0;
		
		board = new Board();
		
		while(!board.isDone()) {
			System.out.println("It is " + players[current].getName() + "'s turn");
			System.out.println("\nWhere would you like to place your piece?[x y]");
			int[] place = Arrays.stream(in.nextLine().split(" ")).map(String::trim).mapToInt(Integer::parseInt).toArray();
			System.out.println(Arrays.toString(place));
			try {
				board.set(place[0] + 1, place[1] + 1, players[current].getPiece());
			} catch (Exception e) {
				System.out.println("There already is a piece there!");
				continue;
			}
			current++;
			if(current >= players.length) {
				current = 0;
			}
			
			int[] piece;
			boolean dirb;
			
			while(true) {
				System.out.println("Which Board piece would you like to turn?[x y] ");
				
				piece = Arrays.stream(in.nextLine().split(" ")).map(String::trim).mapToInt(Integer::parseInt).toArray();
				System.out.println("And which direction?[l|r]");
				String dir = (String)in.nextLine();
				System.out.println(dir);
				if(dir.toLowerCase().equals("l")) {
					dirb = false;
					break;
				} else if(dir.toLowerCase().equals("r")) {
					dirb = true;
					break;
				} else {
					System.out.println("I did not understand that.");
				}
			}
			board.turn(piece[0] + 1, piece[1] + 1, dirb);
			
			System.out.println(board);
		}
		
		in.close();
		
	}
	
	public static void main(String[] args) {
		new Gamemaster();
	}

}
