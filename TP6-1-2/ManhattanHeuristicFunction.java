package aima.core.environment.fifteenpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class ManhattanHeuristicFunction implements HeuristicFunction {
	
	FifteenPuzzleBoard goal = new FifteenPuzzleBoard(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 });

	public double h(Object state) {
		FifteenPuzzleBoard board = (FifteenPuzzleBoard) state;
		int retVal = 0;
		for (int i = 1; i < 16; i++) {
			XYLocation inicio = board.getLocationOf(i);
			XYLocation fin = goal.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(inicio, fin);
		}
		return retVal;

	}

	public int evaluateManhattanDistanceOf(XYLocation inicio, XYLocation fin) {
		int xPosInicial = inicio.getXCoOrdinate();
		int yPosInicial = inicio.getYCoOrdinate();
		
		int xPosLocFinal = fin.getXCoOrdinate();
		int yPosLocFinal = fin.getYCoOrdinate();
		return Math.abs(xPosLocFinal - xPosInicial) + Math.abs(yPosLocFinal - yPosInicial);
	}
}