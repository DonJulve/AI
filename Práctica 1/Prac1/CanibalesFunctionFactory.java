package aima.core.environment.Canibales;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class CanibalesFunctionFactory {
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
			CanibalesBoard board = (CanibalesBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(CanibalesBoard.M1CAN)) {
				actions.add(CanibalesBoard.M1CAN);
			}
			if (board.canMoveGap(CanibalesBoard.M2CAN)) {
				actions.add(CanibalesBoard.M2CAN);
			}
			if (board.canMoveGap(CanibalesBoard.M1MIS)) {
				actions.add(CanibalesBoard.M1MIS);
			}
			if (board.canMoveGap(CanibalesBoard.M2MIS)) {
				actions.add(CanibalesBoard.M2MIS);
			}
			if (board.canMoveGap(CanibalesBoard.M1CAN1MIS)) {
				actions.add(CanibalesBoard.M1CAN1MIS);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			CanibalesBoard board = (CanibalesBoard) s;

			if (CanibalesBoard.M1CAN.equals(a)
					&& board.canMoveGap(CanibalesBoard.M1CAN)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.M1CAN();
				return newBoard;
			} else if (CanibalesBoard.M2CAN.equals(a)
					&& board.canMoveGap(CanibalesBoard.M2CAN)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.M2CAN();
				return newBoard;
			} else if (CanibalesBoard.M1MIS.equals(a)
					&& board.canMoveGap(CanibalesBoard.M1MIS)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.M1MIS();
				return newBoard;
			} else if (CanibalesBoard.M2MIS.equals(a)
					&& board.canMoveGap(CanibalesBoard.M2MIS)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.M2MIS();
				return newBoard;
			}
			else if (CanibalesBoard.M1CAN1MIS.equals(a)
					&& board.canMoveGap(CanibalesBoard.M1CAN1MIS)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.M1CAN1MIS();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}