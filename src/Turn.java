public class Turn {

    private int[] piecePosition;
    private int[] rotatePosition;
    private boolean rotateDirection;
    
    public Turn(int pieceX, int pieceY, int rotX, int rotY, boolean dir){
        piecePosition = new int[2];
        piecePosition[0] = pieceX;
        piecePosition[1] = pieceY;
        
        rotatePosition = new int[2];
        rotatePosition[0] = rotX;
        rotatePosition[1] = rotY;
        
        rotateDirection = dir;
    }
    
    public int[] getPiecePosition() {
        return piecePosition;
    }
    public int[] getRotatePosition() {
        return rotatePosition;
    }
    public boolean getRotateDirection() {
        return rotateDirection;
    }

}