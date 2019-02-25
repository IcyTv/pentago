 


public class Board {

    private Panel[][] panels;
    private boolean done;
    private Player winner;
    private int bSize;
    
    public Board(int bSize, int pSize) {
        if (bSize%pSize!=0) System.out.println("bSize muss durch pSize teilbar sein!");
        this.bSize = bSize;
        done = false;
        winner = null;
        
        panels = new Panel[bSize/pSize][bSize/pSize];
        for (int x = 0; x< bSize/pSize; x++) {
            for (int y = 0; y< bSize/pSize; y++) {
                panels[x][y] = new Panel(pSize);
            }
        }
        displayBoard();
    }
    
    public void set(int x, int y, Piece piece) {
       int size = panels[0][0].getSize();
       panels[x/size][y/size].set(x%size, y%size, piece);
    }
    
    public Piece get(int x, int y) {
       int size = panels[0][0].getSize();
       return panels[x/size][y/size].get(x%size, y%size);
    }
    
    public void rotate(int x, int y, boolean dir) {
        panels[x][y].rotate(dir);
    }
    
    public void displayBoard() {
        System.out.println("");
        for (int y = 0; y<bSize; y++) {
            for (int x = 0; x<panels.length; x++) {
                panels[x][y/panels.length].displayLine(y%panels.length);
            }
            System.out.println("");
        }
        System.out.println("");
        
    }
    
}
