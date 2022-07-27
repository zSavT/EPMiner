package mining;

import data.Attribute;

import java.io.*;

/**
 * classe per la gestione del tipo Item.
 */
abstract public class Item implements Serializable {
    /**
     * attributo coinvolto nell'item
     */
    private Attribute attribute;
    /**
     * valore assegnato all'attributo
     */
    private Object value;

    /**
     *costruttore di Item.
     *
     * @param attribute the attribute
     * @param value     the value
     */
    Item(Attribute attribute,Object value){
        this.attribute= attribute;
        this.value=value;
    }

    /**
     * @return attributo di Item
     */
    Attribute getAttribute(){
        return attribute;
    }

    /**
     * @return valore di Item di tipo Oggetto
     */
    Object getValue(){
        return value;
    }

    /**
     * verifica che il valore rispetti una determinata condizione.
     *
     * @param value valore
     * @return valore booleano che indica l'esito della verifica
     */
    abstract boolean checkItemCondition(Object value);

    /**
     * metodo per la stampa della classe
     * @return string
     */
    public String toString(){
        return attribute+"="+value;
    }


}
