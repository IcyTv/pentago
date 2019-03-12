/** Die Klasse Panel modelliert eine drehbare Untereinheit des Bretts.
  * Sie kennt die Klasse Piece
   **/


public class Panel {

    private Piece[][] pieces;
    private int size;
    //Piece[][] tmp;
        
    /** Konstruktor
      * Erzeugt ein Panel ohne besetzte Felder
      * @param n         Seitenlaenge des Panels
       **/
    public Panel(int pSize) {
        this.size = pSize;
        pieces = new Piece[size][size];
    }

    public int getSize() {
        return size;
    }
    
    /**Setzt das uebergebene Piece an die uebergebenen Koordinaten
     * @param x         X-Positoin des Piece
     * @param y         Y-Position des Piece
     * @param piece     zu platzierendes Piece
       **/
    public void set(int x, int y, Piece piece) {
        pieces[x][y] = piece;
    }
    
    /**Gibt das Piece an den uebergebenen Koordinaten zurueck
       @param x:        X-Koordinate
       @param y:        Y-Koordianate
       **/
    public Piece get(int x, int y) {
        return pieces[x][y];
    }
    
    /** Rotiert das Panel
     *  @param dir          gibt die Drehrichtung an; 1 = im UZS, 0 = gegen den UZS
       **/
    public void rotate(boolean dir) {
        Piece[][] tmp;
        tmp = new Piece[size][size];
        if (dir == true) {
            //fuer Drehung im den Uhrzeigersinn
            for (int x = 0; x<size; x++) {
                for (int y = 0; y<size; y++) {
                    tmp[y][size-x-1] = pieces[x][y];
                }
            }
        }
        else {
            //fuer Drehung gegen Uhrzeigersinn
            for (int x = 0; x<size; x++) {
                for (int y = 0; y<size; y++) {
                    tmp[size-y-1][x] = pieces[x][y];
                }
            }
        }
        
        //kopiert die Eintraege des temporaeren Arrays in das permanente
        for (int x = 0; x<size; x++) {
                for (int y = 0; y<size; y++) {
                    pieces[x][y] = tmp[x][y];
                }
         }
    }
    
    /**Gibt eine Zeile des Panels aus
       @param n         gewuenschte Zeile (startet bei 0)
       **/
    public void displayLine(int n) {
        for (int x = 0; x < size; x++) {
            if (pieces[x][n] != null) {
                System.out.print("1 ");
            } else {
                System.out.print("0 ");
            }
        }
    }

}
