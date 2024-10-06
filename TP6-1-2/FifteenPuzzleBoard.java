package aima.core.environment.fifteenpuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * @author R. Lunde
 */
public class FifteenPuzzleBoard {

	public static Action LEFT = new DynamicAction("Left");

	public static Action RIGHT = new DynamicAction("Right");

	public static Action UP = new DynamicAction("Up");

	public static Action DOWN = new DynamicAction("Down");

	private int[] state;

	//
	// PUBLIC METHODS
	//

	public FifteenPuzzleBoard() {
		state = new int[] { 2, 1, 3, 4, 8, 5, 6, 7, 12, 9, 10, 11, 13, 0, 14, 15 };
	}

	public FifteenPuzzleBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public FifteenPuzzleBoard(FifteenPuzzleBoard copyBoard) {
		this(copyBoard.getState()); //Copia el estado
	}

	public int[] getState() {
		return state;
	}

	public int getValueAt(XYLocation loc) {
		return getValueAt(loc.getXCoOrdinate(), loc.getYCoOrdinate());
	}

	public XYLocation getLocationOf(int val) {
		int absPos = getPositionOf(val);
		return new XYLocation(getXCoord(absPos), getYCoord(absPos));
	}

	public void moveGapRight() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int ypos = getYCoord(gapPos);
		if (!(ypos == 3)) {
			int valueOnRight = getValueAt(x, ypos + 1);
			setValue(x, ypos, valueOnRight);
			setValue(x, ypos + 1, 0);
		}

	}

	public void moveGapLeft() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int ypos = getYCoord(gapPos);
		if (!(ypos == 0)) {
			int valueOnLeft = getValueAt(x, ypos - 1);
			setValue(x, ypos, valueOnLeft);
			setValue(x, ypos - 1, 0);
		}

	}

	public void moveGapDown() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int y = getYCoord(gapPos);
		if (!(x == 3)) {
			int valueOnBottom = getValueAt(x + 1, y);
			setValue(x, y, valueOnBottom);
			setValue(x + 1, y, 0);
		}

	}

	public void moveGapUp() {
		int gapPos = getGapPosition();
		int x = getXCoord(gapPos);
		int y = getYCoord(gapPos);
		if (!(x == 0)) {
			int valueOnTop = getValueAt(x - 1, y);
			setValue(x, y, valueOnTop);
			setValue(x - 1, y, 0);
		}
	}

	public List<XYLocation> getPositions() {
		ArrayList<XYLocation> retVal = new ArrayList<XYLocation>();
		for (int i = 0; i < 16; i++) {
			int absPos = getPositionOf(i);
			XYLocation loc = new XYLocation(getXCoord(absPos),
					getYCoord(absPos));
			retVal.add(loc);

		}
		return retVal;
	}

	public void setBoard(List<XYLocation> locs) {
		int count = 0;
		for (int i = 0; i < locs.size(); i++) {
			XYLocation loc = locs.get(i);
			this.setValue(loc.getXCoOrdinate(), loc.getYCoOrdinate(), count);
			count = count + 1;
		}
	}

	public boolean canMoveGap(Action where) {	//Dime una acción a realizar y yo te digo si se puede o no
		boolean retVal = true;
		int absPos = getPositionOf(0);
		if (where.equals(LEFT))
			retVal = (getYCoord(absPos) != 0);
		else if (where.equals(RIGHT))
			retVal = (getYCoord(absPos) != 3);
		else if (where.equals(UP))
			retVal = (getXCoord(absPos) != 0);
		else if (where.equals(DOWN))
			retVal = (getXCoord(absPos) != 3);
		return retVal;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		FifteenPuzzleBoard aBoard = (FifteenPuzzleBoard) o;

		for (int i = 0; i < 8; i++) {
			if (this.getPositionOf(i) != aBoard.getPositionOf(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		for (int i = 0; i < 8; i++) {
			int position = this.getPositionOf(i);
			result = 37 * result + position;
		}
		return result;
	}

	@Override
	public String toString() {
		String retVal = state[0] + " " + state[1] + " " + state[2] + " " + state[3] + "\n"
				+ state[4] + " " + state[5] + " " + state[6] + " " + state[7] + "\n"
				+ state[8] + " " + state[9] + " " + state[10] + " " + state[11] + "\n"
				+ state[12] + " " + state[13] + " " + state[14] + " " + state[15] + "\n";
		return retVal;
	}

	//
	// PRIVATE METHODS
	//

	/**
	 * Note: The graphic representation maps x values on row numbers (x-axis in
	 * vertical direction).
	 */
	private int getXCoord(int absPos) {
		return absPos / 4;
	}

	/**
	 * Note: The graphic representation maps y values on column numbers (y-axis
	 * in horizontal direction).
	 */
	private int getYCoord(int absPos) {
		return absPos % 4;
	}

	private int getAbsPosition(int x, int y) {
		return x * 4 + y;
	}

	private int getValueAt(int x, int y) {
		// refactor this use either case or a div/mod soln
		return state[getAbsPosition(x, y)];
	}

	private int getGapPosition() {
		return getPositionOf(0);
	}

	private int getPositionOf(int val) {
		int retVal = -1;
		for (int i = 0; i < 16; i++) {
			if (state[i] == val) {
				retVal = i;
			}
		}
		return retVal;
	}

	private void setValue(int x, int y, int val) {
		int absPos = getAbsPosition(x, y);
		state[absPos] = val;

	}
}