package control;

import core.event.Event;
import graphics.View;

/**
 * The Class CustomEvent.
 */
public class CustomEvent implements Event {

	/** The view. */
	private View view;

	/**
	 * Instantiates a new custom event.
	 *
	 * @param view the view
	 */
	public CustomEvent(View view) {
		this.view = view;
	}

	/**
	 * Gets the view.
	 *
	 * @return the view
	 */
	public View getView() {
		return view;
	}

	/**
	 * Event type.
	 *
	 * @return the string
	 */
	@Override
	public String eventType() {
		return null;
	}

}