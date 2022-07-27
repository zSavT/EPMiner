package data;


import java.io.Serializable;

/**
 * Classe per la gestione degli attributi
 */
public abstract class Attribute implements Serializable {

	/**
	 *	Nome dell'attributo
	 */
	private String name;
	/**
	 *	Indice attributo
	 */
	private int index;

	/**
	 * Istanzia un nuovo attributo.
	 *
	 * @param name  nome dell'attributo
	 * @param index indice
	 */
	public Attribute(String name, int index){
		this.name= name;
		this.index=index;
	}

	/**
	 * Restituisce il nome.
	 *
	 * @return name name
	 */
	String getName() {
		return name;
	}

	/**
	 * Restituisce l'indice.
	 *
	 * @return indice index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * metodo per la stampa della classe
	 * @return stringa contenente il nome dell'attributo
	 */
	public String toString() {
		return name;
	}
	
}

