package ar.edu.unlam.tda;

public interface Pila <T> {
	boolean empty(); // es vacía
	void push(T t); //apilar
	T pop(); // deseapilar
	T peek(); //devuelve una referencia al elemento de la cima
	void vaciar();
}
