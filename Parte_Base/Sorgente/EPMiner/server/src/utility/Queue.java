package utility;

import java.io.Serializable;

/**
 * Classe per le code del programma
 *
 * @param <T> Generico valore utilizzare
 */
public class Queue<T> implements Serializable {

	/**
	 *  Valore iniziale
	 */
		private Record begin = null;

	/**
	 * Valore finale
	 */
		private Record end = null;

	/**
	 * Classe relativa al singolo record della coda.
	 */
		private class Record {
		/**
		 * Generico elemento della coda.
		 */
			T elem;
		/**
		 * Record successivo.
		 */
			Record next;

			/**
			 * Istanzia un nuovo Record.
			 *
			 * @param e generico valore
			 */
			Record(T e) {
				this.elem = e; 
				this.next = null;
			}
		}


	/**
	 * Verifica se la coda è vuota e restituisce true o false.
	 *
	 * @return boolean
	 */
	public boolean isEmpty() {
			return this.begin == null;
		}

	/**
	 * Enqueue.
	 *
	 * @param e the e
	 */
	public void enqueue(T e) {
			if (this.isEmpty())
				this.begin = this.end = new Record(e);
			else {
				this.end.next = new Record(e);
				this.end = this.end.next;
			}
		}


	/**
	 * Il metodo restituisce il primo elemento nella coda
	 *
	 * @return il primo elemento
	 */
	public T first(){
			return this.begin.elem;
		}

	/**
	 * Il metodo controlla se la coda ha elementi o meno
	 *
	 * @throws EmptyQueueException the empty queue exception
	 */
	public void dequeue() throws EmptyQueueException{
			if(this.begin==this.end){
				if(this.begin==null) throw new EmptyQueueException();
				else
					this.begin=this.end=null;
			}
			else{
				begin=begin.next;
			}
			
		}

	}