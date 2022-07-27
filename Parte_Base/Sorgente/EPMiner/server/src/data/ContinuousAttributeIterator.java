package data;

import java.io.Serializable;
import java.util.Iterator;

/**
 * classe per la gestione dell'iteratore del tipo ContinuosAttribute
 */
public class ContinuousAttributeIterator implements Iterator<Float>, Serializable {

	/**
	 * Valore minimo
	 */
	private float min;
	/**
	 * Valore massimo
	 */
	private float max;
	/**
	 * Indice (parte da 0).
	 */
	private int j=0;
	/**
	 * Numero valori.
	 */
	private int numValues;

	/**
	 * avvalora i membri della classe ContinuosAttribute con i parametri del costruttore
	 *
	 * @param min      	valore minimo
	 * @param max       the massimo
	 * @param numValues numero di valori
	 */
	ContinuousAttributeIterator(float min,float max,int numValues){
		this.min=min;
		this.max=max;
		this.numValues=numValues;
	}

	/**
	 * valore booleano che vale true se j <= numValues, false altrimenti
	 * @return valore booleano
	 */
	@Override
	public boolean hasNext() {
		
		return (j<=numValues);
	}

	/**
	 * incrementa j
	 *
	 * @return valore float in posizione j-1(min + (j-1)*(max-min)/numValues))
	 */
	public Float next() {

		j++;
		return min+((max-min)/numValues)*(j-1);
	}

	/**
	 * Remove.
	 */
	public void remove() {}

}
