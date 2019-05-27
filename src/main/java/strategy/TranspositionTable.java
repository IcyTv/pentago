package strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranspositionTable {

	private Map<Integer, List<BitBoard>> table;
	
	public TranspositionTable() {
		table = new HashMap<Integer, List<BitBoard>>();
	}
	
	public void addDepth(int depth) {
		table.put(depth, new ArrayList<BitBoard>());
	}
	
	public void addBoard(int depth, BitBoard board) {
		table.get(depth).add(board);
	}
	
	public boolean isBoardInTable(int depth, BitBoard board) {
		List<BitBoard> depthList = table.get(depth);
		
		for(BitBoard b: depthList) {
			if(board.equals(b)) {
				return true;
			}
		}
		
		return false;
	}
	
}
