package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class ManhattanHeuristicFunction2 implements HeuristicFunction {

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		EightPuzzleBoard goalboard = EightPuzzleGoalTest2.goal;
		int retVal = 0;
		for (int i = 1; i < 9; i++) {
			XYLocation loc = board.getLocationOf(i);
			XYLocation locgoal = goalboard.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(loc, locgoal);
		}
		return retVal;

	}

	public int evaluateManhattanDistanceOf(XYLocation loc, XYLocation locgoal) {
		int retVal = -1;
		int xpos = loc.getXCoOrdinate();
		int xposgoal = locgoal.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();
		int yposgoal = locgoal.getXCoOrdinate();
		retVal = Math.abs(xposgoal - xpos) + Math.abs(yposgoal - ypos);
		return retVal;
	}	
}