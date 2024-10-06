package aima.core.environment.Canibales;

import java.util.Arrays;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;


/**
 * @author Ravi Mohan
 * @author R. Lunde
 */
public class CanibalesBoard {

	public static Action M1CAN = new DynamicAction("M1C");

	public static Action M2CAN = new DynamicAction("M2C");

	public static Action M1MIS = new DynamicAction("M1M");

	public static Action M2MIS = new DynamicAction("M2M");
	
	public static Action M1CAN1MIS = new DynamicAction("M1C1M");

	private char[] state;

	//
	// PUBLIC METHODS
	//

	public CanibalesBoard() {
		state = new char[] { '3','3','i','0','0' };
	}

	public CanibalesBoard(char[] state) {
		this.state = new char[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public CanibalesBoard(CanibalesBoard copyBoard) {
		this(copyBoard.getState()); //Copia el estado
	}

	public char[] getState() {
		return state;
	}

	public void M1CAN() {
		char balsa = obtenerPosicionBalsa();
		int Ci = obtenerCanibales('i');
		int Cf = obtenerCanibales('f');
		if (balsa == 'i') {
			modificarCanibales(Ci-1, 'i');
			modificarCanibales(Cf+1, 'f');
			modificarPosBalsa('f');
		}
		else if (balsa == 'f') {
			modificarCanibales(Ci+1, 'i');
			modificarCanibales(Cf-1, 'f');
			modificarPosBalsa('i');
		}
	}
	
	
	public void M2CAN() {
		char balsa = obtenerPosicionBalsa();
		int Ci = obtenerCanibales('i');
		int Cf = obtenerCanibales('f');
		if (balsa == 'i') {
			modificarCanibales(Ci-2, 'i');
			modificarCanibales(Cf+2, 'f');
			modificarPosBalsa('f');
		}
		else if (balsa == 'f') {
			modificarCanibales(Ci+2, 'i');
			modificarCanibales(Cf-2, 'f');
			modificarPosBalsa('i');
		}
	}
	
	public void M1MIS() {
		char balsa = obtenerPosicionBalsa();
		int mi = obtenerMisioneros('i');
		int mf = obtenerMisioneros('f');
		if (balsa == 'i') {
			modificarMisioneros(mi-1, 'i');
			modificarMisioneros(mf+1, 'f');
			modificarPosBalsa('f');
		}
		else if (balsa == 'f') {
			modificarMisioneros(mi+1, 'i');
			modificarMisioneros(mf-1, 'f');
			modificarPosBalsa('i');
		}
	}
	
	public void M2MIS() {
		char balsa = obtenerPosicionBalsa();
		int mi = obtenerMisioneros('i');
		int mf = obtenerMisioneros('f');
		if (balsa == 'i') {
			modificarMisioneros(mi-2, 'i');
			modificarMisioneros(mf+2, 'f');
			modificarPosBalsa('f');
		}
		else if (balsa == 'f') {
			modificarMisioneros(mi+2, 'i');
			modificarMisioneros(mf-2, 'f');
			modificarPosBalsa('i');
		}
	}
	
	
	public void M1CAN1MIS() {
		char balsa = obtenerPosicionBalsa();
		int mi = obtenerMisioneros('i');
		int mf = obtenerMisioneros('f');
		int Ci = obtenerCanibales('i');
		int Cf = obtenerCanibales('f');
		if (balsa == 'i') {
			modificarMisioneros(mi-1, 'i');
			modificarMisioneros(mf+1, 'f');
			modificarCanibales(Ci-1, 'i');
			modificarCanibales(Cf+1, 'f');
			modificarPosBalsa('f');
		}
		else if (balsa == 'f') {
			modificarMisioneros(mi+1, 'i');
			modificarMisioneros(mf-1, 'f');
			modificarCanibales(Ci+1, 'i');
			modificarCanibales(Cf-1, 'f');
			modificarPosBalsa('i');
		}
	}
	
	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		char balsa = obtenerPosicionBalsa();
		int mi = obtenerMisioneros('i');
		int mf = obtenerMisioneros('f');
		int Ci = obtenerCanibales('i');
		int Cf = obtenerCanibales('f');
		if (where.equals(M1CAN)) {
			if (balsa == 'i') {
				retVal = (Ci >= 1  && (mf >= Cf + 1 || mf == 0));
			}
			else if (balsa == 'f') {
				retVal = (Cf >= 1 && (mi >= Ci + 1 || mi == 0));
			}
		}
		else if (where.equals(M2CAN)) {
			if (balsa == 'i') {
				retVal = (Ci >= 2  && (mf >= Cf + 2 || mf == 0));
			}
			else if (balsa == 'f') {
				retVal = (Cf >= 2 && (mi >= Ci + 2 || mi == 0));
			}
		}
		else if (where.equals(M1MIS)) {
			if (balsa == 'i') {
				retVal = (mi >= 1  && ((mi >= Ci + 1 || mi == 1) && mf >= Cf - 1));
			}
			else if (balsa == 'f') {
				retVal = (mf >= 1  && ((mf >= Cf + 1 || mf == 1) && mi >= Ci - 1));
			}
		}
		else if (where.equals(M2MIS)) {
			if (balsa == 'i') {
				retVal = (mi >= 2  && ((mi >= Ci + 2 || mi == 2) && mf >= Cf - 2));
			}
			else if (balsa == 'f') {
				retVal = (mf >= 2  && ((mf >= Cf + 2 || mf == 2) && mi >= Ci - 2));
			}
		}
		else if (where.equals(M1CAN1MIS)) {
			if (balsa == 'i') {
				retVal = (mi >= 1  && mf >= Cf && Ci >= 1);
			}
			else if (balsa == 'f') {
				retVal = (mf >= 1  && mi >= Ci && Cf >= 1);
			}
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
		CanibalesBoard other = (CanibalesBoard) obj;
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
				"RIBERA-IZQ " + escribirMisioneros(obtenerMisioneros('i')) + " " + escribirCanibales(obtenerCanibales('i')) + " " +
				escribirBalsa(obtenerPosicionBalsa()) + " " + escribirMisioneros(obtenerMisioneros('f')) + " " + escribirCanibales(obtenerCanibales('f')) + " RIBERA-DCH";
				return retVal;
	}

	//
	// PRIVATE METHODS
	//
	
	private int obtenerMisioneros(char ribera) {
		int retVal = 0;
		if (ribera == 'i') {
			retVal = Character.getNumericValue(state[0]);
		}
		if (ribera == 'f') {
			retVal = Character.getNumericValue(state[3]);
		}
		return retVal;
	}
	
	
	private int obtenerCanibales(char ribera) {
		int retVal = 0;
		if (ribera == 'i') {
			retVal = Character.getNumericValue(state[1]);
		}
		if (ribera == 'f') {
			retVal = Character.getNumericValue(state[4]);
		}
		return retVal;
	}
	
	private char obtenerPosicionBalsa() {
		return state [2];
	}
	
	private void modificarMisioneros(int numero, char ribera) {
		if (ribera == 'i') {
			state[0] = Character.forDigit(numero, 10);
		}
		else if (ribera == 'f') {
			state[3] = Character.forDigit(numero, 10);
		}
	}
	
	private void modificarCanibales(int numero, char ribera) {
		if (ribera == 'i') {
			state[1] = Character.forDigit(numero, 10);
		}
		else if (ribera == 'f') {
			state[4] = Character.forDigit(numero, 10);
		}
	}
	
	private void modificarPosBalsa(char posicion) {
		state [2] = posicion;
	}
	
	private String escribirMisioneros (int numero) {
		String retVal = "     ";
		if (numero == 3) {	
			retVal = "M M M";
		}
		else if (numero == 2) {	
			retVal = "  M M";
		}
		else if (numero == 1) {	
			retVal = "    M";
		}
		return retVal;
	}
	
	private String escribirCanibales (int numero) {
		String retVal = "     ";
		if (numero == 3) {	
			retVal = "C C C";
		}
		else if (numero == 2) {	
			retVal = "  C C";
		}
		else if (numero == 1) {	
			retVal = "    C";
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

	