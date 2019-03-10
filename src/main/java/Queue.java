
public class Queue {
    private Node current;
    private Node first;
    private Gamemaster gm;
    
    public Queue(Gamemaster gm) {
    	this.gm     = gm;
        current = null;
        first   = null;
    }
    
    public Player getCurrentP()
    {
    	return current.getPlayer();
    }
    
    public Node getFirstNode()
    {
    	return first;
    }
    
    public void enqueue(Player p)
    {
    	if (first == null)
    	{
    		first = new Node(p);
    		current = first;
    	}
    	else
    	{
    		first.enqueue(first, p, gm.getAmountOfPlayers(), 1);
    	}
    }
    
    public void nextPlayer()
    {
    	current = current.getNext();
    }
}