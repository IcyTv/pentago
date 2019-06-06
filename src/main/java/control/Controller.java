package control;

import org.lwjgl.glfw.GLFW;

import core.event.Callback;
import core.event.CallbackStackInterruption;
import core.event.Event;
import core.event.KeyEvent;
import core.event.MouseClickedEvent;
import core.inputs.Keyboard;
import graphics.View;
import model.Gamemaster;
import model.Turn;

/**
 * The Class Controller.
 * Class for handeling inputs during game phase
 */
public class Controller implements Callback {

	/** The view. */
	private View view;

	/**
	 * Instantiates a new controller.
	 *
	 * @param view View for making changes to the Board
	 */
	public Controller(View view) {
		this.view = view;
	}

	/**
	 * Invoke
	 * 
	 * Called on keypress or mouse click
	 *
	 * @param e the event
	 * @throws CallbackStackInterruption interrupts the callbackstack
	 */
	@Override
	public void invoke(Event e) throws CallbackStackInterruption {
		if (e.eventType().equals("KeyEvent")) {
			invoke((KeyEvent) e);
		} else {
			invoke((MouseClickedEvent) e);
		}
	}

	/**
	 * Invoke KeyEvent
	 * 
	 * Called if Event is KeyEvent
	 *
	 * @param ev the KeyEvent
	 * @throws CallbackStackInterruption interrupts the callbackstack
	 */
	public void invoke(KeyEvent ev) throws CallbackStackInterruption {

		if (!view.getMaster().getCurrentPlayer().isHuman()) {
			return;
		}

		boolean press = ev.getAction() == GLFW.GLFW_PRESS;

		if (press) {

			if (!view.getMaster().getCurrentPlayer().isHuman()) {
				return;
			}
			if (Keyboard.toCharacter(ev.getKey()) == "ESC") {
				view.setTutorialStage(0);
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

	/**
	 * Do turn
	 * 
	 * method for handeleing user turns
	 *
	 * @param numKey Panel selection
	 */
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
				if (Turn.valid(view.getMaster().getBoard(), x, y)) {
					System.out.println("Valid Turn");
					master.placePiece(x, y);
				} else {
					System.out.printf("Invalid Turn %d %d\n", x, y);
					System.out.println(view.getMaster().getBoard());
				}
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

	/**
	 * Plays a computer turn
	 */
	public void playRoundComp() {

		CompTurn c = new CompTurn(view);

		Thread t = new Thread(c, "Computer Turn Thread");
		// t.setDaemon(true);
		t.start();

	}

	/**
	 * Invoke MouseClickedEvent
	 * 
	 * Caled on mouse click
	 *
	 * @param ce the ClickEvent
	 * @throws CallbackStackInterruption interrupts the callbackstack
	 */
	public void invoke(MouseClickedEvent ce) throws CallbackStackInterruption {
	}

	/**
	 * Priority.
	 *
	 * @return the priority of this Callback
	 */
	@Override
	public int priority() {
		return 2;
	}

}
