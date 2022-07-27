package data;


/**
 * La classe che si occupa della gestione delle eccezioni.
 * @see RuntimeException
 */
public class EmptySetException extends RuntimeException {


    /**
     * Istanzia una nuova eccezione dell'insieme vuoto, richiamando il costruttore della classe madre.
     */
    public EmptySetException(){
        super();
    }

    /**
     * Istanzia una nuova eccezione dell'insieme vuoto, richiamando il costruttore della classe madre e passando
     * come parametro una stringa che contiene il messaggio di errore.
     *
     * @param msg Messaggio di errore
     */
    public EmptySetException(String msg){
        super(msg);
    }

}
