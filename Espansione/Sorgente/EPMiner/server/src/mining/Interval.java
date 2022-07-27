package mining;

import java.io.*;

/**
 * classe per la gestione del tipo Interval.
 */
public class Interval  implements Serializable {
    /**
     * Valore minimo
     */
    private float inf;
    /**
     * Valore massimo
     */
    private float sup;

    /**
     * costruttore di Interval.
     *
     * @param inf the inf
     * @param sup the sup
     */
    public Interval(float inf,float sup){
        this.inf=inf;
        this.sup=sup;
    }

    /**
     * impostare il campo inf di Interval uguale ad inf
     *
     * @param inf valore che indica l'estremo inferiore
     */
    void setInf(float inf){
        this.inf=inf;
    }

    /**
     * impostare il campo sup di Interval uguale a sup
     *
     * @param sup valore che indica l'estremo superiore
     */
    void setSup(float sup){
        this.sup=sup;
    }

    /**
     * restituisce l'inf di Interval
     *
     * @return valore float che indica l'inf
     */
    float getInf(){
        return this.inf;
    }

    /**
     * restituisce il sup di Interval
     *
     * @return valore float che indica il sup
     */
    float getSup(){
        return this.sup;
    }

    /**
     * restituisce true se valore è compreso tra inf e sup, false altrimenti
     *
     * @param value valore
     * @return valore booleano
     */
    public boolean checkValueInclusion(float value){
        return(value>=getInf() && value<getSup());
    }

    /**
     * metodo per la stampa della classe
     * @return string
     */
    public String toString(){
        return "["+getInf()+","+getSup()+"[";
    }


}
