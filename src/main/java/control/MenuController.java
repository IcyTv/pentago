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
import graphics.View;

/**
 * The Class MenuController
 * 
 * Used to control inputs during menu phase
 */
public class MenuController implements Callback {

	/** Buttons */
	public List<Button> buttons;
	
	/** The view. */
	private View view;
	
	/**
	 * Instantiates a new menu controller.
	 *
	 * @param view the view
	 */
	public MenuController(View view) {
		super();
		
		buttons = new ArrayList<Button>();
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
		if (e.eventType() == "KeyEvent") {
			invoke((KeyEvent) e);
		} else if (e.eventType() == "MouseClickedEvent") {
			invoke((MouseClickedEvent) e);
		}

		if (Constants.state == Constants.STATE.MENU) {
			throw new CallbackStackInterruption("Menu interrupt");
		}
	}

	/**
	 * Invoked on KeyEvent
	 *
	 * @param e KeyEvent
	 */
	public void invoke(KeyEvent e) {
		if (Keyboard.toCharacter(e.getKey()) == "ENTER" && e.getAction() == GLFW_PRESS && view.getTutorialStage() == 0) {
			view.setTutorialStage(1);
		} else if (Keyboard.toCharacter(e.getKey()) == "ENTER" && e.getAction() == GLFW_PRESS && view.getTutorialStage() == 1) {
			view.setTutorialStage(2);
		} else if (Keyboard.toCharacter(e.getKey()) == "ENTER" && e.getAction() == GLFW_PRESS && view.getTutorialStage() == 2) {
			view.setTutorialStage(3);
		} else if (Keyboard.toCharacter(e.getKey()) == "ENTER" && e.getAction() == GLFW_PRESS && view.getTutorialStage() == 3) {
			view.setTutorialStage(-1);
			Constants.state = Constants.STATE.GAME;
		} else if (Keyboard.toCharacter(e.getKey()) == "ENTER" && e.getAction() == GLFW_PRESS) {
			Constants.state = Constants.STATE.GAME;
		} else {
			System.out.println(Keyboard.toCharacter(e.getKey()));
		}
	}

	/**
	 * Invoked on MouseClickedEvent
	 *
	 * Used for Buttons
	 *
	 * @param e MouseClickedEvent
	 */
	public void invoke(MouseClickedEvent e) {
		for(Button b: buttons) {
			System.out.println(b.clicked(e.getX(), e.getY()));
			if(b.clicked(e.getX(), e.getY())) {
				b.onClick(new ButtonEvent(e.getAction() == GLFW_PRESS));
			}
		}
	}
	
	/**
	 * Adds a button.
	 *
	 * @param b a button
	 */
	public void addButton(Button b) {
		buttons.add(b);
	}
	
	/**
	 * Removes a button.
	 *
	 * @param x a button
	 */
	public void removeButton(int x) {
		buttons.remove(x);
	}

	/**
	 * Priority.
	 *
	 * @return the priority of the menu controller
	 */
	@Override
	public int priority() {
		return 0;
	}

}
