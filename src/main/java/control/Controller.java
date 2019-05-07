package control;

import org.lwjgl.glfw.GLFW;

import core.Constants;
import core.event.Callback;
import core.event.CallbackStackInterruption;
import core.event.Event;
import core.event.KeyEvent;
import core.event.MouseClickedEvent;
import core.inputs.Keyboard;
import graphics.View;
import model.Gamemaster;

public class Controller implements Callback {

	private View view;

	public Controller(View view) {
		this.view = view;
	}

	@Override
	public void invoke(Event e) throws CallbackStackInterruption {
		if (e.eventType().equals("KeyEvent")) {
			invoke((KeyEvent) e);
		} else {
			invoke((MouseClickedEvent) e);
		}
	}

	public void invoke(KeyEvent ev) throws CallbackStackInterruption {

		boolean press = ev.getAction() == GLFW.GLFW_PRESS;

		if (press) {
			if (Constants.state == Constants.STATE.MENU) {
				if (Keyboard.toCharacter(ev.getKey()).equals("ENTER")) {
					Constants.state = Constants.STATE.GAME;
				}
			} else if (Constants.state == Constants.STATE.GAME) {

				if (!view.getMaster().getCurrentPlayer().isHuman()) {
					return;
				}
				if (Keyboard.toCharacter(ev.getKey()) == "ESC") {
					Constants.state = Constants.STATE.MENU;
				}

				if (view.getMaster().won()) {
					return;
				}
				switch (Keyboard.toCharacter(ev.getKey())) {
				case "NUM_1":
					doTurn(6);
					break;
				case "NUM_2":
					doTurn(7);
					break;
				case "NUM_3":
					doTurn(8);
					break;
				case "NUM_4":
					doTurn(3);
					break;
				case "NUM_5":
					doTurn(4);
					break;
				case "NUM_6":
					doTurn(5);
					break;
				case "NUM_7":
					doTurn(0);
					break;
				case "NUM_8":
					doTurn(1);
					break;
				case "NUM_9":
					doTurn(2);
					break;
				}
			}
		}
	}

	private void doTurn(int numKey) {

		int[] currentPanel = view.getCurrentPanel();
		Gamemaster master = view.getMaster();

		if (currentPanel == null) {
			currentPanel = new int[] { numKey % master.getPSize(), numKey / master.getPSize() };
			view.setCurrentPanel(currentPanel);
		} else {
			if (master.currentTurnIsPlaceTurn()) {
				int x = currentPanel[0] * master.getPSize() + numKey % master.getPSize();
				int y = currentPanel[1] * master.getPSize() + numKey / master.getPSize();
				master.placePiece(x, y);
				view.resetBoardColor();
				view.setCurrentPanel(null);
			} else {
				if (numKey == 3) {
					master.rotPanel(currentPanel[0], currentPanel[1], true);
					view.resetBoardColor();
					view.setCurrentPanel(null);

					if (!master.getCurrentPlayer().isHuman()) {
						playRoundComp();
					}

				} else if (numKey == 5) {
					master.rotPanel(currentPanel[0], currentPanel[1], false);
					view.resetBoardColor();
					view.setCurrentPanel(null);

					if (!master.getCurrentPlayer().isHuman()) {
						playRoundComp();
					}
				}
			}
		}
	}

	public void playRoundComp() {

		CompTurn c = new CompTurn(view);

		Thread t = new Thread(c, "Computer Turn Thread");
		// t.setDaemon(true);
		t.start();

	}

	public void invoke(MouseClickedEvent ce) throws CallbackStackInterruption {
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
