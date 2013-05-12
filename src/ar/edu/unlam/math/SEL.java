package ar.edu.unlam.math;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
	private boolean tieneSolucion;

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

	/**
	 * Construye el sistema de ecuaciones lineales en base al contenido del
	 * archivo
	 * 
	 * @param file
	 *            Ubicacion del archivo
	 */
	public SEL(String file) throws SELException {
		File f = null;
		FileReader fr = null;
		BufferedReader in = null;
		String linea;
		double matriz[][];
		double vector[];
		try {
			f = new File(file);
			fr = new FileReader(f);
			in = new BufferedReader(fr);

			// Leo la dimension de la matriz
			linea = in.readLine();
			int dim = Integer.valueOf(linea);
			matriz = new double[dim][];

			// Leo los valores de la matriz
			for (int i = 0; i < dim; i++) {
				matriz[i] = new double[dim];
				linea = in.readLine();
				String[] numeros = linea.split(" ");
				for (int j = 0; j < dim; j++)
					matriz[i][j] = Double.valueOf(numeros[j]);
			}

			// Leo los valores del vector resultado
			vector = new double[dim];
			for (int i = 0; i < dim; i++)
				vector[i] = Double.valueOf(in.readLine());

			this.m = new MatrizMath(matriz);
			this.b = new VectorMath(vector);

		} catch (Exception e) {
			throw new SELException(e);
		} finally {
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					throw new SELException(e);
				}
		}
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
			tieneSolucion = true;
		} catch (MatrizOperationException e) {
			tieneSolucion = false;
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

	public void mostrarResultado() {
		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(tieneSolucion) {
			int dim = this.getX().getDimension();
			sb.append(dim + "\n");
			for (int i = 0; i < dim; i++)
				sb.append("x" + i + " = " + x.get(i) + "\n");
			sb.append(this.getError());
		} else {
			sb.append("EL SISTEMA NO TIENE SOLUCION");
		}
		return sb.toString();
	}

	public void toFile(String file) throws SELException {
		FileWriter fw = null;
		PrintWriter out = null;

		try {
			fw = new FileWriter(file);
			out = new PrintWriter(fw);
			out.println(this);
		} catch (Exception e) {
			throw new SELException(e);
		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				throw new SELException(e);
			}
		}
	}

	// Test ~
	// -----------------------------------------------------------------
	public static void main(String[] args) {
		double inicio, fin;
		try {
			// Abro carpeta test/input
			File directorio = new File("test/input");
			String nombre;
			// Recorro los archivos uno a uno
			for (File archivo : directorio.listFiles()) {
				
				// Obtengo el nombre del archivo
				nombre = archivo.getName();
				
				// Abro el archivo de entrada
				SEL test = new SEL("test/input/" + nombre);
				
				// Ejecuto la prueba
				inicio = System.currentTimeMillis();
				try{
					test.resolver();
				} catch (Exception e) {
					e.printStackTrace();
				}
				fin = System.currentTimeMillis();
				
				// Escribo el archivo de salida
				nombre = nombre.split("\\.")[0];
				test.toFile("test/output real/" + nombre + ".out");
				
				// Muestro tiempo utilizado
				System.out.println(nombre + ": " + (fin - inicio) + " ms");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
