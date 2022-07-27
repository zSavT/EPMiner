package mining;

import data.Data;
import data.EmptySetException;
import utility.ComparatorGrowRate;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * classe per la gestione del tipo EmerginPatternMiner.
 * @see EmergingPattern
 */
public class EmergingPatternMiner implements Iterable<EmergingPattern>, Serializable {

    /**
     * Lista contenente i valori riscontrati.
     */
    private List<EmergingPattern> epList = new LinkedList<EmergingPattern>();

    /**
     * costruttore dell' EmergingPatternMiner.
     *
     * @param dataBackground oggetto di tipo dataBackground
     * @param fpList         oggetto di tipo FrequentPatternMiner
     * @param minG          valore di minG
     * @throws EmptySetException the empty set exception
     */
    public EmergingPatternMiner(Data dataBackground,FrequentPatternMiner fpList, float minG) throws EmptySetException{

        boolean controllo = false;

        if (fpList.getOutputFP() != null) {

            List temp = fpList.getOutputFP();
            Iterator i = temp.listIterator();
            while (i.hasNext()) {
                EmergingPattern ep = null;
                try {
                    ep = computeEmergingPattern(dataBackground, (FrequentPattern) i.next(), minG);
                } catch (EmergingPatternException e) {
                    System.out.println("");
                }
                if (ep != null) {
                    epList.add(ep);
                    controllo = true;
                }
            }
            epList.sort(new ComparatorGrowRate());
            if (!controllo) throw new EmptySetException();
        }
    }


    /**
     * Compute grow rate float.
     *
     * @param dataBackground oggetto di tipo dataBackground
     * @param fp             oggetto di tipo FrequentPattern
     * @return valore float dopo la computazione del GrowRate
     */
//Metodi
    float computeGrowRate(Data dataBackground, FrequentPattern fp) {
        return fp.getSupport() / fp.computeSupport(dataBackground);
    }

    /**
     *
     * @param dataBackground oggetto di tipo dataBackground
     * @param fp             oggetto di tipo FrequentPattern
     * @param minGR          valore di minGR
     * @return restituisce nuovo EmergingPattern dopo aver richiamato computeGrowRate e verificato se superiore o uguale al minGR
     * @throws EmergingPatternException gestione delle eccezioni per EmergingPattern
     */
    EmergingPattern computeEmergingPattern(Data dataBackground, FrequentPattern fp, float minGR) throws EmergingPatternException{
        float growRate = computeGrowRate(dataBackground, fp);
        if (growRate >= minGR)
            return new EmergingPattern (fp, growRate);
        else throw new EmergingPatternException();

    }

    /**
     * metodo per la stampa della classe
     * @return string
     */
    public String toString() {
        String value = "Emerging patterns\n";
        int i = 0;
        Iterator iter = epList.listIterator();
        while (iter.hasNext()) {
            value += Integer.toString(i + 1) + ":" + iter.next() + "\n";
            i++;
        }
        return value;
    }

    /**
     * Salva EmergingPatternMiner per serializzazione.
     *
     * @param nomeFile nome del file
     * @throws FileNotFoundException gestione delle eccezioni per i file non trovati
     *  @throws IOException gestionde delle eccezioni di IO
     */
    public void salva(String nomeFile) throws FileNotFoundException, IOException{
        FileOutputStream outFile = new FileOutputStream(nomeFile + ".dat");
        ObjectOutputStream outStream = new ObjectOutputStream(outFile);
        outStream.writeObject(this);
        outFile.close();
    }

    /**
     * Carica emerging pattern miner.
     *
     * @param nomeFile nome del file
     * @return EmergingPatternMiner
     * @throws FileNotFoundException gestione delle eccezioni per i file non trovati
     * @throws IOException gestione delle eccezioni di IO
     * @throws ClassNotFoundException gestione delle eccezioni per le classi non trovate
     */
    public static EmergingPatternMiner carica(String nomeFile) throws FileNotFoundException,IOException,ClassNotFoundException{
        FileInputStream inFile = new FileInputStream(nomeFile + ".dat");
        ObjectInputStream inStream = new ObjectInputStream(inFile);
        EmergingPatternMiner temp = (EmergingPatternMiner) inStream.readObject();
        inFile.close();
        return temp;
    }

    /**
     * @return iteratore
     */
    @Override
    public Iterator<EmergingPattern> iterator() {
        return null;
    }


}