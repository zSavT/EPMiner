package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import database.TableSchema.Column;


/**
 * classe per il tipo Table data.
 */
public class TableData {

	/**
	 * Connettore al database
	 */
	private Connection connection;

	/**
	 * costruttore di TableData
	 *
	 * @param connection connesione
	 */
	public TableData(Connection connection){this.connection=connection;}

	/**
	 * classe per il tipo TupleData.
	 */
	public class TupleData{

		/**
		 * Lista contenente le tuple dei dati.
		 */
		public List<Object> tuple=new ArrayList<Object>();

		/**
		 * metodo per la stampa della classe
		 * @return string
		 */
		public String toString(){
			String value="";
			Iterator<Object> it= tuple.iterator();
			while(it.hasNext())
				value+= (it.next().toString() +" ");
			
			return value;
		}
	}


	/**
	 * restitusce transazioni.
	 *
	 * @param table table
	 * @return transazioni
	 * @throws SQLException gestione delle eccezioni di SQL
	 */
	public List<TupleData> getTransazioni(String table) throws SQLException{
		LinkedList<TupleData> transSet = new LinkedList<TupleData>();
		Statement statement;
		TableSchema tSchema=new TableSchema(table,connection);
		
		
		String query="select ";
		
		for(int i=0;i<tSchema.getNumberOfAttributes();i++){
			Column c=tSchema.getColumn(i);
			if(i>0)
				query+=",";
			query += c.getColumnName();
		}
		if(tSchema.getNumberOfAttributes()==0)
			throw new SQLException();
		query += (" FROM "+table);
		
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			TupleData currentTuple=new TupleData();
			for(int i=0;i<tSchema.getNumberOfAttributes();i++)
				if(tSchema.getColumn(i).isNumber())
					currentTuple.tuple.add(rs.getFloat(i+1));
				else
					currentTuple.tuple.add(rs.getString(i+1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();

		
		
		return transSet;

	}


	/**
	 * Get distinct column values list.
	 *
	 * @param table  table
	 * @param column column
	 * @return list
	 * @throws SQLException gestione delle eccezioni di SQL
	 */
	public  List<Object>getDistinctColumnValues(String table,Column column) throws SQLException{
		LinkedList<Object> valueSet = new LinkedList<Object>();
		Statement statement;
		TableSchema tSchema=new TableSchema(table,connection);
		
		
		String query="select distinct ";
		
		query+= column.getColumnName();
		
		query += (" FROM "+table);
		
		query += (" ORDER BY " +column.getColumnName());
		
		
		
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
				if(column.isNumber())
					valueSet.add(rs.getFloat(1));
				else
					valueSet.add(rs.getString(1));
			
		}
		rs.close();
		statement.close();
		
		return valueSet;

	}

	/**
	 * Ottiene il valore aggregato della colonna.
	 *
	 * @param table     table
	 * @param column    column
	 * @param aggregate aggregate
	 * @return valore aggregato della colonna
	 * @throws SQLException gestione delle eccezioni di SQL
	 * @throws NoValueException eccezzione per la non presenza del valore
	 */
	public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
		Statement statement;
		TableSchema tSchema=new TableSchema(table,connection);
		Object value=null;
		String aggregateOp="";
		
		String query="select ";
		if(aggregate==QUERY_TYPE.MAX)
			aggregateOp+="max";
		else
			aggregateOp+="min";
		query+=aggregateOp+"("+column.getColumnName()+ ") FROM "+table;
		
		
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
				if(column.isNumber())
					value=rs.getFloat(1);
				else
					value=rs.getString(1);
			
		}
		rs.close();
		statement.close();
		if(value==null)
			throw new NoValueException("No " + aggregateOp+ " on "+ column.getColumnName());
			
		return value;

	}

	

	

}
