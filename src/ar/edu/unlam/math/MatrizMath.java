package ar.edu.unlam.math;

import java.util.Arrays;

import ar.edu.unlam.math.exception.MatrizOperationException;
import ar.edu.unlam.math.triangulador.InversorGaussJordan;
import ar.edu.unlam.math.triangulador.Triangulador;
import ar.edu.unlam.math.triangulador.TrianguladorGauss;

/**
 * Esta clase representa una Matriz matemática y permite realizar las
 * operaciones básicas con ellas
 * 
 * @author yoni
 * 
 */
public class MatrizMath {
	// Atributos ~
	// -----------------------------------------------------------------
	/**
	 * Almacena los valores numéricos de la matriz
	 */
	private double[][] matriz;
	/**
	 * Almacena el tamaño de la matriz, siendo el primer elemento el numero de
	 * filas y el segundo el numero de columnas
	 */
	private int[] size;

	// Constructores ~
	// -----------------------------------------------------------------
	public MatrizMath() {
	}

	public MatrizMath(double[][] matriz) {
		this.matriz = matriz;
		if (matriz.length > 0 && matriz[0] != null)
			this.size = new int[] { matriz.length, matriz[0].length };
	}
	
	// Getters and setters ~
	// -----------------------------------------------------------------
	protected double[][] getMatriz() {
		return this.matriz;
	}

	public void setMatriz(double[][] matriz) {
		this.matriz = matriz;
	}

	/**
	 * Devuelve el valor ubicado en las posiciones indicadas por i y j
	 * 
	 * @param i
	 *            numero de fila
	 * @param j
	 *            numero de columna
	 * @return valor almacenado en (i,j)
	 */
	public double get(int i, int j) {
		return matriz[i][j];
	}

	/**
	 * Devuelve el tamaño de la matriz
	 * 
	 * @return Devuelve el tamaño de la matriz, siendo el primer elemento el
	 *         numero de filas y el segundo el numero de columnas
	 */
	public int[] getDimension() {
		return size;
	}

	/**
	 * Obtiene la matriz identidad que le corresponde a una matriz de este
	 * tamaño
	 * 
	 * @return Matriz identidad que le corresponde a una matriz de este tamaño
	 * @exception MatrizOperationException
	 *                La matriz debe ser cuadrada
	 */
	public MatrizMath getIdentidad() throws MatrizOperationException {
		if (this.size[0] != this.size[1])
			throw new MatrizOperationException("Para calcular la identidad la matriz debe ser cuadrada");

		double[][] resultado = new double[size[0]][];
		for (int i = 0; i < size[0]; i++) {
			resultado[i] = new double[size[1]];
			for (int j = 0; j < size[1]; j++) {
				resultado[i][j] = i == j ? 1 : 0;
			}
		}
		return new MatrizMath(resultado);
	}

	// Metodos ~
	// -----------------------------------------------------------------
	public String toString() {
		int n = matriz.length;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			sb.append("(");
			for (int j = 0; j < matriz[i].length; j++)
				sb.append(matriz[i][j] + ", ");
			sb.replace(sb.length() - 2, sb.length(), ")\n");
		}
		return sb.toString();
	}

	public MatrizMath clone() {
		double[][] resultado = new double[size[0]][];
		for (int i = 0; i < size[0]; i++) {
			resultado[i] = new double[size[1]];
			for (int j = 0; j < size[1]; j++)
				resultado[i][j] = matriz[i][j];
		}
		return new MatrizMath(resultado);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(matriz);
		result = prime * result + Arrays.hashCode(size);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatrizMath other = (MatrizMath) obj;
		if (!Arrays.deepEquals(matriz, other.matriz))
			return false;
		if (!Arrays.equals(size, other.size))
			return false;
		return true;
	}

	/**
	 * Obtiene el determinante de la matriz
	 * 
	 * @return Valor del determinante
	 * @throws MatrizOperationException
	 *             La matriz debe ser cuadrada
	 */
	public double determinante() throws MatrizOperationException {
		Triangulador triangulador = new TrianguladorGauss();
		double[][] triangulada = triangulador.triangular(this).getMatriz();
		double resultado = 1;
		for (int i = 0; i < this.size[0]; i++)
			for (int j = 0; j < this.size[1]; j++)
				if (i == j)
					resultado *= triangulada[i][j];
		return resultado;
	}

	/**
	 * Obtiene la inversa de la matriz
	 * 
	 * @return Valor del determinante
	 * @throws MatrizOperationException
	 *             La matriz debe ser cuadrada
	 */
	public MatrizMath inversa() throws MatrizOperationException {
		Triangulador triangulador = new InversorGaussJordan();
		double[][] inversa = triangulador.triangular(this).getMatriz();
		return new MatrizMath(inversa);
	}

	/**
	 * Operacion elemental de intercambiar filas de una matriz (F1 <-> F2). Esta
	 * operación no modifica el valor de la matriz.
	 * 
	 * @param f1
	 *            fila origen
	 * @param f2
	 *            fila destinoeclipse-open:%E2%98%82=TP2/%5C/usr%5C/lib%5C/jvm%5C/java-1.7.0-openjdk-1.7.0.9.x86_64%5C/jre%5C/lib%5C/rt.jar%3Cjava.lang(System.class%E2%98%83System%5Eout
	 */
	public void intercambiarFilas(int f1, int f2) {
		double[] aux = matriz[f2];
		matriz[f2] = matriz[f1];
		matriz[f1] = aux;
	}

	/**
	 * Operacion elemental de multiplicar toda una fila de la matriz por un
	 * escalar (F1 = F1 * n). Esta operacion no modifica el valor de la matriz
	 * el valor de la matriz.
	 * 
	 * @param f1
	 *            numero de fila
	 * @param n
	 *            escalar a multiplicar
	 */
	public void multiplicarFilaPorEscalar(int f1, double n) {
		for (int j = 0; j < size[1]; j++)
			matriz[f1][j] *= n;
	}

	/**
	 * Operacion elemental de sumar una fila por el multiplo de otra (F1 = F1 +
	 * n*F2). Esta operacion no modifica el valor de la matriz el valor de la
	 * matriz.
	 * 
	 * @param f1
	 *            numero de fila destino
	 * @param f2
	 *            numero de fila a sumar
	 * @param n
	 *            multiplo de la fila a sumar
	 */
	public void sumarFilas(int f1, int f2, double n) {
		for (int j = 0; j < size[1]; j++)
			matriz[f1][j] += n * matriz[f2][j];
	}

	/**
	 * Operacion elemental de sumar una fila por otra (F1 = F1 + F2). Esta
	 * operacion no modifica el valor de la matriz el valor de la matriz.
	 * 
	 * @param f1
	 *            numero de fila destino
	 * @param f2
	 *            numero de fila a sumar
	 */
	public void sumarFilas(int f1, int f2) {
		this.sumarFilas(f1, f2, 1d);
	}
	
	/**
	 * Obtiene la suma de la matriz por la matriz m2
	 * 
	 * @param m2
	 *            Matriz a sumar
	 * @return Resultado de la suma de matrices
	 * @throws MatrizOperationException
	 *             Para sumarse las matrices deben ser del mismo tamaño
	 */
	public MatrizMath sumar(MatrizMath m2) throws MatrizOperationException {
		int size2[] = m2.getDimension();
		if (this.size[0] != size2[0] || this.size[1] != size2[1])
			throw new MatrizOperationException("Para sumar el tamaño de las matrices debe ser igual");

		double resultado[][] = new double[this.size[0]][];

		for (int i = 0; i < size[0]; i++) {
			resultado[i] = new double[size[1]];
			for (int j = 0; j < size2[1]; j++)
				resultado[i][j] = get(i, j) + m2.get(i, j);
		}
		return new MatrizMath(resultado);
	}


	/**
	 * Obtiene la resta de la matriz por la matriz m2
	 * 
	 * @param m2
	 *            Matriz a restar
	 * @return Resultado de la resta de matrices
	 * @throws MatrizOperationException
	 *             Para restarse las matrices deben ser del mismo tamaño
	 */
	public MatrizMath resta(MatrizMath m2) throws MatrizOperationException {
		int size2[] = m2.getDimension();
		if (this.size[0] != size2[0] || this.size[1] != size2[1])
			throw new MatrizOperationException("Para restar el tamaño de las matrices debe ser igual");

		double resultado[][] = new double[this.size[0]][];

		for (int i = 0; i < size[0]; i++) {
			resultado[i] = new double[size[1]];
			for (int j = 0; j < size2[1]; j++)
				resultado[i][j] = get(i, j) - m2.get(i, j);
		}
		return new MatrizMath(resultado);
	}

	/***
	 * Obtiene la matriz producto de multiplicar por la matriz m2
	 * 
	 * @param m2
	 *            Matriz a multiplicar
	 * @return Resultado del producto
	 * @throws MatrizOperationException
	 *             Para multiplicar el numero de columnas de la primer matriz
	 *             debe coincidir con el numero de filas de la segunda matriz
	 */
	public MatrizMath producto(MatrizMath m2) throws MatrizOperationException {
		int size2[] = m2.getDimension();
		if (this.size[1] != size2[0])
			throw new MatrizOperationException(
					"Para multiplicar el numero de columnas de la primer matriz debe coincidir con el numero de filas de la segunda matriz");

		double resultado[][] = new double[this.size[0]][];

		for (int i = 0; i < size[0]; i++) {
			resultado[i] = new double[size2[1]];
			for (int j = 0; j < size2[1]; j++)
				for (int k = 0; k < size[0]; k++)
					resultado[i][j] += get(i, k) * m2.get(k, j);
		}
		return new MatrizMath(resultado);
	}

	/***
	 * Obtiene la matriz producto de multiplicar por un escalar
	 * 
	 * @param m2
	 *            Matriz a multiplicar
	 * @return Resultado del producto
	 */
	public MatrizMath producto(double n) {
		double resultado[][] = new double[this.size[0]][];

		for (int i = 0; i < size[0]; i++) {
			resultado[i] = new double[size[1]];
			for (int j = 0; j < size[1]; j++)
				resultado[i][j] += get(i, j) * n;
		}
		return new MatrizMath(resultado);
	}
	
	/***
	 * Obtiene la matriz producto de multiplicar por un vector
	 * 
	 * @param m2
	 *            Matriz a multiplicar
	 * @return Resultado del producto
	 * @throws MatrizOperationException
	 *             Para multiplicar el numero de columnas de la primer matriz debe coincidir con la dimension del vector
	 */
	public MatrizMath producto(VectorMath v) throws MatrizOperationException {
		if (this.size[1] != v.getDimension())
			throw new MatrizOperationException(
					"Para multiplicar el numero de columnas de la matriz debe coincidir con la dimension del vector");
		
		double resultado[][] = new double[this.size[0]][];
		
		for (int i = 0; i < size[0]; i++) {
			resultado[i] = new double[1];
			for (int j = 0; j < size[1]; j++)
				resultado[i][0] += get(i, j) * v.get(j);
		}
		return new MatrizMath(resultado);
	}
	
	/**
	 * Realiza la norma uno de la matriz (Suma absoluta de columnas)
	 * 
	 * @return (Suma absoluta de columnas)
	 */
	public double normaUno() {
		double maximo = 0;
		double valor;
		for (int j = 0; j < size[0]; j++) {
			valor = 0;
			for (int i = 0; i < size[1]; i++) {
				valor += Math.abs(get(i, j));
				if (valor > maximo)
					maximo = valor;
			}
		}
		return maximo;
	}
	
	/**
	 * Realiza la norma dos de la matriz, tambien llamada norma de Frobenius
	 * 
	 * @return
	 */
	public double normaDos() {
		double resultado = 0;
		for (int i = 0; i < size[0]; i++)
			for (int j = 0; j < size[1]; j++)
				resultado += get(i, j) * get(i, j);
		return Math.sqrt(resultado);
	}
	
	/**
	 * Realiza la norma infinito de la matriz (Suma absoluta de filas)
	 * 
	 * @return (Suma absoluta de filas)
	 */
	public double normaInfinito() {
		double maximo = 0;
		double valor;
		for (int i = 0; i < size[0]; i++) {
			valor = 0;
			for (int j = 0; j < size[1]; j++) {
				valor += Math.abs(get(i, j));
				if (valor > maximo)
					maximo = valor;
			}
		}
		return maximo;
	}

	/**
	 * Ejecuta el lote de Pruebas
	 */
	public static void main(String[] args) {
		// Creacion de matrices
		// ---------------------------------------------------------------------
		double m1[][] = new double[][] { { 2d, 0d, 1d }, { 2d, 1d, 1d }, { 2d, 10d, 1d } };
		double m2[][] = new double[][] { { 2d, 3d }, { 2d, 7d } };
		double m3[][] = new double[][] { { 25d, 30d }, { 85.5d, 77d } };
		double m4[][] = new double[][] { { 2 * Math.PI, Math.sqrt(2) }, { 3 * Math.E, Math.sqrt(3) / 2 } };

		// Creacion de objetos matriz
		// ---------------------------------------------------------------------
		MatrizMath matriz1 = new MatrizMath(m1);
		MatrizMath matriz2 = new MatrizMath(m2);
		MatrizMath matriz3 = new MatrizMath(m3);
		MatrizMath matriz4 = new MatrizMath(m4);
		
		VectorMath v1 = new VectorMath(1,1,1);
		VectorMath v2 = new VectorMath(2,3,1);
		VectorMath v3 = new VectorMath(0,0);

		// Determinante
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Determinante");
			System.out.println("m1= " + matriz1.determinante() + " - Esperado 0");
			System.out.println("m2= " + matriz2.determinante() + " - Esperado 8");
			System.out.println("m3= " + matriz3.determinante() + " - Esperado -640");
			System.out.println("m4= " + matriz4.determinante() + " - Esperado -6.091294992");
		} catch (MatrizOperationException e) {
			e.printStackTrace();
		}

		// Inversa
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Inversa");
			System.out.println("m2= " + matriz2.inversa());
			System.out.println("m3= " + matriz3.inversa());
			System.out.println("m4= " + matriz4.inversa());
			// System.out.println("m1= " + matriz1.inversa());
		} catch (MatrizOperationException e) {
			e.printStackTrace();
		}

		// Producto
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Producto");
			System.out.println("m2=" + matriz2);
			System.out.println("m2 * I= " + matriz2.producto(matriz2.getIdentidad()));
			System.out.println("m2 * m2^-1= " + matriz2.producto(matriz2.inversa()));

			System.out.println("m3=" + matriz3);
			System.out.println("m3 * I= " + matriz3.producto(matriz3.getIdentidad()));
			System.out.println("m3 * m3^-1= " + matriz3.producto(matriz3.inversa()));

			System.out.println("m4=" + matriz4);
			System.out.println("m4 * I= " + matriz4.producto(matriz4.getIdentidad()));
			System.out.println("m4 * m4^-1= " + matriz4.producto(matriz4.inversa()));
		} catch (MatrizOperationException e) {
			e.printStackTrace();
		}

		// Norma dos
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Norma dos");
			System.out.println("m1= " + matriz1.normaDos() + " - Esperado 10.77032961");
			System.out.println("m2= " + matriz2.normaDos() + " - Esperado 8.124038405");
			System.out.println("m3= " + matriz3.normaDos() + " - Esperado 121.5082302");
			System.out.println("m4= " + matriz4.normaDos() + " - Esperado 10.42736412");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		// Calculo de error
		// ---------------------------------------------------------------------
		try {
			System.out.println("Calculo de error");
			// System.out.println("m1= " + matriz1.producto(matriz4.inversa()) +
			// " - Esperado 10.77032961");
			System.out.println("m2= " + matriz2.getIdentidad().resta(matriz2.producto(matriz2.inversa())).normaDos()
					+ " - Esperado < 1E-12");
			System.out.println("m3= " + matriz3.getIdentidad().resta(matriz3.producto(matriz3.inversa())).normaDos()
					+ " - Esperado < 1E-12");
			System.out.println("m4= " + matriz4.getIdentidad().resta(matriz4.producto(matriz4.inversa())).normaDos()
					+ " - Esperado < 1E-12");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		// Suma
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Suma");
			System.out.println("m1 + Id= " + matriz1.sumar(matriz1.getIdentidad()));
			System.out.println("m2 + m2= " + matriz2.sumar(matriz2));
			System.out.println("m3 + m2= " + matriz3.sumar(matriz2));
			System.out.println("m1 + m2= " + matriz1.sumar(matriz2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Resta
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Resta");
			System.out.println("m1= " + matriz1.resta(matriz1.getIdentidad()));
			System.out.println("m2= " + matriz2.resta(matriz2));
			System.out.println("m3= " + matriz3.resta(matriz2));
			System.out.println("m4= " + matriz4.resta(matriz4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Producto por escalar
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Producto por escalar");
			System.out.println("m1 * 1= " + matriz1.producto(1));
			System.out.println("m2 * 0= " + matriz2.producto(0));
			System.out.println("m3 * 1000= " + matriz3.producto(1000));
			System.out.println("m1 * PI= " + matriz1.producto(Math.PI));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Producto por vector
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Producto por vector");
			System.out.println("m1 * " + v1 +  "=" + matriz1.producto(v1));
			System.out.println("m1 * " + v2 +  "=" + matriz1.producto(v2));
			System.out.println("m2 * " + v3 +  "=" + matriz2.producto(v3));
			System.out.println("m4 * " + v1 +  "=" + matriz4.producto(v1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Norma uno
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Norma Uno");
			System.out.println("m1= " + matriz1.normaUno() + "- Esperado: 11");
			System.out.println("m2= " + matriz2.normaUno() + "- Esperado: 10");
			System.out.println("m3= " + matriz3.normaUno() + "- Esperado: 110.5");
			System.out.println("m4= " + matriz4.normaUno() + "- Esperado: " + (3*Math.E+Math.PI*2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Norma infinito
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Norma infinito");
			System.out.println("m1= " + matriz1.normaInfinito() + "- Esperado: 13");
			System.out.println("m2= " + matriz2.normaInfinito() + "- Esperado: 9");
			System.out.println("m3= " + matriz3.normaInfinito() + "- Esperado: 162.5");
			System.out.println("m4= " + matriz4.normaInfinito() + "- Esperado: " + (3*Math.E+Math.sqrt(3)/2));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Equals
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Equals");
			System.out.println("m1 == m1 ? " + matriz1.equals(matriz1));
			System.out.println("m1 == m2 ? " + matriz1.equals(v2));
			System.out.println("m2 == m2.clone() " + matriz2.equals(matriz2.clone()));
			System.out.println("m4 == null ? " + matriz4.equals(null));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
