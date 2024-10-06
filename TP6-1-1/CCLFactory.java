package aima.core.environment.CabraLoboCol;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class CCLFactory {
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
			CCLBoard board = (CCLBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(CCLBoard.MLOBO)) {
				actions.add(CCLBoard.MLOBO);
			}
			if (board.canMoveGap(CCLBoard.MCABRA)) {
				actions.add(CCLBoard.MCABRA);
			}
			if (board.canMoveGap(CCLBoard.MCOL)) {
				actions.add(CCLBoard.MCOL);
			}
			if (board.canMoveGap(CCLBoard.MSOLO)) {
				actions.add(CCLBoard.MSOLO);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			CCLBoard board = (CCLBoard) s;

			if (CCLBoard.MLOBO.equals(a)
					&& board.canMoveGap(CCLBoard.MLOBO)) {
				CCLBoard newBoard = new CCLBoard(board);
				newBoard.MLOBO();
				return newBoard;
			} else if (CCLBoard.MCABRA.equals(a)
					&& board.canMoveGap(CCLBoard.MCABRA)) {
				CCLBoard newBoard = new CCLBoard(board);
				newBoard.MCABRA();
				return newBoard;
			} else if (CCLBoard.MCOL.equals(a)
					&& board.canMoveGap(CCLBoard.MCOL)) {
				CCLBoard newBoard = new CCLBoard(board);
				newBoard.MCOL();
				return newBoard;
			} else if (CCLBoard.MSOLO.equals(a)
					&& board.canMoveGap(CCLBoard.MSOLO)) {
				CCLBoard newBoard = new CCLBoard(board);
				newBoard.MSOLO();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}