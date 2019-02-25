import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * Title:        View
 * Description:  Ein GUI für die exitierenden Modell-Klassen. Die View hat den Kontroller.
 */


public class View  extends JFrame 
{
    JPanel myJPanel                 = new JPanel();
    
    BorderLayout myBorderLayout     = new BorderLayout();
   
    JTextField punktestandTextField = new JTextField();
    JTextField zahlObenTextField    = new JTextField();
    JTextField kippenTextField      = new JTextField();
 
    JLabel jLabelPunktestand        = new JLabel();
    JLabel jLabelZahlOben           = new JLabel();
    JLabel jLabelKippen             = new JLabel();
  
    JButton wuerfeln                = new JButton();
    JButton kippen                  = new JButton();
    JButton reset                   = new JButton();
    JButton exit                    = new JButton();

    public View() {
        int pos_x = 30;
        
        Controller myController= new Controller(this);
        
        getContentPane().setLayout(myBorderLayout);
        myJPanel.setLayout(null);
        
        punktestandTextField.setBounds(210, 80, 130, 30);
        zahlObenTextField.setBounds(210,120, 130, 30);
        kippenTextField.setBounds(210, 160, 130, 30);
        
        jLabelPunktestand.setText("Aktueller Punktestand:");
        jLabelPunktestand.setBounds(new Rectangle(pos_x, 80, 150, 30));
        jLabelZahlOben.setText("Obere Wuerfelzahl:");
        jLabelZahlOben.setBounds(new Rectangle(pos_x, 120, 150, 30));
        jLabelKippen.setText("Kippen auf:");
        jLabelKippen.setBounds(new Rectangle(pos_x, 160, 150, 30));
    
        wuerfeln.setText("Wuerfeln");
        wuerfeln.setBounds(new Rectangle(pos_x, 210, 110, 30));
        wuerfeln.setActionCommand("wuerfeln");
        wuerfeln.addActionListener(myController);
        
        kippen.setText("Kippen");
        kippen.setBounds(new Rectangle(pos_x, 250, 110, 30));
        kippen.setActionCommand("kippen");
        kippen.addActionListener(myController);

        reset.setText("Reset");
        reset.setBounds(new Rectangle(pos_x, 290, 110, 30));
        reset.setActionCommand("reset");
        reset.addActionListener(myController);
        
        exit.setText("Exit");
        exit.setBounds(new Rectangle(pos_x, 330, 110, 30));
        exit.setActionCommand("exit");
        exit.addActionListener(myController);                  
    
        myJPanel.setBackground(Color.orange);
        
        myJPanel.add(punktestandTextField, null);
        myJPanel.add(zahlObenTextField, null);
        myJPanel.add(kippenTextField, null);
        
        myJPanel.add(jLabelPunktestand, null);
        myJPanel.add(jLabelZahlOben, null);
        myJPanel.add(jLabelKippen, null);
        
        myJPanel.add(wuerfeln, null);
        myJPanel.add(kippen, null);
        myJPanel.add(reset, null);
        myJPanel.add(exit, null);
        
        this.getContentPane().add(myJPanel, BorderLayout.CENTER);
  }

}