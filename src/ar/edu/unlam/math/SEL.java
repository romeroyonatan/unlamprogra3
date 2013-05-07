package ar.edu.unlam.math;

import ar.edu.unlam.math.exception.MatrizOperationException;
import ar.edu.unlam.math.exception.SELException;

/**
 * Resuelve un sistema de ecuaciones lineales
 * 
 * @author yoni
 * 
 */
public class SEL {
	private static final double EPSILON = 1E-12;
	// Atributos ~
	// -----------------------------------------------------------------
	private MatrizMath m;
	private VectorMath x, b;
	private double error;

	// Constructores ~
	// -----------------------------------------------------------------
	/**
	 * Construye un sistema de ecuaciones lineales con los parametros m y b
	 * 
	 * @param m
	 *            Matriz de coeficientes
	 * @param b
	 *            Resultado de ecuaciones
	 */
	public SEL(MatrizMath m, VectorMath b) {
		this.m = m;
		this.b = b;
	}

	// Getters and setters ~
	// -----------------------------------------------------------------
	public MatrizMath getM() {
		return m;
	}

	public void setM(MatrizMath m) {
		this.m = m;
	}

	public VectorMath getX() {
		return x;
	}

	public VectorMath getB() {
		return b;
	}

	public void setB(VectorMath b) {
		this.b = b;
	}

	public double getError() {
		return error;
	}

	// Metodos ~
	// -----------------------------------------------------------------
	public void resolver() throws Exception {
		try {
			x = new VectorMath(m.inversa().producto(b));
			calcularError();
		} catch (MatrizOperationException e) {
			throw new SELException("El sistema de ecuaciones no tiene solución", e);
		}
	}

	public boolean test() throws Exception {
		return calcularError() < EPSILON;
	}

	public double calcularError() throws Exception {
		VectorMath aprox = new VectorMath(m.producto(x));
		error = b.restar(aprox).normaDos();
		return error;
	}

	public String mostrarResultado() {
		return this.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < x.getDimension(); i++)
			sb.append("x" + i + " = " + x.get(i) + "\n");
		return sb.toString();
	}

	// Test ~
	// -----------------------------------------------------------------
	public static void main(String[] args) {
		// Creacion de matrices
		// ---------------------------------------------------------------------
		double matriz1[][] = new double[][] { { 2d, 0d, 1d }, { 2d, 1d, 1d }, { 2d, 10d, 1d } };
		double matriz2[][] = new double[][] { { 2d, 3d }, { 2d, 7d } };
		double matriz3[][] = new double[][] { { 25d, 30d }, { 85.5d, 77d } };
		double matriz4[][] = new double[][] { { 2 * Math.PI, Math.sqrt(2) }, { 3 * Math.E, Math.sqrt(3) / 2 } };

		// Creacion de objetos matriz
		// ---------------------------------------------------------------------
		MatrizMath m1 = new MatrizMath(matriz1);
		MatrizMath m2 = new MatrizMath(matriz2);
		MatrizMath m3 = new MatrizMath(matriz3);
		MatrizMath m4 = new MatrizMath(matriz4);

		VectorMath b1 = new VectorMath(10 * Math.PI, 5 * Math.E);
		VectorMath b2 = new VectorMath(1, 5);
		VectorMath b3 = new VectorMath(10, 5, 1);

		SEL sel;

		// Caso 1
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Caso 1: m2 y b1");
			sel = new SEL(m2, b1);
			sel.resolver();
			System.out.println("Resultado:\n" + sel);
			System.out.println("Error: " + sel.getError());
			System.out.println("Pasa prueba: " + sel.test());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Caso 2
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Caso 2: m1 y b3");
			sel = new SEL(m1, b3);
			sel.resolver();
			System.out.println("Resultado:\n" + sel);
			System.out.println("Error: " + sel.getError());
			System.out.println("Pasa prueba: " + sel.test());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Caso 3
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Caso 3: m3 y b2");
			sel = new SEL(m3, b2);
			sel.resolver();
			System.out.println("Resultado:\n" + sel);
			System.out.println("Error: " + sel.getError());
			System.out.println("Pasa prueba: " + sel.test());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Caso 4
		// ---------------------------------------------------------------------
		try {
			System.out.println("---------------------------------------------------------------------");
			System.out.println("Caso 4: m4 y b1");
			sel = new SEL(m4, b1);
			sel.resolver();
			System.out.println("Resultado:\n" + sel);
			System.out.println("Error: " + sel.getError());
			System.out.println("Pasa prueba: " + sel.test());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
