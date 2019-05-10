package control;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

import core.Constants;
import core.event.Callback;
import core.event.CallbackStackInterruption;
import core.event.Event;
import core.event.KeyEvent;
import core.event.MouseClickedEvent;
import core.inputs.Keyboard;

public class MenuController implements Callback {

	public MenuController() {
		super();
	}

	@Override
	public void invoke(Event e) throws CallbackStackInterruption {
		if (e.eventType() == "KeyEvent") {
			invoke((KeyEvent) e);
		} else if (e.eventType() == "MouseClickedEvent") {
			invoke((MouseClickedEvent) e);
		}

		if (Constants.state == Constants.STATE.MENU) {
			throw new CallbackStackInterruption("Menu interrupt");
		}
	}

	public void invoke(KeyEvent e) {

		if (Keyboard.toCharacter(e.getKey()) == "ENTER" && e.getAction() == GLFW_PRESS) {
			Constants.state = Constants.STATE.GAME;
		} else {
			System.out.println(Keyboard.toCharacter(e.getKey()));
		}
	}

	public void invoke(MouseClickedEvent e) {
		if (e.getAction() == GLFW_PRESS) {
			System.out.println(e.getX() + " " + e.getY());
		}
	}

	@Override
	public int priority() {
		return 0;
	}

}
