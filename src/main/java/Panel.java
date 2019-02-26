 


public class Panel {

    private Piece[][] pieces;
    private int size;
    //Piece[][] tmp;
        
    
    public Panel(int pSize) {
        this.size = 3;
        pieces = new Piece[size][size];
    }
    
    public int getSize() {
        return size;
    }
    
    public void set(int x, int y, Piece piece) {
        pieces[x][y] = piece;
    }
    
    public Piece get(int x, int y) {
        return pieces[x][y];
    }
    
    public void rotate(boolean dir) {
        Piece[][] tmp;
        tmp = new Piece[size][size];
        if (dir == true) {
            for (int x = 0; x<size; x++) {
                for (int y = 0; y<size; y++) {
                    tmp[y][size-x-1] = pieces[x][y];
                }
            }
        }
        else {
            for (int x = 0; x<size; x++) {
                for (int y = 0; y<size; y++) {
                    tmp[size-y-1][x] = pieces[x][y];
                }
            }
        }
        
        for (int x = 0; x<size; x++) {
                for (int y = 0; y<size; y++) {
                    pieces[x][y] = tmp[x][y];
                }
            }
    }
    
    public void displayLine(int n) {
        for (int x = 0; x<size; x++) {
            if (pieces[x][n] != null) {
                System.out.print("1 ");
            }
            else {
                System.out.print("0 ");
            }
        }
    }
    
}
