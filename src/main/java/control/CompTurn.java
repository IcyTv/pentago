package control;

import core.event.Callback;
import core.event.CallbackStackInterruption;
import core.event.Event;
import graphics.View;
import model.Computer;
import model.Turn;

/**
 * The Class CompTurn.
 * 
 * Used for multithreading of AI turn generation
 */
public class CompTurn implements Runnable {

	/** The view.
	 * 
	 *  Used to make changes to the board
	 */
	private View view;

	/**
	 * Instantiates a new comp turn.
	 *
	 * @param view View for making changes to the Board
	 */
	public CompTurn(View view) {
		super();
		this.view = view;
	}

	/**
	 * Runs the turn in a new Thread
	 */
	@Override
	public void run() {

		System.out.println("Running computer computation");

		Computer c = (Computer) view.getMaster().getCurrentPlayer();

		final Turn turn = c.getTurn();

		view.getQueue().put(new Callback() {

			@Override
			public void invoke(Event e) throws CallbackStackInterruption {
				CustomEvent ce = (CustomEvent) e;

				ce.getView().getMaster().playRound(turn);
				ce.getView().getMaster().getQueue().nextPlayer();

				if (!ce.getView().getMaster().getCurrentPlayer().isHuman()) {
					ce.getView().getController().playRoundComp();
				}

				ce.getView().setCurrentColor();
			}

			@Override
			public int priority() {
				return 0;
			}

		});

	}
}
