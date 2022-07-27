package mining;

import data.ContinuousAttribute;

import java.io.*;

/**
 * classe per la gestione del tipo ContinuousItem.
 * @see Item
 */
public class ContinuousItem extends Item implements Serializable {

    /**
     * costruttore di ContinuosItem, che richiama il costruttore della classe madre.
     *
     * @param attribute ContinuousAttribute
     * @param value     valore dell'intervallo
     */
    public ContinuousItem(ContinuousAttribute attribute,Interval value){
        super(attribute,value);
    }

    /**
     * restituisce true o false dopo aver richiamato la funzione checkValueInclusion
     *
     * @param value oggetto di cui effettuare il controllo
     */
    boolean checkItemCondition(Object value) {
        return ((Interval) (this.getValue())).checkValueInclusion((float) value);
    }

    /**
     * metodo per la stampa della classe
     * @return string
     */
    public String toString(){
        String s = "";
        s += getAttribute() + " in " + (Interval) this.getValue();
        return s;
    }

}
