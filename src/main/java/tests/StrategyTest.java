package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.lwjgl.system.CallbackI.B;

import model.Turn;
import strategy.BitBoard;
import strategy.Strategy;

class StrategyTest {

	@Test
	void test() {
		BitBoard bb = new BitBoard(9, 3, 2);
		
		for(int i = 0; i < 4; i++) {
			bb.set(i, 0, 0);
			bb.set(i, 1, 1);
		}
		
		bb.set(4, 1, false, 1);
		

		Strategy s = new Strategy(null, 2, 2);
		s.setCurrentState(bb);
		Turn t = s.findBestTurn();
		
		if(t.getPiecePosition()[0] != 5 || t.getPiecePosition()[1] != 0) {
			fail("Wrong position " + t.getPiecePosition()[0] + " " + t.getPiecePosition()[1]
					 + "\nRotation Position " + t.getRotatePosition()[0] + " " + t.getRotatePosition()[1]);
		}
	}

}
