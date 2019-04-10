package graphics2d;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu extends JPanel {

    private JTextField playerCount;
    private JButton startGame;

    public Menu(View controller) {

        playerCount = new JTextField();
        playerCount.setText("2");

        startGame = new JButton();
        startGame.setText("Start Game");
        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                int players = Integer.parseInt(playerCount.getText());
                controller.toGame(players);
            }
        });

        add(playerCount);
        add(startGame);

    }
}