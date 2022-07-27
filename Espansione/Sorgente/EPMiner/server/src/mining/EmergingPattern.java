package mining;

import java.io.*;


/**
 * classe per la gestione del tipo EmergingPattern.
 * @see FrequentPattern
 */
public class EmergingPattern extends FrequentPattern implements Serializable {
    /**
     * Grow rate del pattern
     */
    private float growrate;

    /**
     * costruttore di EmergingPattern che richiama il costruttore della classe madre.
     *
     * @param fp       FrequentPattern
     * @param growrate valore del growrate
     */
    EmergingPattern(FrequentPattern fp, float growrate){
        super(fp);
        this.growrate=growrate;
    }

    /**
     * @return growrate
     */
    public float getGrowrate(){
        return growrate;
    }

    /**
     * metodo per la stampa della classe
     * @return String
     */
    public String toString(){
        return super.toString() + "[" + growrate + "]";
    }

    public void salva(String nomeFile) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeFile));
        out.writeObject(this);
        out.close();
    }

    /**
     * Carica emerging pattern.
     *
     * @param nomeFile nome del file
     * @return EmergingPattern
     * @throws IOException gestione delle eccezioni IO
     * @throws ClassNotFoundException gestione Eccezzione classe non trovata
     */
    public static EmergingPattern carica(String nomeFile) throws IOException,ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeFile));
        EmergingPattern temp = (EmergingPattern) in.readObject();
        in.close();
        return temp;
    }
}

