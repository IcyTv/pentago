package model;

import static core.Constants.COLOR;

public class Colorpicker {
    private COLOR color;

    public Colorpicker(int n) {
        switch (n) {
        case 0:
            color = COLOR.RED;
            break;
        case 1:
            color = COLOR.GREEN;
            break;
        case 2:
            color = COLOR.BLUE;
            break;
        case 3:
            color = COLOR.PURPLE;
            break;
        /*case 4:
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
            break;*/
        default:
            System.out.println("i liegt nicht zwischen 0 und 10");
        }
    }

    public COLOR getColor() {
        return color;
    }
}