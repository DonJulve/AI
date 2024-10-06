package aima.gui.demo.search;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
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

public class EightPuzzlePract1 {
	static EightPuzzleBoard boardWithThreeMoveSolution = new EightPuzzleBoard(
			new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });;

	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2,
			7, 5, 8, 3, 0, 6 });

	static EightPuzzleBoard extreme = new EightPuzzleBoard(new int[] { 0, 8, 7,
			6, 5, 4, 3, 2, 1 });

	public static void main(String[] args) {
		System.out.printf("%15s|%11s|%11s|%11s|%11s|%11s\n", "Problema", "Depth",
				"Expand","Q.Size","MaxQS","Tiempo");
		eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), boardWithThreeMoveSolution, "BFS-G-3");
		eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), boardWithThreeMoveSolution, "BFS-T-3");
		eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), boardWithThreeMoveSolution, "DFS-G-3");
		//eightPuzzleSearch(new DepthFirstSearch(new TreeSearch()), boardWithThreeMoveSolution, "DFS-T-3");
		System.out.printf("%15s|%11s|%11s|%11s|%11s|%11s\n", "DFS-T-3","---","---","---","---","---","---");
		eightPuzzleSearch(new DepthLimitedSearch(9), boardWithThreeMoveSolution, "DLS-9-3");
		eightPuzzleSearch(new DepthLimitedSearch(3), boardWithThreeMoveSolution, "DLS-3-3");
		eightPuzzleSearch(new IterativeDeepeningSearch(), boardWithThreeMoveSolution, "IDS-3");
		eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), boardWithThreeMoveSolution, "UCS-G-3");
		eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), boardWithThreeMoveSolution, "UCS-T-3");
		eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), random1, "BFS-G-9");
		eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), random1, "BFS-T-9");
		eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), random1, "DFS-G-9");
		//eightPuzzleSearch(new DepthFirstSearch(new TreeSearch()), random1, "DFS-T-9");
		System.out.printf("%15s|%11s|%11s|%11s|%11s|%11s\n", "DFS-T-9","---","---","---","---","---","---");
		eightPuzzleSearch(new DepthLimitedSearch(9), random1, "DLS-9-9");
		eightPuzzleSearch(new DepthLimitedSearch(3), random1, "DLS-3-9");
		eightPuzzleSearch(new IterativeDeepeningSearch(), random1, "IDS-9");
		eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), random1, "UCS-G-9");
		eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), random1, "UCS-T-9");
		eightPuzzleSearch(new BreadthFirstSearch(new GraphSearch()), extreme, "BFS-G-30");
		//eightPuzzleSearch(new BreadthFirstSearch(new TreeSearch()), extreme, "BFS-T-30");
		System.out.printf("%15s|%11s|%11s|%11s|%11s|%11s\n", "BFS-T-30","---","---","---","---","---","---");
		eightPuzzleSearch(new DepthFirstSearch(new GraphSearch()), extreme, "DFS-G-30");
		//eightPuzzleSearch(new DepthFirstSearch(new TreeSearch()), extreme, "DFS-T-30");
		System.out.printf("%15s|%11s|%11s|%11s|%11s|%11s\n", "DFS-T-30","---","---","---","---","---","---");
		eightPuzzleSearch(new DepthLimitedSearch(9), extreme, "DLS-9-30");
		eightPuzzleSearch(new DepthLimitedSearch(3), extreme, "DLS-3-30");
		//eightPuzzleSearch(new IterativeDeepeningSearch(), extreme, "IDS-30");
		System.out.printf("%15s|%11s|%11s|%11s|%11s|%11s\n", "IDS-30","---","---","---","---","---","---");
		eightPuzzleSearch(new UniformCostSearch(new GraphSearch()), extreme, "UCS-G-30");
		//eightPuzzleSearch(new UniformCostSearch(new TreeSearch()), extreme, "UCS-T-30");
		System.out.printf("%15s|%11s|%11s|%11s|%11s|%11s\n", "UCS-T-30","---","---","---","---","---","---");
		
		
	}
	public static void eightPuzzleSearch(Search busqueda, EightPuzzleBoard estado, String problema) {
		try {
			long tiempo = System.currentTimeMillis();
			Problem problem = new Problem(estado, EightPuzzleFunctionFactory
					.getActionsFunction(), EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest());
			Search search = busqueda;
			SearchAgent agent = new SearchAgent(problem, search);
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
			System.out.printf("%15s|%11s|%11s|%11s|%11s|%11s\n", problema, depth,
					expandedNodes,queueSize,maxQueueSize,tiempo);
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	private static void eightPuzzleDLSDemo() {
		System.out.println("\nEightPuzzleDemo recursive DLS (9) -->");
		try {
			Problem problem = new Problem(boardWithThreeMoveSolution, EightPuzzleFunctionFactory
					.getActionsFunction(), EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest());
			Search search = new DepthLimitedSearch(9);
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void eightPuzzleIDLSDemo() {
		System.out.println("\nEightPuzzleDemo Iterative DLS -->");
		try {
			Problem problem = new Problem(random1, EightPuzzleFunctionFactory
					.getActionsFunction(), EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest());
			Search search = new IterativeDeepeningSearch();
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void eightPuzzleGreedyBestFirstDemo() {
		System.out
				.println("\nEightPuzzleDemo Greedy Best First Search (MisplacedTileHeursitic)-->");
		try {
			Problem problem = new Problem(boardWithThreeMoveSolution,
					EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(),
					new EightPuzzleGoalTest());
			Search search = new GreedyBestFirstSearch(new GraphSearch(),
					new MisplacedTilleHeuristicFunction());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void eightPuzzleGreedyBestFirstManhattanDemo() {
		System.out
				.println("\nEightPuzzleDemo Greedy Best First Search (ManhattanHeursitic)-->");
		try {
			Problem problem = new Problem(boardWithThreeMoveSolution,
					EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(),
					new EightPuzzleGoalTest());
			Search search = new GreedyBestFirstSearch(new GraphSearch(),
					new ManhattanHeuristicFunction());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void eightPuzzleAStarDemo() {
		System.out
				.println("\nEightPuzzleDemo AStar Search (MisplacedTileHeursitic)-->");
		try {
			Problem problem = new Problem(random1, EightPuzzleFunctionFactory
					.getActionsFunction(), EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest());
			Search search = new AStarSearch(new GraphSearch(),
					new MisplacedTilleHeuristicFunction());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void eightPuzzleSimulatedAnnealingDemo() {
		System.out.println("\nEightPuzzleDemo Simulated Annealing  Search -->");
		try {
			Problem problem = new Problem(random1, EightPuzzleFunctionFactory
					.getActionsFunction(), EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest());
			SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
					new ManhattanHeuristicFunction());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void eightPuzzleAStarManhattanDemo() {
		System.out
				.println("\nEightPuzzleDemo AStar Search (ManhattanHeursitic)-->");
		try {
			Problem problem = new Problem(random1, EightPuzzleFunctionFactory
					.getActionsFunction(), EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest());
			Search search = new AStarSearch(new GraphSearch(),
					new ManhattanHeuristicFunction());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

}