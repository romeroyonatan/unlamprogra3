package ar.edu.unlam.math.exception;

public class SELException extends Exception {
	public SELException(String mensaje) {
		super(mensaje);
	}

	public SELException(String string, MatrizOperationException e) {
		super(string, e);
	}

	public SELException(Exception e) {
		super(e);
	}

	private static final long serialVersionUID = 1L;

}
