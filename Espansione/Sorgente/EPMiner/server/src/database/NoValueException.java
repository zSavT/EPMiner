package database;

/**
 * classe per la gestione delle eccezioni per nessun valore
 * @see Exception
 */
public class NoValueException extends Exception {

	/**
	 * costruttore per la classe NoValueException, che richiama il costruttore della classe madre
	 *
	 * @param msg messaggio di errore
	 */
	public NoValueException(String msg) {
		// TODO Auto-generated constructor stub
		super(msg);
	}

}
