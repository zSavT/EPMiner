package mining;


import data.*;

import java.io.*;
import java.util.Collections;
import utility.EmptyQueueException;
import utility.Queue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * classe per la gestionde del tipo FrequentPatternMiner.
 * @see FrequentPattern
 */
public class FrequentPatternMiner implements Iterable<FrequentPattern>, Serializable {
	/**
	 * Lista contenente i valori di output del FrequentPattern.
	 */
	private List<FrequentPattern> outputFP= new LinkedList<FrequentPattern>();

	/**
	 * costruttore di FrequentPatternMiner.
	 *
	 * @param data   oggetto di tipo Data
	 * @param minSup minSup
	 * @throws EmptySetException gestione delle eccezioni per insieme vuoto
	 */
	public FrequentPatternMiner(Data data,float minSup) throws EmptySetException{
		Queue<FrequentPattern> fpQueue=new Queue();
		boolean controllo = false;
		for(int i=0;i<data.getNumberOfAttributes();i++) {
			Attribute currentAttribute=data.getAttribute(i);
			if(currentAttribute instanceof DiscreteAttribute) {
				for(int j=0;j<((DiscreteAttribute)currentAttribute).getNumberOfDistinctValues();j++) {
					//Discrete
					DiscreteItem item=new DiscreteItem((DiscreteAttribute)currentAttribute,
							((DiscreteAttribute)currentAttribute).getValue(j));
					FrequentPattern fp=new FrequentPattern();
					fp.addItem(item);
					fp.setSupport(fp.computeSupport(data));
					if(fp.getSupport()>=minSup){ // 1-FP CANDIDATE
						fpQueue.enqueue(fp);
						//System.out.println(fp);
						outputFP.add(fp);
						controllo = true;
					}
				}
			} else if(currentAttribute instanceof ContinuousAttribute) {
				ContinuousAttribute continuousAttribute = (ContinuousAttribute) currentAttribute;
				Iterator<Float> iteratore = continuousAttribute.iterator();
				float min = iteratore.next();
				while(iteratore.hasNext()) {
					float next = iteratore.next();
					ContinuousItem item=new ContinuousItem(continuousAttribute, new Interval(min,next));
					FrequentPattern fp=new FrequentPattern();
					fp.addItem(item);
					fp.setSupport(fp.computeSupport(data));
					if(fp.getSupport()>=minSup) { // 1-FP CANDIDATE
						fpQueue.enqueue(fp);
						//System.out.println(fp);
						outputFP.add(fp);
						controllo=true;
					}
					min=next;
				}
			}
		} if (controllo) {
			try {
				expandFrequentPatterns(data, minSup, fpQueue, outputFP);
				outputFP.sort(FrequentPattern::compareTo);
			} catch (EmptyQueueException e) {
				System.out.println("Lista vuota.");
			}
		} else throw new EmptySetException();
	}


	/**
	 * @return lista del FrequentPattern
	 */
	List getOutputFP(){
		return this.outputFP;
	}

	/**
	 * @param FP   oggetto di tipo FrequentPattern
	 * @param item item
	 * @return FrequentPattern ridefinito
	 */
	FrequentPattern refineFrequentPattern(FrequentPattern FP, Item item){
		FrequentPattern pattern = new FrequentPattern(FP);
		pattern.addItem(item);
		return pattern;
	}

	/**
	 * restituisce una lista dopo aver espanso i frequent patterns
	 *
	 * @param data oggetto di tipo data
	 * @param minSup minSup
	 * @param fpQueue coda di FrequentPattern
	 * @param outputFP lista
	 * @throws EmptyQueueException eccezzione coda vuota
	 * @return lista espansa
	 */
	private List expandFrequentPatterns(Data data, float minSup, Queue<FrequentPattern> fpQueue, List outputFP) throws EmptyQueueException {
		if (fpQueue.isEmpty()) {
			throw new EmptyQueueException();
		} else {
			while (!fpQueue.isEmpty()) {
				FrequentPattern fp = (FrequentPattern) fpQueue.first(); //fp to be refined
				fpQueue.dequeue();
				for (int i = 0; i < data.getNumberOfAttributes(); i++) {
					boolean found = false;
					for (int j = 0; j < fp.getPatternLength(); j++) //the new item should involve an attribute different form attributes already involved into the items of fp
						if (fp.getItem(j).getAttribute().equals(data.getAttribute(i))) {
							found = true;
							break;
						}
					if (!found) //data.getAttribute(i) is not involve into an item of fp
					{

						if(data.getAttribute(i) instanceof DiscreteAttribute){
							for (int j = 0; j < ((DiscreteAttribute) data.getAttribute(i)).getNumberOfDistinctValues(); j++) {
								DiscreteItem item = new DiscreteItem((DiscreteAttribute) data.getAttribute(i), ((DiscreteAttribute) (data.getAttribute(i))).getValue(j));
								FrequentPattern newFP = refineFrequentPattern(fp, item); //generate refinement
								newFP.setSupport(newFP.computeSupport(data));
								if (newFP.getSupport() >= minSup) {
									fpQueue.enqueue(newFP);
									//System.out.println(newFP);
									outputFP.add(newFP);
								}
							}
						}

						else if(data.getAttribute(i) instanceof ContinuousAttribute){

							ContinuousAttribute continuousAttribute = (ContinuousAttribute) data.getAttribute(i);
							Iterator<Float> iter = continuousAttribute.iterator();
							float min = iter.next();
							while (iter.hasNext()) {
								float next = iter.next();
								ContinuousItem item = new ContinuousItem(continuousAttribute, new Interval(min, next));
								FrequentPattern newFP = refineFrequentPattern(fp, item); //generate refinement
								newFP.setSupport(newFP.computeSupport(data));
								if (newFP.getSupport() >= minSup) {
									fpQueue.enqueue(newFP);
									//System.out.println(newFP);
									outputFP.add(newFP);
								}
								min = next;
							}
						}
					}
				}
			}
			return outputFP;
		}
	}

	/**
	 * metodo per la stampa della classe
	 * @return string
	 */
	public String toString() {
		String value = "Frequent patterns\n";
		int n=0;
		Iterator i = outputFP.iterator();
		while (i.hasNext()) {
			value += Integer.toString(n+1) + ":" + i.next() + "\n";
			n++;
		}
		return value;
	}

	/**
	 * Salva.
	 *
	 * @param nomeFile nome del file
	 * @throws FileNotFoundException delle eccezioni per i file non trovati
	 * @throws IOException delle eccezioni di IO
	 */
	public void salva(String nomeFile) throws FileNotFoundException, IOException {
		FileOutputStream file = new FileOutputStream(nomeFile + ".dat");
		ObjectOutputStream outStream = new ObjectOutputStream(file);
		outStream.writeObject(this);
		file.close();
	}

	/**
	 * Carica frequent pattern miner.
	 *
	 * @param nomeFile nome del file
	 * @return FrequentPatternMiner
	 * @throws FileNotFoundException gestione delle eccezioni per i file non trovati
	 * @throws IOException gestione delle eccezioni di IO
	 * @throws ClassNotFoundException gestione delle eccezioni per le classi non trovate
	 *
	 */
	public static FrequentPatternMiner carica(String nomeFile) throws FileNotFoundException,IOException,ClassNotFoundException {
		FileInputStream inFile = new FileInputStream(nomeFile + ".dat");
		ObjectInputStream inStream = new ObjectInputStream(inFile);
		FrequentPatternMiner temp = (FrequentPatternMiner) inStream.readObject();
		inFile.close();
		return temp;
	}

	/**
	 * @return iteratore
	 */
	@Override
	public Iterator<FrequentPattern> iterator() {
		return null;
	}
}
