package aima.core.search.informed;

import java.util.ArrayList;
import java.util.List;

import aima.core.agent.Action;
import aima.core.search.framework.Node;
import aima.core.search.framework.EvaluationFunction;
import aima.core.search.framework.HeuristicFunction;
import aima.core.search.framework.Metrics;
import aima.core.search.framework.NodeExpander;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchUtils;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): Figure 3.18, page
 * 89.<br>
 * <br>
 * 
 * <pre>
 * function ITERATIVE-DEEPENING-SEARCH(problem) returns a solution, or failure
 *   for depth = 0 to infinity  do
 *     result &lt;- DEPTH-LIMITED-SEARCH(problem, depth)
 *     if result != cutoff then return result
 * </pre>
 * 
 * Figure 3.18 The iterative deepening search algorithm, which repeatedly
 * applies depth-limited search with increasing limits. It terminates when a
 * solution is found or if the depth- limited search returns failure, meaning
 * that no solution exists.
 * 
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class IterativeDeepeningAStar extends NodeExpander implements Search {
	private static String PATH_COST = "pathCost";
	private final EvaluationFunction Evaluar;
	private Node actual;
	private final int infinity = Integer.MAX_VALUE;

	private final Metrics iterationMetrics;

	public IterativeDeepeningAStar(HeuristicFunction h) {
		this.actual = new Node(null);
		iterationMetrics = new Metrics();
		Evaluar = new AStarEvaluationFunction(h);
	}

	@Override
	public List<Action> search(Problem p) throws Exception {
		
		iterationMetrics.set(METRIC_NODES_EXPANDED, 0);
		iterationMetrics.set(METRIC_NODES_GENERATED, 0);
		iterationMetrics.set(PATH_COST, 0);
		
		List<Action> actions = new ArrayList<Action>();
		
		clearInstrumentation();
		
		Node NodoInicial = new Node(p.getInitialState());
		actual = NodoInicial;	//Inicializo el nodo al mismo valor que el inicial para empezar la búsqueda

		for (int i = 0; i <= infinity; i++) {
			
			Result resultado = AStar(NodoInicial, p, actual);
			actual = resultado.getMin();
			if (resultado.getStatus() == SearchResult.Status.SOLUTION_FOUND) {
				Node aux = resultado.getSolution();
				actions = SearchUtils.actionsFromNodes(aux.getPathFromRoot());
				actions = resultado.getActions();
				setPathCost(aux.getPathCost());
				break;

			}
		}
		iterationMetrics.set(PATH_COST, actions.size());
		iterationMetrics.set(METRIC_NODES_EXPANDED, metrics.getInt(METRIC_NODES_EXPANDED));
		iterationMetrics.set(METRIC_NODES_GENERATED, metrics.getInt(METRIC_NODES_GENERATED));
		return actions;
	}

	@Override
	public Metrics getMetrics() {
		return iterationMetrics;
	}
	
	public void setPathCost(Double pathCost) {
		iterationMetrics.set(PATH_COST, pathCost);
	}
	
	public interface SearchResult {
	    interface Status { 
	    	String FAILURE = "FAILURE";
	    	String SOLUTION_FOUND = "SOLUTION_FOUND";
	    }
	}

	
	private class Result {
	    private String status;
	    private Node min;
	    private Node solution;
	    private List<Action> actions;

	    public Result(String status, Node min, Node solution, List<Action> actions) {
	        this.status = status;
	        this.min = min;
	        this.solution = solution;
	        this.actions = actions;
	    }

	    public String getStatus() {
	        return this.status;
	    }

	    public Node getMin() {
	        return this.min;
	    }

	    public Node getSolution() {
	        return this.solution;
	    }

	    public List<Action> getActions() {
	        return this.actions;
	    }
	}

	private Result AStar(Node NodoInicial, Problem p, Node actual) {
	    if (Evaluar.f(NodoInicial) > Evaluar.f(actual)) {
	        return new Result(SearchResult.Status.FAILURE, NodoInicial, NodoInicial, SearchUtils.actionsFromNodes(NodoInicial.getPathFromRoot()));
	    }
	    if (SearchUtils.isGoalState(p, NodoInicial)) {
	        setPathCost(NodoInicial.getPathCost());
	        return new Result(SearchResult.Status.SOLUTION_FOUND, NodoInicial, NodoInicial, SearchUtils.actionsFromNodes(NodoInicial.getPathFromRoot()));
	    }
	    Node minimo = null;
	    List<Node> hijos = expandNode(NodoInicial, p);
	    for (Node padre : hijos) {
	        Result resultado = AStar(padre, p, actual);
	        
	        if (resultado.getStatus().equals(SearchResult.Status.SOLUTION_FOUND)) {
	            return new Result(SearchResult.Status.SOLUTION_FOUND, resultado.getMin(), resultado.getSolution(), resultado.getActions());
	        }
	        if (Minimo(resultado.getMin(), minimo, actual)) {
	        	minimo = resultado.getMin();
	        }
	    }
	    return new Result(SearchResult.Status.FAILURE, minimo, NodoInicial, SearchUtils.actionsFromNodes(NodoInicial.getPathFromRoot()));
	}

	private boolean Minimo(Node NodoInicial, Node Minimo, Node NodoFinal) {
	    return (Minimo == null || (Evaluar.f(NodoInicial) < Evaluar.f(Minimo)
	            && Evaluar.f(NodoInicial) > Evaluar.f(NodoFinal)));
	}
}