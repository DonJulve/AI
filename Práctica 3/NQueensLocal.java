package aima.gui.demo.search;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.nqueens.AttackingPairsHeuristic;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFitnessFunction;
import aima.core.environment.nqueens.NQueensFunctionFactory;
import aima.core.environment.nqueens.NQueensGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.Individual;
import aima.core.search.local.Scheduler;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;

/**
 * @author Ravi Mohan
 * 
 */

public class NQueensLocal {

	private static final int _boardSize = 8;
	
	public static void main(String[] args) {
		nQueensHillClimbingSearch_Statistics(10000);
		nQueensRandomRestartHillClimbing();
		nQueensSimulatedAnnealing_Statistics(1000);
		nQueensHillSimulatedAnnealingRestart();
		nQueensGeneticAlgorithmSearch();
	}
	
	private static void nQueensHillClimbingSearch_Statistics(int numExperiments) {
		System.out.println("\nNQueensDemo HillClimbing con " + numExperiments + " estados iniciales diferentes -->");
		int exitos = 0, fallos = 0, costeExitos = 0, costeFallos = 0;
		try {
			for (int i = 0; i < numExperiments; i++) {
				NQueensBoard board = new NQueensBoard(_boardSize);
				board.random();
				Problem problem = new Problem(board, NQueensFunctionFactory.getCActionsFunction(), NQueensFunctionFactory.getResultFunction(), new NQueensGoalTest());
				HillClimbingSearch search = new HillClimbingSearch(new AttackingPairsHeuristic());
				SearchAgent agent = new SearchAgent(problem, search);
				if (search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					exitos++;
					costeExitos += agent.getActions().size();
				}
				else {
					fallos++;
					costeFallos += agent.getActions().size();
				}
			}
			System.out.printf("Fallos: %.2f%%\n", (double) fallos/numExperiments*100);
			System.out.printf("Coste medio fallos: %.2f\n", (double) costeFallos/fallos);
			System.out.printf("Éxitos: %.2f%%\n", (double) exitos/numExperiments*100);
			System.out.printf("Coste medio éxitos: %.2f\n", (double) costeExitos/exitos);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void nQueensRandomRestartHillClimbing() {
		System.out.println("\nNQueensDemo HillClimbing Restart -->");
		int intentos = 0, costeExito = 0, costeFallos = 0;
		boolean reset = true;
		try {
			while (reset) {
				NQueensBoard board = new NQueensBoard(_boardSize);
				board.random();
				Problem problem = new Problem(board, NQueensFunctionFactory.getCActionsFunction(), NQueensFunctionFactory.getResultFunction(), new NQueensGoalTest());
				HillClimbingSearch search = new HillClimbingSearch(new AttackingPairsHeuristic());
				SearchAgent agent = new SearchAgent(problem, search);
				intentos ++;
				if(search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					reset = false;
					costeExito = agent.getActions().size();
					System.out.println("Search Outcome = " + search.getOutcome().toString());
					System.out.println("Final State=\n" + search.getLastSearchState());
					System.out.println("Número de intentos = " + intentos);
					System.out.printf("Coste medio fallos: %.2f\n", (double) costeFallos/(intentos - 1));
					System.out.println("Coste éxito: " + (costeFallos + costeExito));
					System.out.println("Coste medio éxito: " + costeExito);
				}
				else {
					costeFallos += agent.getActions().size();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void nQueensSimulatedAnnealing_Statistics(int numExperiments) {
		System.out.println("\nNQueensDemo Simulated Annealing con " + numExperiments + " estados iniciales diferentes -->");
		int exitos = 0, fallos = 0, costeExitos = 0, costeFallos = 0;
		try {
			Scheduler s = new Scheduler(10, 0.1, 500);
			System.out.println("Parámetros Scheduler: " + s.toString());
			for (int i = 0; i < numExperiments; i++) {
				NQueensBoard board = new NQueensBoard(_boardSize);
				board.random();
				Problem problem = new Problem(board, NQueensFunctionFactory.getCActionsFunction(), NQueensFunctionFactory.getResultFunction(), new NQueensGoalTest());
				SimulatedAnnealingSearch  search = new SimulatedAnnealingSearch (new AttackingPairsHeuristic(), s);
				SearchAgent agent = new SearchAgent(problem, search);
				if (search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					exitos++;
					costeExitos += agent.getActions().size();
				}
				else {
					fallos++;
					costeFallos += agent.getActions().size();
				}
			}
			System.out.printf("Fallos: %.2f%%\n", (double) fallos/numExperiments*100);
			System.out.printf("Coste medio fallos: %.2f\n", (double) costeFallos/fallos);
			System.out.printf("Éxitos: %.2f%%\n", (double) exitos/numExperiments*100);
			System.out.printf("Coste medio éxitos: %.2f\n", (double) costeExitos/exitos);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void nQueensHillSimulatedAnnealingRestart() {
		System.out.println("\nNQueensDemo Simulated Annealing Restart -->");
		int intentos = 0, costeExito = 0, costeFallos = 0;
		boolean reset = true;
		try {
			Scheduler s = new Scheduler(10, 0.1, 500);
			while (reset) {
				NQueensBoard board = new NQueensBoard(_boardSize);
				board.random();
				Problem problem = new Problem(board, NQueensFunctionFactory.getCActionsFunction(), NQueensFunctionFactory.getResultFunction(), new NQueensGoalTest());
				SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(new AttackingPairsHeuristic(), s);
				SearchAgent agent = new SearchAgent(problem, search);
				intentos ++;
				if(search.getOutcome().toString().contentEquals("SOLUTION_FOUND")) {
					reset = false;
					costeExito = agent.getActions().size();
					System.out.println("Search Outcome = " + search.getOutcome().toString());
					System.out.println("Final State=\n" + search.getLastSearchState());
					System.out.println("Número de intentos = " + intentos);
					System.out.println("Fallos: " + (intentos - 1));
					System.out.println("Coste éxito: " + (costeFallos + costeExito));
				}
				else {
					costeFallos += agent.getActions().size();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void nQueensGeneticAlgorithmSearch() {
		System.out.println("\nNQueensDemo GeneticAlgorithm  -->");
		try {
			NQueensFitnessFunction fitnessFunction = new NQueensFitnessFunction();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(fitnessFunction
						.generateRandomIndividual(_boardSize));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(
					_boardSize,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(_boardSize),
					0.15);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(
					population, fitnessFunction, fitnessFunction, 1000L);
			
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction,fitnessFunction, 0L);
			System.out.println("Parámetros iniciales:\tPoblación: " + ga.getPopulationSize() + ", " + "Probabilidad mutación: 0.15");
			System.out.println("Mejor individuo=\n" + fitnessFunction.getBoardForIndividual(bestIndividual) + "\n");
			System.out.println("Tamaño tablero = " + _boardSize);
			System.out.println("Fitness = " + fitnessFunction.getValue(bestIndividual));
			System.out.println("Es objetivo = " + fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Tamaño de población = " + ga.getPopulationSize());
			System.out.println("Iteraciones = " + ga.getIterations());
			System.out.println("Tiempo = " + ga.getTimeInMilliseconds() + "ms.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}