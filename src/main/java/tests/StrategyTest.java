package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.BitSet;

import org.junit.jupiter.api.Test;
import org.lwjgl.system.CallbackI.B;

import model.Turn;
import strategy.BitBoard;
import strategy.BitPanel;
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
	
	@Test
	void bitPanelTest() {
		BitPanel p = new BitPanel(3, 2);
		
		p.set(0, 0, 1);
		p.set(0, 1, 0);
//		BitSet piece = p.get(0, 0);
//		BitSet eq = new BitSet(2);
//		eq.set(1);
//		
//
//		BitSet piece2 = p.get(0, 1);
//		BitSet eq2 = new BitSet(2);
//		eq2.set(0);
//		
//		if(!piece.equals(eq)) {
//			fail("BitSet assertion failed");
//		}
//		
//		if(!piece2.equals(eq2)) {
//			fail("BitSet assertion 2 failed");
//		}
//		
		p.rotate(true);
		
		BitPanel tmp = new BitPanel(3, 2);
		
		//0/1  2/3  4/5
		//6/7  8/9  10/11
		//12/13 14/15 16/17
		
		
		tmp.set(2, 0, 1);
		tmp.set(1, 0, 0);
		
		if(!tmp.equals(p)) {
			fail("Rotate assertion failed");
		}

	}
	
	@Test
	void emptyBitSetTest() {
		BitPanel p = new BitPanel(3, 2);
		
		if(!p.emptyBitSet(0, 0)) {
			fail("BitSet not empty");
		}
		
		p.set(0, 0, 0);
		
		if(p.emptyBitSet(0, 0)) {
			fail("BitSet empty");
		}
		
		if(!p.emptyBitSet(1, 0)) {
			fail("BitSet 1 0 not empty");
		}
	}

}
