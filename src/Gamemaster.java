 import java.awt.Color;
 import java.util.Scanner;

public class Gamemaster {
    
    //private Player[] players;
    private Board board;
    Scanner s;
    Queue queue;
    
    public Gamemaster(int bSize, int pSize) {
        board = new Board(bSize, pSize);
        queue = new Queue();
        //queue.add(new Human("Hans", Color.RED, board));
        //players = new Player[nPlayers];
        //s = new Scanner(System.in);
        
        /*for (int i = 0; i<players.length; i++) {
            System.out.println("Namen eingeben: ");
            String name = s.next();
            System.out.println("Ist der Spieler ein Mensch? (true oder false)");
            boolean mensch = Boolean.parseBoolean(s.next());
            System.out.println("Zahl zwischen 0 (inklusiv) und 10 (exklusiv) eingeben: ");
            int n = Integer.parseInt(s.next());
            Colorpicker picker = new Colorpicker(n);
            Color color = picker.getColor();
            
            if (mensch) players[i] = new Human(name, color, board);
            else players[i] = new Computer(name, color, board);
            
        }*/
        
        
    }
    
    public void lasseRundeSpielen(int pX, int pY, int rX, int rY, boolean dir) {
        Turn testturn = new Turn(pX,pY,rX,rY, dir);
        queue.next().playRound(testturn);
    }

    public void addPlayer(Player player){
        queue.add(player);
    }

    public Player generatePlayer(String name, Color color, boolean computer){
        if(computer){
            return new Computer(name, color, board);
        } else {
            return new Human(name, color, board);
        }
    }



    //********STATIC METHODS********//

    public static Turn getTurn(int pieceX, int pieceY, int rotX, int rotY, boolean dir) {
        return new Turn(pieceX, pieceY, rotX, rotY, dir);
    }
}
