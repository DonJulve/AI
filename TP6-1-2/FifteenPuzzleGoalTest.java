package aima.core.environment.fifteenpuzzle;

import aima.core.search.framework.GoalTest;

/**
 * @author Ravi Mohan
 * 
 */
public class FifteenPuzzleGoalTest implements GoalTest {
	FifteenPuzzleBoard goal = new FifteenPuzzleBoard(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});

	public boolean isGoalState(Object state) {
		FifteenPuzzleBoard board = (FifteenPuzzleBoard) state;
		return board.equals(goal);
	}
}