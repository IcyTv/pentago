public class Turn {

    private int[] piecePosition;
    private int[] rotatePosition;
    private boolean rotateDirection;

    private Board board;
    
    public Turn(int pieceX, int pieceY, int rotX, int rotY, boolean dir, Board board, boolean softFail) throws IllegalArgumentException{
        piecePosition = new int[2];
        piecePosition[0] = pieceX;
        piecePosition[1] = pieceY;
        
        rotatePosition = new int[2];
        rotatePosition[0] = rotX;
        rotatePosition[1] = rotY;
        
        rotateDirection = dir;

        this.board = board;

        if(!valid() && !softFail){
            throw new IllegalArgumentException("Illegal turn!");
        }
    }

    public Turn(int pieceX, int pieceY, int rotX, int rotY, boolean dir, Board board){
        this(pieceX, pieceY, rotX, rotY, dir, board, false);
    }

    public boolean valid(){
        if(board.get(piecePosition[0], piecePosition[1]) != null){
            return false;
        }
        return true;
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