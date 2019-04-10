package control;

import core.WindowManager;
import core.event.Callback;
import core.event.CallbackStackInterruption;
import core.event.Event;
import core.event.KeyEvent;
import core.inputs.Keyboard;

public class MenuController implements Callback {

	private WindowManager manager;
	
	public MenuController(WindowManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void invoke(Event e) throws CallbackStackInterruption {
		if(e.eventType() == "KeyEvent") {
			invoke((KeyEvent) e);
		}
	}
	
	public void invoke(KeyEvent e) {

		if(Keyboard.toCharacter(e.getKey()) == "ENTER") {
			manager.toScene(0);
		} else {
			System.out.println(Keyboard.toCharacter(e.getKey()));
		}
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 0;
	}

}
