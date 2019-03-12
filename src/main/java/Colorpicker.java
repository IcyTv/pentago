import java.awt.Color;

public class Colorpicker {
    private Color color;

    public Colorpicker(int n) {
        switch (n) {
        case 0:
            color = Color.RED;
            break;
        case 1:
            color = Color.GREEN;
            break;
        case 2:
            color = Color.BLUE;
            break;
        case 3:
            color = Color.MAGENTA;
            break;
        case 4:
            color = Color.CYAN;
            break;
        case 5:
            color = Color.YELLOW;
            break;
        case 6:
            color = Color.BLACK;
            break;
        case 7:
            color = Color.WHITE;
            break;
        case 8:
            color = Color.ORANGE;
            break;
        case 9:
            color = Color.PINK;
            break;
        default:
            System.out.println("i liegt nicht zwischen 0 und 10");
        }
    }

    public Color getColor() {
        return color;
    }
}