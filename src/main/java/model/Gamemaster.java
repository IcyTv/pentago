package model;

/**Der Gamemaster modelliert das Gesamtspiel. Mit seiner Erzeugung laeuft auch das Spiel ab.
 * Er hat das Board, die Queue, die Player.
   **/

import java.awt.Color;
import java.util.InputMismatchException;
import java.util.Scanner;
import static core.Constants.COLOR;

public class Gamemaster {

	private int amountOfPlayers;
	private Board board;
	private Queue queue;

	private boolean won;

	private boolean currentTurnPlace;

	public Gamemaster(int bSize, int pSize, int amountOfPlayers, boolean[] human) {

		if(human.length != amountOfPlayers) {
			throw new IllegalArgumentException();
		}
		
		this.amountOfPlayers = amountOfPlayers;
		currentTurnPlace = true;
		won = false;

		board = new Board(bSize, pSize, this);
		queue = new Queue(this);

		int[] colors = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int current = 1;

		for (int i = 0; i < amountOfPlayers; i++) {
			if(human[i]) {
				String name = "Player " + i;// enterName(s, (i + 1));
				COLOR color = new Colorpicker(colors[current - 1]).getColor();// whichColor(s, (i + 1));
	
				Human h = new Human(name, color, board, current++);
				queue.enqueue(h);
			} else {
				String name = "Computer " + i;
				COLOR color = new Colorpicker(colors[current - 1]).getColor();
				
				Computer c = new Computer(name, color, board, amountOfPlayers, current++);
				queue.enqueue(c);
			}
		}

	}

	public int getAmountOfPlayers() {
		return this.amountOfPlayers;
	}

	public void playRound(Turn turn) {
		queue.getCurrentP().playRound(turn);
	}

	public void addPlayer(Player player) {
		queue.enqueue(player);
	}

	public void placePiece(int x, int y) {
		if (currentTurnPlace) {
			try {
				Turn.valid(board, x, y);
				board.set(x, y, queue.getCurrentP().getPiece());
				currentTurnPlace = false;
				won = board.won();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	public void rotPanel(int x, int y, boolean dir) {
		if (!currentTurnPlace) {
			board.rotate(x, y, dir);
			currentTurnPlace = true;
			System.out.println(board.won());
			won = board.won();
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
		return won;
	}

	public int getBSize() {
		return board.getBSize();
	}

	public int getPSize() {
		return board.getPSize();
	}

	public Player get(int x, int y) {
		Piece tmp = board.get(x, y);
		if (tmp == null) {
			return null;
		} else {
			return tmp.getPlayer();
		}
	}

	// ********STATIC METHODS********//

	public static Turn getTurn(int pieceX, int pieceY, int rotX, int rotY, boolean dir, Board board) {
		return new Turn(pieceX, pieceY, rotX, rotY, dir, board);
	}

}
