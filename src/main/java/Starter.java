import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Title:        Starter
 * Description:  Diese Klasse dient zum Starten des gesamten Programms.
 */

public class Starter 
{
    public static void main(String[] args) 
    {
    	Scanner s = new Scanner(System.in);
    	int bSize = getBoardSize(s);
    	int pSize = getPanelSize(s, bSize);
    	new Gamemaster(bSize,pSize,howManyPlayers(s));
    }
    
    private static int getPanelSize(Scanner s, int bSize)
    {
    	try
    	{
    		System.out.println("Wie groß soll ein Panel sein? (N mal N)");
    		System.out.println("N = ?");
    		int pSize = s.nextInt();
    		
    		if (bSize % pSize != 0)
    		{
    			System.out.println("Die Panels können mit dieser Größe nicht gleichmäßig verteilt werden");
    			s.nextLine();
    			return getPanelSize(s, bSize);
    		}
    		else
    		{
    			return pSize;
    		}
    	} catch (InputMismatchException e) {
    		System.out.println("Es muss eine Zahl angegeben werden");
    		s.nextLine();
    		return getPanelSize(s, bSize);
    	}
    }
    
    private static int getBoardSize(Scanner s)
    {
    	try
    	{
    		System.out.println("Wie groß soll das Spielbrett sein? (N mal N)");
    		System.out.println("N = ?");
    		int bSize = s.nextInt();
    		
    		return bSize;
    	} catch (InputMismatchException e) {
    		System.out.println("Es muss eine Zahl angegeben werden");
    		s.nextLine();
    		return getBoardSize(s);
    	}
    }
    
    private static int howManyPlayers(Scanner s)
    {
    	try
    	{
    		System.out.println("Wie viele Spieler sollen mitspielen?");
    		int amountOfPlayers = s.nextInt();
    		
    		return amountOfPlayers;
    	} catch (InputMismatchException e) {
    		System.out.println("Es muss eine Zahl angegeben werden");
    		s.nextLine();
    		return howManyPlayers(s);
    	}
    }
}