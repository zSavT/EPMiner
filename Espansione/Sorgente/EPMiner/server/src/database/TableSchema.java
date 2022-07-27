package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * classe per il tipo Table schema.
 */
public class TableSchema {
	/**
	 * Connettore al database
	 */
	private Connection connection;

	/**
	 * instanzia un nuovo Table schema.
	 *
	 * @param tableName  table name
	 * @param connection connessione
	 * @throws SQLException gestione per gli errori di SQL
	 */
	public TableSchema(String tableName, Connection connection) throws SQLException{
		this.connection=connection;
		HashMap<String,String> mapSQL_JAVATypes=new HashMap<String, String>();
		//http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");
		
		 DatabaseMetaData meta = connection.getMetaData();
	     ResultSet res = meta.getColumns(null, null, tableName, null);
		   
	     while (res.next()) {
	         
	         if(mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
	        		 tableSchema.add(new Column(
	        				 res.getString("COLUMN_NAME"),
	        				 mapSQL_JAVATypes.get(res.getString("TYPE_NAME")))
	        				 );
	
	         
	         
	      }
	      res.close();
		
	}

	/**
	 * classe per la gestione del tipo Column.
	 */
	public class Column{
		/**
		 * Nome colum
		 */
		private String name;
		/**
		 * Tipologia colum
		 */
		private String type;

		/**
		 * costruttore di Column.
		 *
		 * @param name nome della colonna
		 * @param type tipo
		 */
		Column(String name,String type){
			this.name=name;
			this.type=type;
		}

		/**
		 * metodo che ristituisce il nome della colonna
		 * @return stringa del nome della colonna
		 */
		public String getColumnName(){
			return name;
		}

		/**
		 * metodo che controlla se il valore è un numero o meno
		 * @return valore booleano che indica true se è un numero, false altrimenti
		 */
		public boolean isNumber(){
			return type.equals("number");
		}

		/**
		 * metodo per la stampa della classe
		 * @return string
		 */
		public String toString(){
			return name+":"+type;
		}
	}

	/**
	 * Tabella contenente le colonne dei dati.
	 */
	List<Column> tableSchema=new ArrayList<Column>();


	/**
	 * metodo che restituisce il numero degli attributi
	 * @return intero che indica il numero di attributi
	 */
	public int getNumberOfAttributes(){
			return tableSchema.size();
		}

	/**
	 * metodo che restituisce la colonna in riferimento all'indice
	 * @param index intero dell'indice
	 * @return colonna in posizione indice
	 */
	public Column getColumn(int index){
			return tableSchema.get(index);
		}

		
	}

		     


