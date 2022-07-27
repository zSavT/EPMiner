package data;


import java.io.Serializable;

/**
 * classe per la gestione del tipo DiscreteAttribute.
 * @see Attribute
 */
public class DiscreteAttribute extends Attribute implements Serializable{

	/**
	 * Vettore contenente i valori.
	 */
	String values[];

	/**
	 * costruttore di Discrete attribute che richiama quello della classe madre.
	 *
	 * @param name   stringa del nome
	 * @param index  stringa dell'indice
	 * @param values array di valori
	 */
	DiscreteAttribute(String name, int index, String values[]){
		super(name,index);
		this.values=new String[values.length];
		for (int i=0;i<values.length;i++){
			this.values[i]=values[i];
		}
	}

	/**
	 * @return dimensione dell'array di valori
	 */
	public int getNumberOfDistinctValues() {
		return values.length;
	}

	/**
	 * @param index intero dell'indice
	 * @return valore in posizione indice
	 */
	public String getValue(int index) {
		return values[index];
	}

}
