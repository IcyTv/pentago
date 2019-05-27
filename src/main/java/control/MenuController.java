package control;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

import java.util.ArrayList;
import java.util.List;

import core.Constants;
import core.event.ButtonEvent;
import core.event.Callback;
import core.event.CallbackStackInterruption;
import core.event.Event;
import core.event.KeyEvent;
import core.event.MouseClickedEvent;
import core.gui.Button;
import core.inputs.Keyboard;

public class MenuController implements Callback {

	public List<Button> buttons;
	
	public MenuController() {
		super();
		
		buttons = new ArrayList<Button>();
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
		for(Button b: buttons) {
			System.out.println(b.clicked(e.getX(), e.getY()));
			if(b.clicked(e.getX(), e.getY())) {
				b.onClick(new ButtonEvent(e.getAction() == GLFW_PRESS));
			}
		}
	}
	
	public void addButton(Button b) {
		buttons.add(b);
	}
	
	public void removeButton(int x) {
		buttons.remove(x);
	}

	@Override
	public int priority() {
		return 0;
	}

}
