package aima.core.environment.CabraLoboCol;

import aima.core.search.framework.GoalTest;

/**
 * @author Ravi Mohan
 * 
 */
public class CCLTest implements GoalTest {
	CCLBoard goal = new CCLBoard(new char[] { '0', '0', '0', 'f', '1', '1', '1'});

	public boolean isGoalState(Object state) {
		CCLBoard board = (CCLBoard) state;
		return board.equals(goal);
	}
}