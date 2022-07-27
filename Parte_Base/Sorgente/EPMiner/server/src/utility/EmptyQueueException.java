package utility;

/**
 * Classe per la gestione delle Exception
 * @see Queue
 * @see mining.FrequentPatternMiner
 */
public class EmptyQueueException extends RuntimeException {

    /**
     * Richiama il costruttore della classe madre per la gestione
     */
    public EmptyQueueException(){
        super();
    }

    /**
     * Richiama il costruttore della classe madre per la gestione con il passaggio di una stringa contenente il codice di errore
     * @param msg Messaggio di errore.
     */
    public EmptyQueueException(String msg){
        super(msg);
    }

}
