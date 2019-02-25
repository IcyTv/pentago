import java.awt.event.*;

/**
 * Title:           Controller
 * Description:     Dies ist der Controller dieses Spiels.
 *                  Der Controller hat das Spiel und kennt die View.
 */

public class Controller implements ActionListener
{
    private View myView;
    Gamemaster spiel;

    public Controller(View pView)
    {
        myView = pView;
        spiel = new Gamemaster(4,9,3);
    }
    
    public void actionPerformed (ActionEvent e)
    {
        /*String cmd = e.getActionCommand();
        
        if (cmd.equals("wuerfeln")) 
        {
            spiel.wuerfel();
            
            myView.punktestandTextField.setText(Integer.toString(spiel.gibPunktestandAn()));
            myView.zahlObenTextField.setText(Integer.toString(spiel.gibObereZahlAn()));
        }
        else if (cmd.equals("kippen")) 
        {
            String str = myView.kippenTextField.getText();
            if (str.equals("")) {
                spiel.kippen(0);
            }
            else {
                int z = Integer.parseInt(myView.kippenTextField.getText());
                if ((1 <= z) && (z <= 6)) {
                    boolean regelkonform = spiel.kippen(z);
                    
                    if (!regelkonform) {
                        System.out.println("Spielregeln beachten!\n");
                    }
                }
                else {
                    System.out.println("Nur Zahlen von 1 bis 6 erlaubt!\n");
                }
            }
            
            myView.punktestandTextField.setText(Integer.toString(spiel.gibPunktestandAn()));
            myView.zahlObenTextField.setText(Integer.toString(spiel.gibObereZahlAn()));
            myView.kippenTextField.setText("");
        }
        else if (cmd.equals("reset")) 
        {
            spiel.reset(0,0);
            
            myView.punktestandTextField.setText("");
            myView.zahlObenTextField.setText("");
            myView.kippenTextField.setText("");
        }
        else if (cmd.equals("exit")) 
        {
            System.exit(0);
        }*/
  }
}