package utility;

import mining.EmergingPattern;

import java.util.Comparator;


/**
 * Classe comparator per EmerginPattern
 * @see EmergingPattern
 */
public class ComparatorGrowRate implements Comparator<EmergingPattern> {

    /**
     * Override del metodo compare per il confronto
     * @param o1 Prima elemento da confrontare
     * @param o2 Secondo elemento da confrontare
     * @return 0 = gli elementi sono uguali, 1 il primo è più grande del secondo, -1 il secondo è più grande del primo
     */
    public int compare(EmergingPattern o1, EmergingPattern o2) {
        if(o1.getGrowrate()==o2.getGrowrate()) return 0;
        else if(o1.getGrowrate()>o2.getGrowrate()) return 1;
        else return -1;
    }

}
