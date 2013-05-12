package ar.edu.unlam.tda;

public class Nodo <T>{
	private T dato;
	private Nodo<T> siguiente;
	
	public T getDato() {
		return dato;
	}
	public void setDato(T dato) {
		this.dato = dato;
	}
	public Nodo<T> getSiguiente() {
		return siguiente;
	}
	public void setSiguiente(Nodo<T> siguiente) {
		this.siguiente = siguiente;
	}
}
