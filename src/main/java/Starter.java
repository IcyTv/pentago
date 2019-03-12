import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Title: Starter Description: Diese Klasse dient zum Starten des gesamten
 * Programms.
 */

public class Starter {

	private static Scanner in;

	public static void main(String[] args) {
		in = new Scanner(System.in);
		int bSize = getBoardSize();
		int pSize = getPanelSize(bSize);
		new Gamemaster(bSize, pSize, howManyPlayers()).getWinner();
	}

	private static int getPanelSize(int bSize) {
		try {
			System.out.println("Wie gross soll ein Panel sein? (N mal N)");
			System.out.print("N = ");
			int pSize = in.nextInt();

			if (bSize % pSize != 0) {
				System.out.println("Die Panels koennen mit dieser Groesse nicht gleichmaessig verteilt werden");
				in.nextLine();
				return getPanelSize(bSize);
			} else {
				return pSize;
			}
		} catch (InputMismatchException e) {
			System.out.println("Es muss eine Zahl angegeben werden");
			in.nextLine();
			return getPanelSize(bSize);
		}
	}

	private static int getBoardSize() {
		try {
			System.out.println("Wie gross soll das Spielbrett sein? (N mal N)");
			System.out.print("N = ");
			int bSize = in.nextInt();

			return bSize;
		} catch (InputMismatchException e) {
			System.out.println("Es muss eine Zahl angegeben werden");
			in.nextLine();
			return getBoardSize();
		}
	}

	private static int howManyPlayers() {
		try {
			System.out.print("Wie viele Spieler sollen mitspielen? ");
			int amountOfPlayers = in.nextInt();

			return amountOfPlayers;
		} catch (InputMismatchException e) {
			System.out.println("Es muss eine Zahl angegeben werden");
			in.nextLine();
			return howManyPlayers();
		}
	}
}
