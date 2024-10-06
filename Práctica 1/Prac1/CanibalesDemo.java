package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.Canibales.CanibalesBoard;
import aima.core.environment.Canibales.CanibalesFunctionFactory;
import aima.core.environment.Canibales.CanibalesGoalTest;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;

/**
 * @author Ravi Mohan
 * 
 */

public class CanibalesDemo {
	static CanibalesBoard initial = new CanibalesBoard(
			new char[] { '3','3','i','0','0' });

	public static void main(String[] args) {
		CanibalesSearch(new BreadthFirstSearch(new GraphSearch()), initial, "BFS");
		System.out.println("\n");
		CanibalesSearch(new DepthLimitedSearch(11), initial, "DLS(11)");
		System.out.println("\n");
		CanibalesSearch(new IterativeDeepeningSearch(), initial, "IDLS");
	}
	public static void CanibalesSearch(Search busqueda, CanibalesBoard estado, String problema) {
		try {
			System.out.println("Misioneros y canibales " + problema + "-->");
			long tiempo = System.currentTimeMillis();
			Problem problem = new Problem(estado, CanibalesFunctionFactory
					.getActionsFunction(), CanibalesFunctionFactory
					.getResultFunction(), new CanibalesGoalTest());
			Search search = busqueda;
			SearchAgent agent = new SearchAgent(problem, search);
			List<Action> actions = agent.getActions();
			String pathcostM =agent.getInstrumentation().getProperty("pathCost");
			int depth = 0;
			if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
			else depth = 0;
			int expandedNodes;
			if (agent.getInstrumentation().getProperty("nodesExpanded")==null) expandedNodes= 0;
			else expandedNodes =
			(int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
			int queueSize;
			if (agent.getInstrumentation().getProperty("queueSize")==null) queueSize=0;
			else queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize"));
			int maxQueueSize;
			if (agent.getInstrumentation().getProperty("maxQueueSize")==null) maxQueueSize= 0;
			else maxQueueSize =
			(int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
			tiempo = System.currentTimeMillis() - tiempo;
			System.out.println("pathCost: " + pathcostM);
			System.out.println("nodesExpanded: " + expandedNodes);
			if (problema == "BFS") {
				System.out.println("queueSize: " + queueSize);
				System.out.println("maxQueueSize: " + maxQueueSize);
			}
			System.out.println("Tiempo: " + tiempo + "\n");
			System.out.println("SOLUCIÓN:");
			System.out.println("GOAL STATE");
			System.out.println("RIBERA-IZQ            --RIO-- BOTE M M M C C C RIBERA-DCH");
			System.out.println("CAMINO ENCONTRADO");
			executeActions(actions, problem);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}
	
	public static void executeActions(List<Action> actions, Problem problem) {
		Object initialState = problem.getInitialState();
		ResultFunction resultFunction = problem.getResultFunction();
		Object state = initialState;
		System.out.println("INITIAL STATE");
		System.out.println(state);
		for (Action action : actions) {
			System.out.println(action.toString());
			state = resultFunction.result(state, action);
			System.out.println(state);
			System.out.println("- - -");
		}
	}
}