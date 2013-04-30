package ar.edu.unlam.math;

import ar.edu.unlam.math.exception.MatrizOperationException;

public class TrianguladorGauss implements Triangulador {

	@Override
	public MatrizMath triangular(MatrizMath m) throws MatrizOperationException {
		// Verifico si la matriz es cuadrada
		int[] size = m.getSize();
		if (size[0] != size[1])
			throw new MatrizOperationException("No es posible triangular una matriz que no sea cuadrada");

		// Clono la matriz para trabajar sobre una copia
		MatrizMath r = m.clone();

		// Busco un numero distinto de cero en la 1º posicion
		int filaSinCeroInicial = 0;
		while (r.get(filaSinCeroInicial, 0) == 0 && filaSinCeroInicial + 1 < size[0])
			filaSinCeroInicial++;

		// Si encontre un numero distinto de cero en otra fila, las intercambio
		if (filaSinCeroInicial != 0)
			r.intercambiarFilas(0, filaSinCeroInicial);

		// Busco ceros alrededor de la j posicion
		for (int j = 0; j < size[1]; j++) {
			for (int i = 0; i < size[0]; i++) {
				if (i != j) {
					double a = r.get(j, j);
					double b = r.get(i, j);
					
					if(a != 0) {
						// Busco que se anule la suma b = b + (-1) * a * b/a, se simplifica a
						// y queda b = b - b, que da como resultado cero
						r.sumarFilas(i, j, -1 * b/a); 
					}
				}
			}
		}

		return r;
	}
}
