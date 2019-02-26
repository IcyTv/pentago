
public class Queue {
    private Player currPlayer;
    private Player lastPlayer;
    
    public Queue() {
        currPlayer = null;
        lastPlayer = null;
    }
    
    public void add(Player p) {
        /*if (currPlayer == null) {
            currPlayer = p;
            currPlayer.setNextPlayer(currPlayer);
        }
        else {
            currPlayer.add(p, currPlayer);
        }*/
        
        
        if (currPlayer == null) {
            currPlayer = p;
            lastPlayer = p;
            currPlayer.setNextPlayer(currPlayer);
        }
        else {
            lastPlayer.setNextPlayer(p);
            p.setNextPlayer(currPlayer);
            lastPlayer = p;
        }
    }
    
    public Player next() {
        if (currPlayer == null) {
            return null;
        }
        else {
            Player next = currPlayer;
            lastPlayer = currPlayer;
            currPlayer = currPlayer.getNextPlayer();
            return next;
        }
        
    }
}