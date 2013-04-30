package ar.edu.unlam.math;

import ar.edu.unlam.math.MatrizMath;
import ar.edu.unlam.math.exception.MatrizOperationException;

public interface Triangulador {
	/**
	 * Triangula una matriz, es decir que solamente habrán números en la diagonal principal 
	 * 
	 * @return Matriz triangulada
	 */
	public MatrizMath triangular(MatrizMath m) throws MatrizOperationException;
}
