package strategy;

import java.util.BitSet;
import java.util.Random;

import model.Board;
import model.Piece;
import model.Turn;

public class Strategy {

	/**
	 * Diese Klasse ist sozusagen das Gehirn des Computers. Jeder Zug, den der Computer macht, wurde durch den Aufruf der 
	 * <code> alphaBeta(int depth, boolean maximizingPlayer, int alpha, int beta) </code> Methode generiert. 
	 * Die Klasse erstellt einen Baum, der alle möglichen Spielzustände bis zur Tiefe <code> DEPTH </code> speichert. 
	 * Aus diesem Baum wird dann der beste Spielzug rausgesucht, die durch die Methode
	 * <code> staticEvaluation(boolean maximizing </code> bewertet wurden. Der Algorithmus, der Findung des besten
	 * Spielzugs zu Grunde liegt ist der sogenannte Min-Max-Algorithmus. Dieser ist zusätzlich modifiziert durch das
	 * sogenannte Alpha-Beta-Pruning. Somit ist der Algorithmus schneller. Die Methode <code> staticEvaluation(boolean maximizing) </code>
	 * bewertet die Spielzüge je nach dem wie viele Spielsteine verbunden sind. Je mehr verbunden sind desto besser, falls
	 * es sich um die eigenen Spielsteine handelt oder desto schlechter, falls es sich um gegnerische Spielsteine handelt.
	 * Es wird mit einem <code> BitBoard </code> gearbeitet. Das eigentliche Board wird durch Abfolgen von bits codiert, damit
	 * die Findung des besten Spielzugs schneller erfolgen kann. Je nach dem welches Bit true ist, resembliert diese Bitfolge
	 * den Spielstein eines Spielers.
	 * 
	 * Beispiel: false, true, false, false
	 * Dieser Spielstein wurde von Spieler 2 gelegt.
	 * 
	 * Beispiel: false, false, false, true
	 * Dieser Spielstein wurde von Spieler 4 gelegt.
	 * 
	 * Beispiel: false, false, false, false
	 * Dieses Spielfeld ist frei.
	 */
	
	private int             MAXDEPTH;                   //Maximale Tiefe bis die der Bot sucht (ownNumber mit einkalkuliert)
	public static final int SCOREMULTIPLIER = 10;       //Um wie viel der Score bei der static evaluation multipliziert wird bei aufeinanderfolgenden Pieces
	public static final int DEPTH = 1;                  //Maximale absolute Tiefe bis die der Bot sucht

	private Board    board;            //Kennt-Beziehung zum Board
	private Turn     bestTurn;         //Hier wird der beste gefundene Turn nach dem Aufruf von alphaBeta(int,boolean,int,int) gespeichert
	private int      amountOfPlayers;  //Speichert die Anzahl der Spieler im Spiel
	private int      ownNumber;        //ownNumber speichert die Platzierung in der Zugreihenfolge (Spieler 1 ownNumber = 1, Spieler 2 ownNumber = 2, ...)
	private BitSet   ownPiece;         //Speichert das eigene Piece, aber in der kodierten Form (Erleichtert vieles)
	private BitBoard currentState;     //Dieses Objekt repräsentiert das Board, aber in kodierter Form
	private Random   r;                //Random Objekt, damit zur Not zufällige Züge generiert werden können
	
	private int highestEvalYet;        //Speichert den größten gefundenen value eines Zugs, damit wirklich der BESTE Zug gefunden werden kann        
	
	/**
	 * Dies ist der Konstruktor der Klasse. Es werden lediglich die Attribute initialisiert.
	 * 
	 * @param board ist das Spielbrett
	 * @param amountOfPlayers ist die Anzahl der Spieler, die im Spiel spielen
	 * @param ownNumber ist die Platzierung im Spiel, also der wie vielte Spieler man ist (Spieler 1, Spieler 2, Spieler 3, ...)
	 */
	public Strategy(Board board, int amountOfPlayers, int ownNumber) {
		
		//Initialisierung der Attribute
		this.highestEvalYet = Integer.MIN_VALUE;
		this.amountOfPlayers = amountOfPlayers;
		this.ownNumber = ownNumber - 1;
		this.board = board;
		this.bestTurn = null;
		this.MAXDEPTH = DEPTH + ownNumber;
		this.r = new Random();
		
		if(board != null) {
			this.currentState = new BitBoard(board.getBSize(), board.getPSize(), amountOfPlayers);
		}

		this.ownPiece = new BitSet(amountOfPlayers);
		for (int i = 0; i < ownPiece.size(); i++) {
			ownPiece.set(i, (i + 1 == ownNumber));
		}
	}
	
	/**
	 * Diese Methode bezweckt, dass man den bestmöglichen Zug finden kann, wenn man
	 * <code> DEPTH </code> Züge weit vorrausschaut. <code> DEPTH </code> ist die 
	 * maximale Anzahl an Zügen, die der Computer vorrausschauen kann.
	 * 
	 * @return den bestmöglichen Zug, der gefunden wurde
	 */
	public Turn findBestTurn() {
		
		if(board != null) {		
			encode();         //Das Board wird kodiert und in <code> currentState </code> gespeichert
		}

		alphaBeta(ownNumber, true, Integer.MIN_VALUE, Integer.MAX_VALUE);    //Jetzt wird versucht, den bestmöglichen Zug zu finden
		
		if(board != null) {
			currentState = new BitBoard(board.getBSize(), board.getPSize(), amountOfPlayers);          //Das kodierte Board wird zurückgesetzt
		}
		highestEvalYet = Integer.MIN_VALUE;          //Der value vom besten Zug wird zurückgesetzt
		
		return bestTurn;     //Der beste Zug wird zurückgegeben
	}
	
	/**
	 * Ein Zustand des Boards wird mit "herkömmlichen" Methoden bewertet.
	 * Es wird geschaut wie viele Steine in einer Reihe sind
	 * 
	 * @param maximizing ist eine boolean, die aussagt, ob man bewerten soll wie der maximierende oder der minimierende Spieler
	 * @return eine Zahl, die die Wertung des Spielstands darstellen soll
	 */
	private int staticEvaluation(boolean maximizing) {
		int boardSize = currentState.getBSize();
		int endEval = 0;

		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				endEval += horizontalBitAlignment(x, y);
				endEval += verticalBitAlignment(x, y);
				endEval += upLeftBitAlignment(x, y);
				endEval += upRightBitAlignment(x, y);
			}
		}
		return endEval;
	}
	
	/**
	 * Es wird geschaut, wie viele Steine in einer horizontalen Reihe angeordnet sind, von einem bestimmten Feld aus.
	 * Horizontal bedeutet hier: Alle Steine, die rechts vom Feld sind.
	 * 
	 * @param x Koordinate des Spielfelds
	 * @param y Koordinate des Spielfelds
	 * @return Wertung
	 */
	private int horizontalBitAlignment(int x, int y) {
		int score = 1;
		BitSet pieceOfReference = currentState.get(x, y);

		if (!currentState.emptyBitSet(x, y)) {
			for (int i = 0; i < 5 && x + i < currentState.getBSize(); i++) {
				if (currentState.get(x + i, y) == pieceOfReference) {
					score *= SCOREMULTIPLIER;
				}
			}
		} else {
			return 0;
		}

		if (!pieceOfReference.equals(ownPiece)) {
			score *= -1;
		}
		return score;
	}
	
	/**
	 * Es wird geschaut, wie viele Steine in einer vertikalen Reihe angeordnet sind, von einem bestimmten Feld aus.
	 * Vertikal bedeutet hier: Alle Steine, die oben vom Feld sind.
	 * 
	 * @param x Koordinate des Spielfelds
	 * @param y Koordinate des Spielfelds
	 * @return Wertung
	 */
	private int verticalBitAlignment(int x, int y) {
		int score = 1;
		BitSet pieceOfReference = currentState.get(x, y);

		if (!currentState.emptyBitSet(x, y)) {
			for (int i = 0; i < 5 && y + i < currentState.getBSize(); i++) {
				if (currentState.get(x, y + i) == pieceOfReference) {
					score *= SCOREMULTIPLIER;
				}
			}
		} else {
			return 0;
		}

		if (!pieceOfReference.equals(ownPiece)) {
			score *= -1;
		}
		return score;
	}
	
	/**
	 * Es wird geschaut, wie viele Steine in einer diagonalen Reihe angeordnet sind, von einem bestimmten Feld aus.
	 * Diagonal bedeutet hier: Alle Steine, die oben-links vom Feld sind.
	 * 
	 * @param x Koordinate des Spielfelds
	 * @param y Koordinate des Spielfelds
	 * @return Wertung
	 */
	private int upLeftBitAlignment(int x, int y) {
		int score = 1;
		BitSet pieceOfReference = currentState.get(x, y);

		if (!currentState.emptyBitSet(x, y)) {
			for (int i = 0; i < 5 && x - i > -1 && y + i < currentState.getBSize(); i++) {
				if (currentState.get(x - i, y + i) == pieceOfReference) {
					score *= SCOREMULTIPLIER;
				}
			}
		} else {
			return 0;
		}

		if (!pieceOfReference.equals(ownPiece)) {
			score *= -1;
		}
		return score;
	}

	/**
	 * Es wird geschaut, wie viele Steine in einer diagonalen Reihe angeordnet sind, von einem bestimmten Feld aus.
	 * Diagonal bedeutet hier: Alle Steine, die oben-rechts vom Feld sind.
	 * 
	 * @param x Koordinate des Spielfelds
	 * @param y Koordinate des Spielfelds
	 * @return Wertung
	 */
	private int upRightBitAlignment(int x, int y) {
		int score = 1;
		BitSet pieceOfReference = currentState.get(x, y);

		if (!currentState.emptyBitSet(x, y)) {
			for (int i = 0; i < 5 && x + i < currentState.getBSize() && y + i < currentState.getBSize(); i++) {
				if (currentState.get(x + i, y + i) == pieceOfReference) {
					score *= SCOREMULTIPLIER;
				}
			}
		} else {
			return 0;
		}

		if (!pieceOfReference.equals(ownPiece)) {
			score *= -1;
		}
		return score;
	}

	/**
	 * Das Board wird kodiert in eine Reihe von Bits. Die Regeln der Kodierung stehen in der Klassenbeschreibung geschrieben.
	 */
	private void encode() {

		for (int x = 0; x < currentState.getBSize(); x++) // BitSet Array wird befÃ¼llt
		{
			for (int y = 0; y < currentState.getBSize(); y++) {
				Piece realPiece = board.get(x, y); // Chip im Board im Platz (Es wird durch das Board durchiteriert)
				//boolean codedPiece[] = new boolean[amountOfPlayers]; // Array in dem der Wert der Stelle codiert wird

				if (realPiece != null) // Gibt es Ã¼berhaupt einen Chip im Board an dieser spezifischen Stelle?
				{
					currentState.set(x, y, true, realPiece.getPlayer().getNumber() - 1);
				}
			}
		}
	}
	
	/**
	 * Ein zufälliger Zug wird erstellt. Fungiert als Tiebreaker sozusagen.
	 */
	private void generateRandomTurn()
	{
		try
		{
			bestTurn = new Turn(r.nextInt(board.getBSize()), r.nextInt(board.getBSize()), r.nextInt(board.getPSize()), r.nextInt(board.getPSize()), r.nextBoolean(), board);
		}
		catch (IllegalStateException e)  //Error wird gethrowed, wenn der Zug, der oben instanziiert wird invalide ist
		{
			generateRandomTurn();  //Dann wird eben nach einem neuen Zug gesucht...
		}
	}
	
	/**
	 * Der Kern dieser Klasse. Eine Elaboration der Methode ist in der Klassenbeschreibung zu finden.
	 * 
	 * @param depth ist die Tiefe, in die sich der Bot grad befindet
	 * @param maximizingPlayer ist eine Boolean, die aussagt, ob aus der Sicht des maximierenden oder minimierenden Spieler bewertet werden soll.
	 * @param alpha unterer Grenzwert der höchstmöglichen Wertung
	 * @param beta oberer Grenzwert der höchstmöglichen Wertung
	 * @return eine Integer, die im Kontext irrelevant ist
	 */
	public int alphaBeta(int depth, boolean maximizingPlayer, int alpha, int beta) {
		if (depth >= MAXDEPTH) // Abbruch, wenn der Bot zu tief im Baum ist
		{
			return staticEvaluation((depth - ownNumber) % amountOfPlayers == 0); // Der Bot bewertet die momentane Lage
		}
		else if (currentState.won()) // Man soll nicht weiter probieren, wenn das Board schon gewonnen ist...
		{
			if (maximizingPlayer) {
				if (depth == ownNumber)
				{
					generateRandomTurn();  //Noch wurde kein Turn festgelegt, also würde sonst null zurückgegeben werden
				}
				return -100000; // Gegner hat gewonnen, weil das Board gewonnen war, als der Bot am Zug
												// war
			} else {
				if (depth == ownNumber)
				{
					generateRandomTurn();   //Noch wurde kein Turn festgelegt, also würde sonst null zurückgegeben werden
				}
				return 100000; // Bot hat gewonnen, weil das Board gewonnen war, als der Gegner am Zug war
			}
		} else if (currentState.draw()) {
			return 0; // Unentschieden hat die Wertung 0
		}
		int amountOfPanels = currentState.getBSize() / currentState.getPSize();       //Dient der Überschaulichkeit
		int boardSize = currentState.getBSize();       //Dient der Überschaulichkeit
		int maxEval, minEval, eval;   //Die Hauptvariablen der Methode
		
		if (maximizingPlayer) // Je nach dem ob der maximierende Spieler dran ist, werden Variablen anders
								// gespeichert...
		{
			maxEval = Integer.MIN_VALUE; // Man geht vom schlimmsten Fall aus
			for (int x = 0; x < boardSize; x++) {      //Man iteriert durch alle Spalten
				for (int y = 0; y < boardSize; y++) {       //Man iteriert durch alle Zeilen
					boolean empty = currentState.emptyBitSet(x, y);        //Man schaut, ob das Spielfeld belegt ist oder nicht und speichert dann das als boolean ab
					for (int rotX = 0; rotX < amountOfPanels; rotX++) {          //Panelspalten werden durchiteriert
						for (int rotY = 0; rotY < amountOfPanels; rotY++) {          //Panelzeilen werden durchiteriert
							for (int rot = 0; rot < 2; rot++) {        //Entweder man rotiert nach links oder rechts
								boolean rotDir = rot > 0;        //0 = false, 1 = true

								if (empty) {      //Wenn das Spielfeld belegt ist, kann man ja sonst gar nicht die Position bewerten
									currentState.set(x, y, true, ownNumber);          //Stein wird im kodierten Board gesetzt
									currentState.rotate(rotX, rotY, rotDir);       //Kodiertes Board wird gedreht
									
									eval = alphaBeta(depth + 1, !maximizingPlayer, alpha, beta);     //Rekursionsaufruf
									
									if (depth == ownNumber && eval > highestEvalYet) {         //Wenn man in der ersten Inkarnationstiefe ist und dieser Zug der beste ist
										Turn lastBestTurn = bestTurn;
										try
										{
											this.bestTurn = new Turn(x, y, rotX, rotY, rotDir, board);   //Dann ist das der beste Zug, der bis jetzt gefunden wurde
											highestEvalYet = eval;      //Das ist dann auch der neue beste score, der gefunden wurde
										}
										catch (IllegalArgumentException e)
										{
											e.printStackTrace();
											bestTurn = lastBestTurn;
										}
									}

									currentState.rotate(rotX, rotY, !rotDir);      //Kodiertes Board wird zurückgedreht
									currentState.set(x, y, false, ownNumber);      //Stein wird weggenommen
									maxEval = Math.max(eval, maxEval);      //Ist der jetzt evaluierte Zug der bestgefundene Zug?
									alpha = Math.max(alpha, maxEval);       //Untergrenze wird neu justiert
									if (beta <= alpha) {       //Abbruchbedingung: Zug ist niemals besser als der Zug im vorherigen Baum
										break;
									}
								}
							}
							if (beta <= alpha) {        //Abbruchbedingung: Zug ist niemals besser als der Zug im vorherigen Baum
								break;
							}
						}
						if (beta <= alpha) {       //Abbruchbedingung: Zug ist niemals besser als der Zug im vorherigen Baum
							break;
						}
					}
					if (beta <= alpha) {       //Abbruchbedingung: Zug ist niemals besser als der Zug im vorherigen Baum
						break;
					}
				}
				if (beta <= alpha) {       //Abbruchbedingung: Zug ist niemals besser als der Zug im vorherigen Baum
					break;
				}
			}
			return maxEval;        //Der beste score wird zurückgegeben
		} else {
			minEval = Integer.MAX_VALUE; // Man geht vom schlimmsten Fall aus
			for (int x = 0; x < boardSize; x++) {      //Man iteriert durch alle Spalten
				for (int y = 0; y < boardSize; y++) {       //Man iteriert durch alle Zeilen
					boolean empty = currentState.emptyBitSet(x, y);        //Man schaut, ob das Spielfeld belegt ist oder nicht und speichert dann das als boolean ab
					int num = depth % amountOfPlayers;      //Der wie vielte Spieler ist denn dran?
					
					for (int rotX = 0; rotX < amountOfPanels; rotX++) {          //Panelspalten werden durchiteriert
						for (int rotY = 0; rotY < amountOfPanels; rotY++) {          //Panelzeilen werden durchiteriert
							for (int rot = 0; rot < 2; rot++) {        //Entweder man rotiert nach links oder rechts
								boolean rotDir = rot > 0;        //0 = false, 1 = true

								if (empty) {      //Wenn das Spielfeld belegt ist, kann man ja sonst gar nicht die Position bewerten
									currentState.set(x, y, true, num);          //Stein vom Spieler wird im kodierten Board gesetzt
									currentState.rotate(rotX, rotY, rotDir);       //Kodiertes Board wird gedreht

									boolean nextPlayerMaximizingPlayer = (((depth + 1 - ownNumber)
											% amountOfPlayers) == 0);  //Ist der nächste Spieler wieder der Computer?
									eval = alphaBeta(depth + 1, nextPlayerMaximizingPlayer, alpha, beta);     //Rekursionsaufruf

									currentState.rotate(rotX, rotY, !rotDir);      //Kodiertes Board wird zurückgedreht
									currentState.set(x, y, false, num);      //Stein wird weggenommen
									minEval = Math.min(eval, minEval);      //Ist der jetzt evaluierte Zug der schlechteste Zug für den maximierenden Spieler?
									beta = Math.min(beta, minEval);       //Obergrenze wird neu justiert
									if (beta <= alpha) {       //Abbruchbedingung: Zug ist niemals besser als der Zug im vorherigen Baum
										break;
									}
								}
							}
							if (beta <= alpha) {       //Abbruchbedingung: Zug ist niemals besser als der Zug im vorherigen Baum
								break;
							}
						}
						if (beta <= alpha) {       //Abbruchbedingung: Zug ist niemals besser als der Zug im vorherigen Baum
							break;
						}
					}
					if (beta <= alpha) {       //Abbruchbedingung: Zug ist niemals besser als der Zug im vorherigen Baum
						break;
					}
				}
				if (beta <= alpha) {       //Abbruchbedingung: Zug ist niemals besser als der Zug im vorherigen Baum
					break;
				}
			}
			return minEval;        //Der für den maximierenden Spieler schlechtesten score wird zurückgegeben
		}
	}
	
	/**
	 * Set-Methode des Attributs <code> currentState </code>
	 * 
	 * @param currentState ist das BitBoard, das das alte BitBoard ersetzen soll
	 */
	public void setCurrentState(BitBoard currentState) {
		this.currentState = currentState;
	}
}
