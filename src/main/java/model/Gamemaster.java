package model;

/**Der Gamemaster modelliert das Gesamtspiel. Mit seiner Erzeugung laeuft auch das Spiel ab.
 * Er hat das Board, die Queue, die Player.
   **/

import java.awt.Color;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Gamemaster {

	private int amountOfPlayers;
	private Board board;
	private Queue queue;

	private boolean currentTurnPlace;

	public Gamemaster(int bSize, int pSize, int amountOfPlayers) {
		this.amountOfPlayers = amountOfPlayers;

		Scanner s = new Scanner(System.in);
		board = new Board(bSize, pSize, this);
		queue = new Queue(this);
		loadPlayers(s);

		for (int i = 0; i < amountOfPlayers - 1; i++) {
			queue.nextPlayer();
		}

		while (!board.won(queue.getCurrentP())) {
			queue.nextPlayer();
			if (queue.getCurrentP().isHuman()) {
				printBoard();
				Human human = (Human) queue.getCurrentP();
				human.playRound(getTurn(this.getPieceX(s), this.getPieceY(s), this.getRotateX(s), this.getRotateY(s),
						this.getRotateDirection(s), board));
			} else {
				Computer computer = (Computer) queue.getCurrentP();
				computer.playRound();
			}
		}
		s.close();
	}

	public Gamemaster(int bSize, int pSize, int amountOfPlayers, boolean text) {
		if (text) {
			throw new RuntimeException("Please use the other constructor for text output");
		}

		this.amountOfPlayers = amountOfPlayers;
		currentTurnPlace = true;

		board = new Board(bSize, pSize, this);
		queue = new Queue(this);

		int[] colors = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int current = 0;

		for (int i = 0; i < amountOfPlayers; i++) {
			String name = "Player " + i;// enterName(s, (i + 1));
			boolean mensch = true;// isHuman(s, (i + 1));
			Color color = new Colorpicker(colors[current++]).getColor();// whichColor(s, (i + 1));

			if (mensch) {
				Human h = new Human(name, color, board);
				queue.enqueue(h);
			} else {
				Computer c = new Computer(name, color, board);
				queue.enqueue(c);
			}
		}

	}

	private int getPieceX(Scanner s) {
		try {
			System.out.println(queue.getCurrentP().getName() + ": In welcher Zeile soll der zu setzende Stein liegen?");
			int x = s.nextInt() - 1;

			if (x < 0 || x > 8) {
				String ret = "Es werden nur die Zahlen ";
				for (int i = 1; i < board.getBSize() + 1; i++) {
					ret += i + ", ";
				}
				ret = ret.substring(0, ret.length() - 2);
				ret += " akzeptiert";
				s.nextLine();
				System.out.println(ret);
				return getPieceX(s);
			} else {
				return x;
			}
		} catch (InputMismatchException e) {

			String ret = "Es werden nur die Zahlen ";
			for (int i = 1; i < board.getBSize() + 1; i++) {
				ret += i + ", ";
			}
			ret = ret.substring(0, ret.length() - 2);
			ret += " akzeptiert";

			System.out.println(ret);
			s.nextLine();
			return getPieceX(s);
		}
	}

	private int getPieceY(Scanner s) {
		try {
			System.out
					.println(queue.getCurrentP().getName() + ": In welcher Spalte soll der zu setzende Stein liegen?");
			int y = s.nextInt() - 1;

			if (y < 0 || y > 8) {
				System.out.println("Es werden nur die Zahlen '1, 2, 3, 4, 5, 6, 7, 8, 9' akzeptiert");
				s.nextLine();
				return getPieceY(s);
			} else {
				return y;
			}
		} catch (InputMismatchException e) {
			System.out.println("Es werden nur die Zahlen '1, 2, 3, 4, 5, 6, 7, 8, 9' akzeptiert");
			s.nextLine();
			return getPieceY(s);
		}
	}

	private int getRotateX(Scanner s) {
		try {
			System.out.println(queue.getCurrentP().getName() + ": In welcher Spalte liegt das zu drehende Panel?");
			int x = s.nextInt() - 1;

			if (x < 0 || x > 2) {
				System.out.println("Es werden nur die Zahlen '1, 2, 3' akzeptiert");
				s.nextLine();
				return getRotateX(s);
			} else {
				return x;
			}
		} catch (InputMismatchException e) {
			System.out.println("Es werden nur die Zahlen '1, 2, 3' akzeptiert");
			s.nextLine();
			return getRotateX(s);
		}
	}

	private int getRotateY(Scanner s) {
		try {
			System.out.println(queue.getCurrentP().getName() + ": In welcher Zeile liegt das zu drehende Panel?");
			int y = s.nextInt() - 1;

			if (y < 0 || y > board.getPSize()) {
				String ret = "Es werden nur die Zahlen ";
				for (int i = 1; i < board.getPSize(); i++) {
					ret += i + ", ";
				}
				ret = ret.substring(0, ret.length() - 2);
				ret += " akzeptiert";
				System.out.println(ret);
				s.nextLine();
				return getRotateY(s);
			} else {
				return y;
			}
		} catch (InputMismatchException e) {
			String ret = "Es werden nur die Zahlen ";
			for (int i = 1; i < board.getPSize(); i++) {
				ret += i + ", ";
			}
			ret = ret.substring(0, ret.length() - 2);
			ret += " akzeptiert";
			s.nextLine();
			return getRotateY(s);
		}
	}

	private boolean getRotateDirection(Scanner s) {
		try {
			System.out.println(queue.getCurrentP().getName()
					+ ": In welche Richtung soll das Brett gedreht werden? (Rechts = true, Links = false)");
			boolean dir = s.nextBoolean();

			return dir;
		} catch (InputMismatchException e) {
			System.out.println("Es werden nur die Werte 'true' oder 'false' akzeptiert");
			s.nextLine();
			return getRotateDirection(s);
		}
	}

	private void printBoard() {
		System.out.println(this);
		System.out.println("");
	}

	public int getAmountOfPlayers() {
		return this.amountOfPlayers;
	}

	public void playRound(Turn turn) {
		queue.getCurrentP().playRound(turn);
	}

	private void loadPlayers(Scanner s) {
		int[] colors = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int current = 0;

		for (int i = 0; i < amountOfPlayers; i++) {
			String name = enterName(s, (i + 1));
			boolean mensch = true;// isHuman(s, (i + 1));
			Color color = new Colorpicker(colors[current++]).getColor();// whichColor(s, (i + 1));

			if (mensch) {
				Human h = new Human(name, color, board);
				queue.enqueue(h);
			} else {
				Computer c = new Computer(name, color, board);
				queue.enqueue(c);
			}
		}
	}

	private String enterName(Scanner s, int number) {
		System.out.println("Spieler " + number + ": Namen eingeben: ");
		String name = s.next();

		return name;
	}

	private boolean isHuman(Scanner s, int number) {
		try {
			System.out.println("Ist der Spieler ein Mensch? (true oder false)");
			boolean mensch = s.nextBoolean();

			return mensch;
		} catch (InputMismatchException e) {
			s.nextLine();
			System.out.println("Es werden nur die Strings 'true' oder 'false' akzeptiert.");
			return isHuman(s, number);
		}
	}

	private Color whichColor(Scanner s, int number) {
		try {
			System.out.println("Zahl zwischen 0 (inklusiv) und 10 (exklusiv) eingeben: ");
			int n = s.nextInt();
			if (n < 0 || n > 9) {
				s.nextLine();
				System.out.println("Es werden nur die Zahlen '0, 1, 2, 3, 4, 5, 6, 7, 8, 9' akzeptiert.");
				return whichColor(s, number);
			} else {
				Colorpicker picker = new Colorpicker(n);
				Color color = picker.getColor();

				return color;
			}
		} catch (InputMismatchException e) {
			s.nextLine();
			System.out.println("Es werden nur die Zahlen '0, 1, 2, 3, 4, 5, 6, 7, 8, 9' akzeptiert.");
			return whichColor(s, number);
		}
	}

	public void addPlayer(Player player) {
		queue.enqueue(player);
	}

	public Player generatePlayer(String name, Color color, boolean computer) {
		if (computer) {
			return new Computer(name, color, board);
		} else {
			return new Human(name, color, board);
		}
	}

	public void placePiece(int x, int y) {
		if (currentTurnPlace) {
			board.set(x, y, queue.getCurrentP().getPiece());
			currentTurnPlace = false;
		} else {
			System.err.println("NOT A CLKICK TURN!!");
		}
	}

	public void rotPanel(int x, int y, boolean dir) {
		if (!currentTurnPlace) {
			board.rotate(x, y, dir);
			currentTurnPlace = true;
			queue.nextPlayer();
		}
	}

	public Board getBoard() {
		return board;
	}

	public Player getWinner() {
		return board.getWinner();
	}

	public Queue getQueue() {
		return this.queue;
	}

	public Player getCurrentPlayer() {
		return queue.getCurrentP();
	}

	public boolean currentTurnIsPlaceTurn() {
		return currentTurnPlace;
	}

	@Override
	public String toString() {
		return board.toString();
	}

	public boolean won() {
		return board.won(queue.getCurrentP());
	}

	// ********STATIC METHODS********//

	public static Turn getTurn(int pieceX, int pieceY, int rotX, int rotY, boolean dir, Board board) {
		return new Turn(pieceX, pieceY, rotX, rotY, dir, board);
	}
}
