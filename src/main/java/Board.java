 


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
    
    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < bSize; i++) {
            for (int n = 0; n < bSize; n++) {
                ret += get(i, n) + " ";
            }
            ret += "\n";
        }
        return ret;
    }
    
}
