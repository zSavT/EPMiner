package mining;

import data.*;


/**
 * classe per la gestione del tipo DiscreteItem.
 * @see Item
 */
public class DiscreteItem extends Item {


    /**
     * costruttore di DiscreteItem che richiama il costruttore della classe madre.
     *
     * @param attribute DiscreteAttribute
     * @param value     valore
     */
    DiscreteItem(DiscreteAttribute attribute, String value) {
        super(attribute,value);
    }

    /**
     * restituisce true se il valore coincide, false altrimenti
     *
     * @param value oggetto di cui effettuare il controllo
     */
    boolean checkItemCondition(Object value){
        return this.getValue().equals(value);
    }


}
