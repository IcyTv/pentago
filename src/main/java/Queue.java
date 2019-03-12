/**Die Klasse Queue implementiert den ADT Schlange mit Wraparound
 * Sie hat Nodes und kennt den Gamemaster;
   **/


public class Queue {
    private Node current;
    private Node first;
    private Gamemaster gm;

    public Queue(Gamemaster gm) {
        this.gm = gm;
        current = null;
        first = null;
    }

    public Player getCurrentP() {
        return current.getPlayer();
    }

    public Node getFirstNode() {
        return first;
    }
    
    /**Reiht einen Player in die Schlange ein.
     * Wenn es der letzte Player ist, wird ihm der erste Player als Nachfolger gesetzt.
     * 
     * @param p         Einzureihender Player
       **/
    public void enqueue(Player p)
    {
        if (first == null)
        {
            first = new Node(p);
            current = first;
        } else {
            first.enqueue(first, p, gm.getAmountOfPlayers(), 1);
        }
    }

    public void nextPlayer() {
        if (current.getNext() == null) {
            current = first;
        } else {
            current = current.getNext();
        }
    }
}
