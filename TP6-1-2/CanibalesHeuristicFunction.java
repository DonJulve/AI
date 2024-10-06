package aima.core.environment.Canibales;

import aima.core.search.framework.HeuristicFunction;

public class CanibalesHeuristicFunction implements HeuristicFunction{
	public double h(Object initial) {
		CanibalesBoard state = (CanibalesBoard) initial;
		return calcular(state);
	}
	
	private double calcular(CanibalesBoard state) {
		char[] posiciones = state.getState();
		int orilla = 0;
		if(posiciones[2] == 0) {
			orilla = 1;
		}
		return 2*(posiciones[0] + posiciones[1]) - orilla;
	}
}