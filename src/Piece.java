 

import java.awt.Color;

public class Piece {

    private Color color;
    private Player player;
    
    public Piece (Player player, Color color) {
       this.player = player;
       this.color = color;
    }
    
    public Player getPlayer() {
       return player;
    }
    
    public Color getColor() {
        return color;
    }
}
