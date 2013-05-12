package ar.edu.unlam.tda;


public class Lista <T> {
	private Nodo<T> primero;
	private Nodo<T> ultimo;
	
	public Lista() {
		primero= null;
		ultimo = null;
	}
	/** 
	 * Inserta un elemento al final.
	 */
		void push_back(T dato){
			Nodo<T> n = new Nodo<T>();
			n.setDato(dato);
			if(primero == null)
				primero = n;
			if(ultimo!=null)
				ultimo.setSiguiente(n);
			ultimo = n;
		}
		
		/**
		 * Borra un elemento al final.
		 */
		public T pop_back(){
			if(primero == null)
				return null;
			Nodo<T> aux = primero;
			while(aux != null && aux.getSiguiente() != ultimo)
				aux = aux.getSiguiente();
			T dato = ultimo.getDato();
			ultimo = aux; 
			return dato;
		}
		
		/** 
		 * Inserta un elemento al comienzo.
		 */
		public void push_front(T dato) {
			Nodo<T> nuevo = new Nodo<T>();
			nuevo.setDato(dato);
			nuevo.setSiguiente(primero);
			primero = nuevo;
		}
		/** 
		 * Borra un elemento al comienzo.
		 */
		public T pop_front(){
			if(primero == null)
				return null;
			T dato = primero.getDato();
			primero = primero.getSiguiente();
			return dato;
		}
		
		/**
		 * Elimina un elemento de un valor determinado.
		 */
		public int remove(T dato) {
			if(primero == null)
				return -1;
			
			Nodo<T> aux = primero;
			int pos = 0;
			while(aux.getSiguiente() != null && !aux.getSiguiente().getDato().equals(dato)) {
				aux = aux.getSiguiente();
				pos++;
			}
			
			if(aux.getSiguiente() == null) {
				return -1;
			} else {
				aux.setSiguiente(aux.getSiguiente().getSiguiente());
				return pos;
			}
		}
		
		/**
		 * invierte el orden de los elementos en la lista
		 */
		void reverse() {
			Lista<T> lista = new Lista<T>();
			T aux = this.pop_back();
			while(aux != null) {
				lista.push_front(aux);
				aux = this.pop_back();
			}
			this.primero = lista.primero;
			this.ultimo = lista.ultimo;
		}
		
//		
//		boolean insert(posici�n, dato ); //insertar
//		void erase(); //Eliminar por posici�n
//		boolean empty(); //vac�a
//		T buscar ( dato );
//		T buscar (posici�n);
//		void vaciar ( );
}
