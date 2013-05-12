package ar.edu.unlam.tda;

public interface Cola <T> {
	void empty( );//vacía
	void offer(T t);//añadir elemento
	T poll( ); //Quitar elemento
	T peek(); //una referencia al primer elemento, pero no elimina
	void vaciar ( );
}
