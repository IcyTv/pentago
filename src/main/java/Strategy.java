public class Strategy
{
    
    public Strategy()
    {
        
    }
    
    /**Gibt bisher nur einen Dummy-Turn zurueck**/
    public Turn findBestTurn() {
        return new Turn(0,0,0,0,true, new Board(0, 0));
    }
}
