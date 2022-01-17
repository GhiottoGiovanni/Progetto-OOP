package com.twitter.exceptions;

/**
 * <b>Eccezione</b> che avvisa quando vengono inseriti valori negativi.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public class NegativeNumberException extends Exception{
	public NegativeNumberException(String trace) {
		super("NUMERO NEGATIVO INSERITO: " + trace + "\nINSERIRE SOLO NUMERI POSITIVI!");
	}
}
