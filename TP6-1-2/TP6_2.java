package aima.gui.demo.search;


import aima.core.environment.Canibales.CanibalesBoard;
import aima.core.environment.Canibales.CanibalesFunctionFactory;
import aima.core.environment.Canibales.CanibalesGoalTest;
import aima.core.environment.Canibales.CanibalesHeuristicFunction;
import aima.core.environment.fifteenpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleBoard;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleFunctionFactory;
import aima.core.environment.fifteenpuzzle.FifteenPuzzleGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarEvaluationFunction;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.IterativeDeepeningAStar;
import aima.core.search.informed.RecursiveBestFirstSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;

/**
 * @author Ravi Mohan
 * 
 */

public class TP6_2 {
	public static void main(String[] args) {
		System.out.printf("%16s|%12s|%12s|%12s|%12s|%12s|%12s\n","Problema", "Profundidad", "Generad", "Expand", "Q.Size", "MaxQS","tiempo");
		
		CanibalesSearch(new DepthFirstSearch(new GraphSearch()), new CanibalesBoard(), "dfs.MC");
		CanibalesSearch(new AStarSearch(new GraphSearch(), new CanibalesHeuristicFunction()), new CanibalesBoard(), "A*.MC");
		CanibalesSearch(new BreadthFirstSearch(new GraphSearch()), new CanibalesBoard(), "BFS.MC");
		CanibalesSearch(new RecursiveBestFirstSearch(new AStarEvaluationFunction(new CanibalesHeuristicFunction())), new CanibalesBoard(), "RBFS.MC");
		CanibalesSearch(new IterativeDeepeningAStar(new CanibalesHeuristicFunction()), new CanibalesBoard(), "IDA*.MC");
		FifteenPuzzleSearch(new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction()), new FifteenPuzzleBoard(), "A*-15p");
		FifteenPuzzleSearch(new RecursiveBestFirstSearch(new AStarEvaluationFunction(new ManhattanHeuristicFunction())), new FifteenPuzzleBoard(), "RBFS-15p");
		FifteenPuzzleSearch(new IterativeDeepeningAStar(new ManhattanHeuristicFunction()),new FifteenPuzzleBoard(), "IDA*-15p");
	}
	private static void CanibalesSearch(Search s, CanibalesBoard estado, String Problema){
		int depth,expandedNodes,queueSize,maxQueueSize,nodesGenerated;
		try {	
			long tiempo =  System.currentTimeMillis();
			Problem problem = new Problem(estado, CanibalesFunctionFactory.getActionsFunction(), CanibalesFunctionFactory.getResultFunction(), new CanibalesGoalTest());
			SearchAgent agent = new SearchAgent(problem,s);
			String pathcostM =agent.getInstrumentation().getProperty("pathCost");
			if (pathcostM!=null) {
				depth = (int)Float.parseFloat(pathcostM);
			}
			else {
				depth = 0;
			}
			
			if (agent.getInstrumentation().getProperty("nodesGenerated") != null) {
				nodesGenerated= (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesGenerated"));
			}
			else {
				nodesGenerated = 0;
			}
			
			if (agent.getInstrumentation().getProperty("nodesExpanded")==null) {
				expandedNodes= 0;
			}
			else {
				expandedNodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
			}
			
			if (agent.getInstrumentation().getProperty("queueSize")==null) {
				queueSize=0;
			}
			
			else {
				queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize"));
			}
			
			if (agent.getInstrumentation().getProperty("maxQueueSize")==null) {
				maxQueueSize= 0;
			}
			else {
				maxQueueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
			}
			
			tiempo = System.currentTimeMillis()-tiempo;
			System.out.printf("%16s|%12s|%12s|%12s|%12s|%12s|%12s\n", Problema, depth, nodesGenerated, expandedNodes, queueSize, maxQueueSize, tiempo);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void FifteenPuzzleSearch(Search s, FifteenPuzzleBoard estado, String Problema){
		int depth,expandedNodes,queueSize,maxQueueSize,nodesGenerated;
		try {	
			long tiempo =  System.currentTimeMillis();
			Problem problem = new Problem(estado, FifteenPuzzleFunctionFactory.getActionsFunction(), FifteenPuzzleFunctionFactory.getResultFunction(), new FifteenPuzzleGoalTest());
			SearchAgent agent = new SearchAgent(problem,s);
			String pathcostM =agent.getInstrumentation().getProperty("pathCost");
			
			if (pathcostM!=null) {
				depth = (int)Float.parseFloat(pathcostM);
			}
			else {
				depth = 0;
			}
			
			if (agent.getInstrumentation().getProperty("nodesGenerated") != null) {
				nodesGenerated= (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesGenerated"));
			}
			else {
				nodesGenerated = 0;
			}
			
			if (agent.getInstrumentation().getProperty("nodesExpanded")==null) {
				expandedNodes= 0;
			}
			else {
				expandedNodes = (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
			}
			
			if (agent.getInstrumentation().getProperty("queueSize")==null) {
				queueSize=0;
			}
			else {
				queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize"));
			}
			if (agent.getInstrumentation().getProperty("maxQueueSize")==null) {
				maxQueueSize= 0;
			}
			else {
				maxQueueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
			}
			tiempo = System.currentTimeMillis()-tiempo;
			System.out.printf("%16s|%12s|%12s|%12s|%12s|%12s|%12s\n", Problema, depth, nodesGenerated, expandedNodes, queueSize, maxQueueSize, tiempo);
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}