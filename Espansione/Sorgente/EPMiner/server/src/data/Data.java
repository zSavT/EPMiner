package data;


import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;

import java.util.List;
import database.*;
import database.TableData.TupleData;

/**
 * Classe per la gestione del tipo Data.
 */
public class Data {
	
	/**
	 * Vettore di oggetti.
	 */
	private Object data [][];
	/**
	 * Numeri di espempi totali
	 */
	private int numberOfExamples;
	/**
	 * Set di attributi
	 */
	private List<Attribute> attributeSet=new LinkedList<Attribute>();


	/**
	 * costruttore di Data.
	 *
	 * @param tableName stringa contenente il nome
	 * @throws DatabaseConnectionException eccezione per la gestione della connesione con il database
	 * @throws SQLException                eccezione per la gestione di SQL
	 * @throws NoValueException            eccezione per la gestione di nessun valore
	 */
	public Data (String tableName) throws DatabaseConnectionException, SQLException, NoValueException {
		// open db connection
	
			
			DbAccess db= new DbAccess();
			db.initConnection();
	
		
		
		
			TableSchema tSchema;
			try {
				tSchema = new TableSchema(tableName, db.getConnection());
				TableData tableData=new TableData(db.getConnection());
				List<TupleData> transSet=tableData.getTransazioni(tableName);
				
				data= new Object[transSet.size()][tSchema.getNumberOfAttributes()];
				for(int i=0;i<transSet.size();i++)
					for(int j=0;j<tSchema.getNumberOfAttributes();j++){
						data[i][j]=transSet.get(i).tuple.get(j);
				}
				
				numberOfExamples=transSet.size();
						
				for(int i=0;i<tSchema.getNumberOfAttributes();i++)
				{
					if(tSchema.getColumn(i).isNumber())
						attributeSet.add(
							new ContinuousAttribute(
									tSchema.getColumn(i).getColumnName(),
									i,
									((Float)(tableData.getAggregateColumnValue(tableName, tSchema.getColumn(i), QUERY_TYPE.MIN))).floatValue(),
									((Float)(tableData.getAggregateColumnValue(tableName, tSchema.getColumn(i), QUERY_TYPE.MAX))).floatValue()+0.606f
							)
							);
					else
					{
						// avvalora values con i valori distinti della colonna oridinati in maniera crescente
						List<Object> valueList=tableData.getDistinctColumnValues(tableName,tSchema.getColumn(i));
						String values[]=new String[valueList.size()];
						Iterator it= valueList.iterator();
						int ct=0;
						while(it.hasNext()){
							values[ct]=(String)it.next();
							ct++;
						}
						attributeSet.add(new DiscreteAttribute(tSchema.getColumn(i).getColumnName(),i,values));
					}
				}
			} 
			
			finally{
				db.closeConnection();
			}
			
		
	}


	/**
	 * Get number of examples int.
	 *
	 * @return intero che indica il numero di esempio
	 */
	public int getNumberOfExamples(){
		return numberOfExamples;
	}

	/**
	 * Get number of attributes int.
	 *
	 * @return intero che indica la dimensione dell'attributeSet
	 */
	public int getNumberOfAttributes(){
		return attributeSet.size();
	}


	/**
	 * Get attribute value object.
	 *
	 * @param exampleIndex   indice dell'esempio
	 * @param attributeIndex indice dell'attributo
	 * @return valore dell'oggetto in posizione [exampleIndex][attributeSet.get(attributeIndex).getIndex()]
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex){
		return data[exampleIndex][attributeSet.get(attributeIndex).getIndex()];
	}

	/**
	 * Get attribute attribute.
	 *
	 * @param index indice
	 * @return attributo in posizione index di attributeSet
	 */
	public Attribute getAttribute(int index){
		return attributeSet.get(index);
	}


	/**
	 * metodo per la stampa della classe
	 * @return String
	 */
	public String toString(){
		String value="";
		for(int i=0;i<numberOfExamples;i++){
			value+=(i+1)+":";
			for(int j=0;j<attributeSet.size()-1;j++)
				value+=data[i][j]+",";
			
			value+=data[i][attributeSet.size()-1]+"\n";
		}
		return value;
		
		
	}


	
	


}
