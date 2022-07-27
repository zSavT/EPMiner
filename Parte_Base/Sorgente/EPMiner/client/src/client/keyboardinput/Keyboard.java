package client.keyboardinput;//********************************************************************
//  client.keyboardinput.Keyboard.java       Author: Lewis and Loftus
//
//  Facilitates keyboard input by abstracting details about input
//  parsing, conversions, and exception handling.
//********************************************************************

import java.io.*;
import java.util.*;

/**
 * The type Keyboard.
 *
 * @author Lewis and Loftus classe per la gestionde del tipo Keyboard.
 */
public class Keyboard {
	// ************* Error Handling Section **************************

	/**
	 * Checker controllo errori
	 */
	private static boolean printErrors = true;

	/**
	 * Numero errori scovati
	 */
	private static int errorCount = 0;

	/**
	 * Gets error count.
	 *
	 * @return intero che indica il conteggio degli errori correnti
	 */
	public static int getErrorCount() {
		return errorCount;
	}

	/**
	 * azzera il conteggio degli errori corrente.
	 *
	 * @param count conteggio
	 */
	public static void resetErrorCount(int count) {
		errorCount = 0;
	}

	/**
	 * Restituisce un booleano che indica se gli errori di input sono attualmente stampati su output standard.
	 *
	 * @return valore booleano
	 */
	public static boolean getPrintErrors() {
		return printErrors;
	}

	/**
	 * Imposta booleano che indica se gli errori di input devono essere stampati su output standard
	 *
	 * @param flag valore booleano
	 */
	public static void setPrintErrors(boolean flag) {
		printErrors = flag;
	}

	/**
	 * incrementa il conteggio degli errori e stampa il messaggio di errore, se opportuno
	 *
	 * @param str oggetto di tipo String
	 */
	private static void error(String str) {
		errorCount++;
		if (printErrors)
			System.out.println(str);
	}

	// ************* Tokenized Input Stream Section ******************

	/**
	 * Token corrente
	 */
	private static String current_token = null;

	/**
	 * Reader token
	 */
	private static StringTokenizer reader;

	/**
	 * Inizializzazione bufferReader
	 */
	private static BufferedReader in = new BufferedReader(
			new InputStreamReader(System.in));

	/**
	 * ottiene il prossimo token di input assumendo che possa essere su linee di input successive.
	 * @return token successivo
	 */
	private static String getNextToken() {
		return getNextToken(true);
	}

	/**
	 * ottiene il prossimo token di input, che potrebbe essere già stato letto.
	 * @param skip controllo su lettura
	 * @return token successivo
	 */
	private static String getNextToken(boolean skip) {
		String token;

		if (current_token == null)
			token = getNextInputToken(skip);
		else {
			token = current_token;
			current_token = null;
		}

		return token;
	}

	/**
	 * @return token successivo
	 * @param skip valore booleano per il controllo della lettura.
	 * ottiene il token successivo dall'input, che può venire dalla linea di input corrente o da una successiva.
	 * Il parametro determina se vengono utilizzate linee successive.
	 */
	private static String getNextInputToken(boolean skip) {
		final String delimiters = " \t\n\r\f";
		String token = null;

		try {
			if (reader == null)
				reader = new StringTokenizer(in.readLine(), delimiters, true);

			while (token == null || ((delimiters.indexOf(token) >= 0) && skip)) {
				while (!reader.hasMoreTokens())
					reader = new StringTokenizer(in.readLine(), delimiters,
							true);

				token = reader.nextToken();
			}
		} catch (Exception exception) {
			token = null;
		}

		return token;
	}

	/**
	 * Restituisce true se non ci sono più token da leggere sulla linea di input corrente, false altrimenti
	 *
	 * @return valore boolean
	 */
	public static boolean endOfLine() {
		return !reader.hasMoreTokens();
	}

	// ************* Reading Section *********************************

	/**
	 * Read string string.
	 *
	 * @return stringa letta da standard input.
	 */
	public static String readString() {
		String str;

		try {
			str = getNextToken(false);
			while (!endOfLine()) {
				str = str + getNextToken(false);
			}
		} catch (Exception exception) {
			error("Error reading String data, null value returned.");
			str = null;
		}
		return str;
	}

	/**
	 * Read word string.
	 *
	 * @return sottostringa delimitata dallo spazio (una parola) letta dallo standard input.
	 */
	public static String readWord() {
		String token;
		try {
			token = getNextToken();
		} catch (Exception exception) {
			error("Error reading String data, null value returned.");
			token = null;
		}
		return token;
	}

	/**
	 * Read boolean boolean.
	 *
	 * @return valore booleano letto dallo standard input
	 */
	public static boolean readBoolean() {
		String token = getNextToken();
		boolean bool;
		try {
			if (token.toLowerCase().equals("true"))
				bool = true;
			else if (token.toLowerCase().equals("false"))
				bool = false;
			else {
				error("Error reading boolean data, false value returned.");
				bool = false;
			}
		} catch (Exception exception) {
			error("Error reading boolean data, false value returned.");
			bool = false;
		}
		return bool;
	}

	/**
	 * Read char char.
	 *
	 * @return valore char letto dallo standard input
	 */
	public static char readChar() {
		String token = getNextToken(false);
		char value;
		try {
			if (token.length() > 1) {
				current_token = token.substring(1, token.length());
			} else
				current_token = null;
			value = token.charAt(0);
		} catch (Exception exception) {
			error("Error reading char data, MIN_VALUE value returned.");
			value = Character.MIN_VALUE;
		}

		return value;
	}

	/**
	 * Read int int.
	 *
	 * @return valore intero letto dallo standard input
	 */
	public static int readInt() {
		String token = getNextToken();
		int value;
		try {
			value = Integer.parseInt(token);
		} catch (Exception exception) {
			error("Error reading int data, MIN_VALUE value returned.");
			value = Integer.MIN_VALUE;
		}
		return value;
	}

	/**
	 * Read long long.
	 *
	 * @return valore long integer letto dallo standard input
	 */
	public static long readLong() {
		String token = getNextToken();
		long value;
		try {
			value = Long.parseLong(token);
		} catch (Exception exception) {
			error("Error reading long data, MIN_VALUE value returned.");
			value = Long.MIN_VALUE;
		}
		return value;
	}

	/**
	 * Read float float.
	 *
	 * @return valore float letto dallo standard input
	 */
	public static float readFloat() {
		String token = getNextToken();
		float value;
		try {
			value = (new Float(token)).floatValue();
		} catch (Exception exception) {
			error("Error reading float data, NaN value returned.");
			value = Float.NaN;
		}
		return value;
	}

	/**
	 * Read double double.
	 *
	 * @return valore double letto dallo standard input
	 */
	public static double readDouble() {
		String token = getNextToken();
		double value;
		try {
			value = (new Double(token)).doubleValue();
		} catch (Exception exception) {
			error("Error reading double data, NaN value returned.");
			value = Double.NaN;
		}
		return value;
	}
}