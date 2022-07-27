package mining;


/**
 * classe per la gestione degli errori del tipo EmergingPattern
 * @see EmergingPattern
 * */
public class EmergingPatternException extends RuntimeException {

    /**
     * costruttore dell'eccezione che richiama il costruttore della classe madre
     */
    public EmergingPatternException(){
        super();
    }

    /**
     * costruttore dell'eccezione che richiama il costruttore della classe madre,
     * passando come parametro il messaggio di errore
     *
     * @param msg messaggio di errore
     */
    public EmergingPatternException(String msg){
        super(msg);
    }

}
