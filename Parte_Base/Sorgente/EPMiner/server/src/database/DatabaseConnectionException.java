package database;

/**
 * classe per la gestione delle eccezioni per la connessione con il database
 * */
public class DatabaseConnectionException extends Exception {
	/**
	 * costruttore che richiama il costruttore madre
	 *
	 * @param msg messaggio di errore
	 */
	DatabaseConnectionException(String msg){
		super(msg);
	}
}
