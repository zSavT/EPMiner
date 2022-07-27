package mining;

import data.ContinuousAttribute;
import data.Data;
import data.DiscreteAttribute;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Il tipo Frequent pattern.
 * @see Item
 */
class FrequentPattern implements Iterable<Item>,Comparable<FrequentPattern>, Serializable {

	/**
	 *
	 * Lista item.
	 */

	private List<Item> fp;
	/**
	 * Valore del support.
	 */
	private float support;


	/**
	 * costruttore di FrequentPattern.
	 */
	public FrequentPattern() {
		fp= new LinkedList<>();
	}


	/**
	 * costruttore per la copia di FrequentPattern.
	 *
	 * @param FP FrequentPattern
	 */
	FrequentPattern(FrequentPattern FP){

		fp=new LinkedList<>();
		Iterator<Item> i = FP.fp.iterator();

		while(i.hasNext()) {
			Item it = i.next();
			fp.add(it);
		}

		support=FP.getSupport();

	}

	/**
	 * aggiunge nuovo item al pettern
	 *
	 * @param item item
	 */
	public void addItem(Item item)
	{
		fp.add(item);
	}

	/**
	 * @param index indice
	 * @return item in posizione indice
	 */
	Item getItem(int index){
		return fp.get(index);
	 }

	/**
	 * @return float che indica il supporto del FrequentPattern
	 */
	float getSupport(){
		return support;
	 }

	/**
	 * @return intero che indica la lunghezza del FrequentPattern
	 */
	int getPatternLength(){ return fp.size(); }

	/**
	 * metodo per la stampa della classe
	 * @return string
	 */
	public String toString() {
		String value = "";

		Iterator<Item> iteratore1 = fp.iterator();
		Iterator<Item> iteratore2 = fp.iterator();

		iteratore1.next();
		while (iteratore1.hasNext()) {
			value += "(" + iteratore2.next() + ") AND ";
			iteratore1.next();
		}
		if (fp.size() > 0) {
			value += "(" + fp.get(fp.size() - 1) + ")";
			value += "[" + support + "]";
		}

		return value;
	}

	/**
	 * @param data oggetto di tipo Data
	 * @return float che indica il supporto aggiornato
	 */
		 float computeSupport(Data data){
			int suppCount=0;
			// indice esempio
			for(int i=0;i<data.getNumberOfExamples();i++){
				//indice item
				boolean isSupporting=true;
				for(int j=0;j<this.getPatternLength();j++)
				{
					if(this.getItem(j) instanceof DiscreteItem){
						//DiscreteItem
						DiscreteItem item=(DiscreteItem)this.getItem(j);
						DiscreteAttribute attribute=(DiscreteAttribute)item.getAttribute();
						//Extract the example value
						Object valueInExample=data.getAttributeValue(i, attribute.getIndex());
						if(!item.checkItemCondition(valueInExample)){
							isSupporting=false;
							break; //the ith example does not satisfy fp
						}
					} else if(this.getItem(j) instanceof ContinuousItem){
						//ContinuousItem
						ContinuousItem item=(ContinuousItem)this.getItem(j);
						ContinuousAttribute attribute=(ContinuousAttribute) item.getAttribute();
						//Extract the example value
						Object valueInExample=data.getAttributeValue(i, attribute.getIndex());
						if(!item.checkItemCondition(valueInExample)){
							isSupporting=false;
							break; //the ith example does not satisfy fp
						}
					}
				} if(isSupporting) suppCount++;
			}
			return((float)suppCount)/(data.getNumberOfExamples());

		}

	/**
	 * imposta il supporto del FrequentPattern con il valore di support
	 *
	 * @param support supporto
	 */
	void setSupport(float support){
		this.support = support;
	 }

	/**
	 * @return iteratore
	 */
	@Override
	public Iterator<Item> iterator() {
		return null;
	}

	/**
	 * Override della funzione compareTo che permette di comparare il supporto tra frequentPattern.
	 *
	 * @param o oggetto di tipo FrequentPattern
	 * @return intero che indica esito del confronto
	 */
	@Override
	public int compareTo(FrequentPattern o) {

		if (this.getSupport() > o.getSupport()) {
			return 1;
		} else if (this.getSupport() < o.getSupport()) {
			return -1;
		} else return 0;
	}

	/**
	 * Salva FrequentPatter serializzandolo.
	 *
	 * @param nomeFile nome del file
	 * @throws IOException gestione delle eccezioni di IO
	 */
	public void salva(String nomeFile) throws IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeFile));
		out.writeObject(this);
		out.close();
	}

	/**
	 * Carica FrequentPattern.
	 *
	 * @param nomeFile nome del file
	 * @return FrequentPattern
	 * @throws IOException gestione delle eccezioni di IO
	 * @throws ClassNotFoundException gestione delle eccezioni per le classi non trovate
	 */
	public static FrequentPattern carica(String nomeFile) throws IOException,ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeFile));
		FrequentPattern temp = (FrequentPattern) in.readObject();
		in.close();
		return temp;
	}

}
