package control;

import core.WindowManager;
import core.event.Callback;
import core.event.CallbackStackInterruption;
import core.event.Event;
import core.event.KeyEvent;
import core.gui.Menu;
import core.inputs.Keyboard;

public class MenuController implements Callback {

	private WindowManager manager;
	private Menu menu;
	
	public MenuController(WindowManager manager, Menu menu) {
		this.manager = manager;
		this.menu = menu;
	}
	
	@Override
	public void invoke(Event e) throws CallbackStackInterruption {
		if(e.eventType() == "KeyEvent") {
			invoke((KeyEvent) e);
		}
	}
	
	public void invoke(KeyEvent e) {

		if(Keyboard.toCharacter(e.getKey()) == "ENTER") {
			menu.stop();
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
