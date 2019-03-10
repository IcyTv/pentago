
public class Node {
	private Player player;
	private Node   next;
	
	public Node(Player player, Node next)
	{
		this.player = player;
		this.next   = next;
	}
	
	public Node(Player player)
	{
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public Node getNext()
	{
		return next;
	}
	
	public void setNext(Node next)
	{
		this.next = next;
	}
	
	public void enqueue(Node firstP, Player shouldAdd, int amountOfPlayers, int counter)
	{
		if (next == null)
		{
			Node n = new Node(shouldAdd);
			next = n;
			System.out.println(amountOfPlayers +", "+ counter);
			if (counter == amountOfPlayers-1)
			{
				next.setNext(firstP);
			}
		}
		else
		{
			counter++;
			next.enqueue(firstP, shouldAdd, amountOfPlayers, counter);
		}
	}
}
