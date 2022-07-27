package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.management.InstanceNotFoundException;

/**
 * Gestisce l'accesso al DB per la lettura dei dati di training
 *
 * @author Map Tutor
 */
public class DbAccess {

	/**
	 * Driver class per la connessione.
	 */
	private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	/**
	 * Tipo di database
	 */
	private final String DBMS = "jdbc:mysql";
	/**
	 * Indirizzo server
	 */
	private final String SERVER = "localhost";
	/**
	 * Porta per la connessione al database.
	 */
	private final int PORT = 3306;
	/**
	 * Nome del database
	 */
	private final String DATABASE = "Map";
	/**
	 * User ID del database per la connessione
	 */
	private final String USER_ID = "Student";
	/**
	 * Password database
	 */
	private final String PASSWORD = "map";

	/**
	 * Connettore per il Database.
	 */
	private Connection conn;

	/**
	 * Inizializza una connessione al DB
	 *
	 * @throws DatabaseConnectionException the database connection exception
	 */
	public void initConnection() throws DatabaseConnectionException{
		String connectionString =  DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
				+ "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
		try {
			
				Class.forName(DRIVER_CLASS_NAME).newInstance();
			} 
		catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new DatabaseConnectionException(e.toString());
			}
		catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new DatabaseConnectionException(e.toString());
			} 
		catch (ClassNotFoundException e) {
			System.out.println("Impossibile trovare il Driver: " + DRIVER_CLASS_NAME);
			throw new DatabaseConnectionException(e.toString());
		}
		
		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);
			
		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}
		
	}

	/**
	 * @return connessione
	 */
	public  Connection getConnection(){
		return conn;
	}

	/**
	 * chiude la connesione
	 */
	public  void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Impossibile chiudere la connessione");
		}
	}

}
