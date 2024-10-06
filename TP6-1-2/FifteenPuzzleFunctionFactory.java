package aima.core.environment.fifteenpuzzle;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class FifteenPuzzleFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {	//Para un estado te dice todas las acciones que puedes hacer
		if (null == _actionsFunction) {
			_actionsFunction = new EPActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {		//Para una accion devuelve el estado resultante
		if (null == _resultFunction) {
			_resultFunction = new EPResultFunction();
		}
		return _resultFunction;
	}

	private static class EPActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			FifteenPuzzleBoard board = (FifteenPuzzleBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(FifteenPuzzleBoard.UP)) {
				actions.add(FifteenPuzzleBoard.UP);
			}
			if (board.canMoveGap(FifteenPuzzleBoard.DOWN)) {
				actions.add(FifteenPuzzleBoard.DOWN);
			}
			if (board.canMoveGap(FifteenPuzzleBoard.LEFT)) {
				actions.add(FifteenPuzzleBoard.LEFT);
			}
			if (board.canMoveGap(FifteenPuzzleBoard.RIGHT)) {
				actions.add(FifteenPuzzleBoard.RIGHT);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			FifteenPuzzleBoard board = (FifteenPuzzleBoard) s;

			if (FifteenPuzzleBoard.UP.equals(a)
					&& board.canMoveGap(FifteenPuzzleBoard.UP)) {
				FifteenPuzzleBoard newBoard = new FifteenPuzzleBoard(board);
				newBoard.moveGapUp();
				return newBoard;
			} else if (FifteenPuzzleBoard.DOWN.equals(a)
					&& board.canMoveGap(FifteenPuzzleBoard.DOWN)) {
				FifteenPuzzleBoard newBoard = new FifteenPuzzleBoard(board);
				newBoard.moveGapDown();
				return newBoard;
			} else if (FifteenPuzzleBoard.LEFT.equals(a)
					&& board.canMoveGap(FifteenPuzzleBoard.LEFT)) {
				FifteenPuzzleBoard newBoard = new FifteenPuzzleBoard(board);
				newBoard.moveGapLeft();
				return newBoard;
			} else if (FifteenPuzzleBoard.RIGHT.equals(a)
					&& board.canMoveGap(FifteenPuzzleBoard.RIGHT)) {
				FifteenPuzzleBoard newBoard = new FifteenPuzzleBoard(board);
				newBoard.moveGapRight();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}