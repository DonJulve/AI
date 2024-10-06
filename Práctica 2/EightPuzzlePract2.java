package aima.gui.demo.search;

import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest2;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction2;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction2;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.util.math.Biseccion;

/**
 * @author Ravi Mohan
 * 
 */

public class EightPuzzlePract2 {
	public static void main(String[] args) {
		EightPuzzleBoard board, goalboard;
		int Nodos_bfs = 0, Nodos_ids = 0, Nodos_misplaced = 0, Nodos_manhattan = 0;
		double bfs_b = 0, ids_b = 0, misplaced_b = 0, manhattan_b = 0;
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("||   ||           Nodos Generados           ||                 b*               ||");
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("||  d||  BFS  |  IDS  |  A*h(1)  |  A*h(2)  ||  BFS  |  IDS  |  A*h(1) | A*h(2) ||");
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------------");
		
		for (int d = 2; d <= 24; d++) {
			Nodos_bfs = 0;
			Nodos_ids = 0;
			Nodos_misplaced = 0;
			Nodos_manhattan = 0;
			for (int i = 0; i <= 100; i++) {
				board = GenerateInitialEightPuzzleBoard.randomIni();
				goalboard = GenerateInitialEightPuzzleBoard.random(d, board);
				EightPuzzleGoalTest2 goal = new EightPuzzleGoalTest2();
				goal.setGoalState(goalboard);
				Problem problem = new Problem(board, EightPuzzleFunctionFactory.getActionsFunction(), EightPuzzleFunctionFactory.getResultFunction(), goal);
				boolean depthOk = false;
				while (!depthOk) {
					try {
						SearchAgent agent = new SearchAgent(problem, new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction2()));
						if (d == ((int)Float.parseFloat(agent.getInstrumentation().getProperty("pathCost")))) {
							depthOk = true;
						}
						else {
							goalboard = GenerateInitialEightPuzzleBoard.random(d, board);
							goal.setGoalState(goalboard);
							problem = new Problem(board, EightPuzzleFunctionFactory.getActionsFunction(), EightPuzzleFunctionFactory.getResultFunction(), goal);
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				Nodos_bfs += eightPuzzleSearch(problem, new BreadthFirstSearch(new GraphSearch()));
				if (d <= 10) {
					Nodos_ids += eightPuzzleSearch(problem, new IterativeDeepeningSearch());
				}
				Nodos_misplaced += eightPuzzleSearch(problem, new AStarSearch(new GraphSearch(), new MisplacedTilleHeuristicFunction2()));
				Nodos_manhattan += eightPuzzleSearch(problem, new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction2()));
			}
			Biseccion b = new Biseccion();
			b.setDepth(d);
			b.setNodosGenerados(Nodos_bfs/100);
			bfs_b = b.biseccion(1.00000001, 4.0, 1E-10);
			if (d <= 10) {
				b.setNodosGenerados(Nodos_ids/100);
				ids_b = b.biseccion(1.00000001, 4.0, 1E-10);
			}
			b.setNodosGenerados(Nodos_misplaced/100);
			misplaced_b = b.biseccion(1.00000001, 4.0, 1E-10);
			b.setNodosGenerados(Nodos_manhattan/100);
			manhattan_b = b.biseccion(1.00000001, 4.0, 1E-10);
			if (d <= 10) {
				System.out.printf(" %2d ||  %5d | %5d | %8d | %8d || %5.2f | %5.2f | %7.2f | %6.2f ||\n", d, Nodos_bfs/100, Nodos_ids/100, Nodos_misplaced/100, Nodos_manhattan/100, bfs_b, ids_b, misplaced_b, manhattan_b);
			}
			else {
				System.out.printf(" %2d || %6d | ----- | %8d | %8d || %5.2f | ----- | %7.2f | %6.2f ||\n", d, Nodos_bfs/100, Nodos_misplaced/100, Nodos_manhattan/100, bfs_b, misplaced_b, manhattan_b);
			}
		}
	}
	private static int eightPuzzleSearch(Problem problem, Search busqueda) {
		try {
			SearchAgent agent = new SearchAgent(problem, busqueda);
			int nodos = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesGenerated"));
			return nodos;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}