package ar.edu.unlam.tda;

public interface Cola <T> {
	void empty( );//vac�a
	void offer(T t);//a�adir elemento
	T poll( ); //Quitar elemento
	T peek(); //una referencia al primer elemento, pero no elimina
	void vaciar ( );
}
