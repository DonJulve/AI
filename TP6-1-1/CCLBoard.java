package aima.core.environment.CabraLoboCol;

import java.util.Arrays;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;


/**
 * @author Ravi Mohan
 * @author R. Lunde
 */
public class CCLBoard {

	public static Action MLOBO = new DynamicAction("MLOBO");

	public static Action MCABRA = new DynamicAction("MCABRA");

	public static Action MCOL = new DynamicAction("MCOL");
	
	public static Action MSOLO = new DynamicAction("MSOLO");

	private char[] state;

	//
	// PUBLIC METHODS
	//

	public CCLBoard() {
		state = new char[] { '1','1','1','i','0','0','0' };
	}

	public CCLBoard(char[] state) {
		this.state = new char[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public CCLBoard(CCLBoard copyBoard) {
		this(copyBoard.getState()); //Copia el estado
	}

	public char[] getState() {
		return state;
	}

	public void MLOBO() {
		char balsa = obtenerPosicionBalsa();
		int Li = obtenerLobo('i');
		int Lf = obtenerLobo('f');
		if (balsa == 'i') {
			modificarLobo(Li-1, 'i');
			modificarLobo(Lf+1, 'f');
			modificarPosBalsa('f');
		}
		else if (balsa == 'f') {
			modificarLobo(Li+1, 'i');
			modificarLobo(Lf-1, 'f');
			modificarPosBalsa('i');
		}
	}
	
	
	public void MCABRA() {
		char balsa = obtenerPosicionBalsa();
		int CAi = obtenerCabra('i');
		int CAf = obtenerCabra('f');
		if (balsa == 'i') {
			modificarCabra(CAi-1, 'i');
			modificarCabra(CAf+1, 'f');
			modificarPosBalsa('f');
		}
		else if (balsa == 'f') {
			modificarCabra(CAi+1, 'i');
			modificarCabra(CAf-1, 'f');
			modificarPosBalsa('i');
		}
	}
	
	public void MCOL() {
		char balsa = obtenerPosicionBalsa();
		int Ci = obtenerCol('i');
		int Cf = obtenerCol('f');
		if (balsa == 'i') {
			modificarCol(Ci-1, 'i');
			modificarCol(Cf+1, 'f');
			modificarPosBalsa('f');
		}
		else if (balsa == 'f') {
			modificarCol(Ci+1, 'i');
			modificarCol(Cf-1, 'f');
			modificarPosBalsa('i');
		}
	}
	public void MSOLO() {
		char balsa = obtenerPosicionBalsa();
		if (balsa == 'i') {
			modificarPosBalsa('f');
		}
		else if (balsa == 'f') {
			modificarPosBalsa('i');
		}
	}
	
	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		char balsa = obtenerPosicionBalsa();
		int Li = obtenerLobo('i');
		int Lf = obtenerLobo('f');
		int CAi = obtenerCabra('i');
		int CAf = obtenerCabra('f');
		int Ci = obtenerCol('i');
		int Cf = obtenerCol('f');
		if (where.equals(MLOBO)) {
			if (balsa == 'i') {
				retVal = (Li == 1);
			}
			else if (balsa == 'f') {
				retVal = (Lf == 1);
			}
		}
		else if (where.equals(MCABRA)) {
			if (balsa == 'i') {
				retVal = (CAi == 1);
			}
			else if (balsa == 'f') {
				retVal = (CAf == 1);
			}
		}
		else if (where.equals(MCOL)) {
			if (balsa == 'i') {
				retVal = (Ci == 1);
			}
			else if (balsa == 'f') {
				retVal = (Cf == 1);
			}
		}
		else if (where.equals(MSOLO)) {
			if (balsa == 'i') {
				retVal = ((Li == 0 && CAi == 1 && Ci == 0) || (Li == 1 && CAi == 0 && Ci == 1));
			}
			else if (balsa == 'f') {
				retVal = ((Lf == 0 && CAf == 1 && Cf == 0) || (Lf == 1 && CAf == 0 && Cf == 1));			}
		}
		return retVal;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
				return true;
		if (obj == null)
				return false;
		if (getClass() != obj.getClass())
				return false;
		CCLBoard other = (CCLBoard) obj;
				return Arrays.equals(state, other.state);
	}
	
	 @Override
	 public int hashCode() {
		 final int prime = 31;
		 int result = 1;
		 result = prime * result + Arrays.hashCode(state);
		 return result;
	 }
	
	

	@Override
	public String toString() {
		String retVal =
				"RIBERA-IZQ " + escribirLobo(obtenerLobo('i')) + " " + escribirCabra(obtenerCabra('i')) + " " + escribirCol(obtenerCol('i')) + " " +
				escribirBalsa(obtenerPosicionBalsa()) + " " + escribirLobo(obtenerLobo('f')) + " " + escribirCabra(obtenerCabra('f')) + " " + escribirCol(obtenerCol('f')) + " " 
				+ " RIBERA-DCH";
				return retVal;
	}

	//
	// PRIVATE METHODS
	//
	
	private int obtenerLobo(char ribera) {
		int retVal = 0;
		if (ribera == 'i') {
			retVal = Character.getNumericValue(state[0]);
		}
		if (ribera == 'f') {
			retVal = Character.getNumericValue(state[4]);
		}
		return retVal;
	}
	
	
	private int obtenerCabra(char ribera) {
		int retVal = 0;
		if (ribera == 'i') {
			retVal = Character.getNumericValue(state[1]);
		}
		if (ribera == 'f') {
			retVal = Character.getNumericValue(state[5]);
		}
		return retVal;
	}
	
	private int obtenerCol(char ribera) {
		int retVal = 0;
		if (ribera == 'i') {
			retVal = Character.getNumericValue(state[2]);
		}
		if (ribera == 'f') {
			retVal = Character.getNumericValue(state[6]);
		}
		return retVal;
	}
	
	private char obtenerPosicionBalsa() {
		return state [3];
	}
	
	private void modificarLobo(int numero, char ribera) {
		if (ribera == 'i') {
			state[0] = Character.forDigit(numero, 10);
		}
		else if (ribera == 'f') {
			state[4] = Character.forDigit(numero, 10);
		}
	}
	
	private void modificarCabra(int numero, char ribera) {
		if (ribera == 'i') {
			state[1] = Character.forDigit(numero, 10);
		}
		else if (ribera == 'f') {
			state[5] = Character.forDigit(numero, 10);
		}
	}
	private void modificarCol(int numero, char ribera) {
		if (ribera == 'i') {
			state[2] = Character.forDigit(numero, 10);
		}
		else if (ribera == 'f') {
			state[6] = Character.forDigit(numero, 10);
		}
	}
	
	private void modificarPosBalsa(char posicion) {
		state [3] = posicion;
	}
	
	private String escribirLobo (int numero) {
		String retVal = " ";
		if (numero == 1) {	
			retVal = "L";
		}
		return retVal;
	}
	
	private String escribirCabra (int numero) {
		String retVal = " ";
		if (numero == 1) {	
			retVal = "CA";
		}
		return retVal;
	}	
	
	private String escribirCol (int numero) {
		String retVal = " ";
		if (numero == 1) {	
			retVal = "C";
		}
		return retVal;
	}	
	private String escribirBalsa(char ribera) {
		String retVal = "";
		if (ribera == 'i') {
			retVal = "BOTE --RIO--     ";
		}
		else if (ribera == 'f') {
			retVal = "     --RIO-- BOTE";
		}
		return retVal;
	}

}

	