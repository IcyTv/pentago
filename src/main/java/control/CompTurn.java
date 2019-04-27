package control;

import core.event.Callback;
import core.event.CallbackStackInterruption;
import core.event.Event;
import graphics.View;
import model.Computer;
import model.Turn;

public class CompTurn implements Runnable {

	private View view;
	private int numToRun;

	public CompTurn(View view) {
		super();
		this.view = view;
	}

	@Override
	public void run() {

		System.out.println("Running computer computation");

		Computer c = (Computer) view.getMaster().getCurrentPlayer();
		Turn turn = c.getTurn();

		view.getQueue().put(new Callback() {
			@Override
			public void invoke(Event e) throws CallbackStackInterruption {
				CustomEvent ce = (CustomEvent) e;
					ce.getView().getMaster().playRound(turn);
					ce.getView().getMaster().getQueue().nextPlayer();
					if(!ce.getView().getMaster().getCurrentPlayer().isHuman()) {
						ce.getView().getController().playRoundComp();
					}
			}

			@Override
			public int priority() {
				return 0;
			}
		});

	}

}
