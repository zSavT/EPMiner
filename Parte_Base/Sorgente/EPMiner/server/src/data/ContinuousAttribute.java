package data;


import java.io.Serializable;
import java.util.Iterator;

/**
 * Classe per la gestione del tipo ContinuousAttribute.
 * @see Attribute
 */
public class ContinuousAttribute extends Attribute implements Iterable<Float>, Serializable {
	/**
	 * Valore massimo
	 */
	private float max;
	/**
	 * Valore minimo
	 */
	private float min;

	/**
	 * @param name  stringa del nome
	 * @param index intero indicante indice
	 * @param min   valore minimo
	 * @param max   valore massimo
	 */
	ContinuousAttribute(String name, int index, float min, float max){
		super(name,index);
		this.max=max;
		this.min=min;
	}

	/**
	 * restituisce il minimo.
	 *
	 * @return minimo
	 */
	public float getMin() {
		return min;
	}

	/**
	 * restituisce il massimo.
	 *
	 * @return massimo
	 */
	public float getMax() {
		return max;
	}

	/**
	 * costruttore dell'iteratore.
	 *
	 * @return iteratore
	 */
	public Iterator<Float> iterator() {
		return new ContinuousAttributeIterator(min, max, 5);
	}

}
