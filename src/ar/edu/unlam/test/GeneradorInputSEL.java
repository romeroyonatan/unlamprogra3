package ar.edu.unlam.test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GeneradorInputSEL {
	private static int DIMENSION = 500;
	private static double NUMERO_MAS_GRANDE = 100;
	private double[][] matriz;

	public GeneradorInputSEL() {
	}

	public void generar() {
		matriz = new double[DIMENSION][];
		for (int i = 0; i < DIMENSION; i++) {
			matriz[i] = new double[DIMENSION];
			for (int j = 0; j < DIMENSION; j++)
				matriz[i][j] = Math.random() * NUMERO_MAS_GRANDE;
		}
	}

	public void toFile(String path) {
		FileWriter file = null;
		PrintWriter out = null;
		try {
			
			file = new FileWriter(path);
			out = new PrintWriter(file);
			
			// Escribo la dimension
			out.println(DIMENSION);

			// Escribo los valores generados
			for (int i = 0; i < DIMENSION; i++) {
				for (int j = 0; j < DIMENSION; j++)
					out.print(matriz[i][j] + " ");
				out.println();
			}
			// Escribo los resultados de las ecuaciones
			for (int i = 0; i < DIMENSION; i++)
				out.println(Math.random() * NUMERO_MAS_GRANDE);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (file != null)
					file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		GeneradorInputSEL generador = new GeneradorInputSEL();
		generador.generar();
		generador.toFile("test/input/fatiga.in");
	}

}
