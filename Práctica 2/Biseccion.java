package aima.core.util.math;

public class Biseccion {
	private int depth;
	private int Nodos_generados;
	
	public void setDepth(int d) {
		depth = d;
	}
	public void setNodosGenerados(int n) {
		Nodos_generados = n;
	}
	public double f(double i) {
		return (i*(1-Math.pow(i,  depth)))/(1-i) - Nodos_generados;
	}
	public double biseccion(double a, double b, double c) {
		double m = a;
		while (b-a >= c) {
			m = (a+b)/2.0;
			if (f(m) == 0.0) {
				break;
			}
			else if (f(m)*f(a) < 0) {
				b = m;
			}
			else {
				a = m;
			}
		}
		return m;
	}
}