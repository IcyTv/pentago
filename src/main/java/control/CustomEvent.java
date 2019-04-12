package control;

import core.event.Event;
import graphics.View;

public class CustomEvent implements Event {

	private View view;

	public CustomEvent(View view) {
		this.view = view;
	}

	public View getView() {
		return view;
	}

	@Override
	public String eventType() {
		return null;
	}

}