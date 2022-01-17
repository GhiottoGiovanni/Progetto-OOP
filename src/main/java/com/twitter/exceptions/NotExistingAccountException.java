package com.twitter.exceptions;

/**
 * <b>Eccezione</b> che avvisa quando non esiste l'utente nell'API Twitter.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 *@version 1.0
 */
public class NotExistingAccountException extends Exception{
	/**
	 * @param username Nome identificativo di un utente.
	 */
	public NotExistingAccountException(String username){
		super("NON ESISTE NESSUN ACCOUNT TWITTER CON IL SEGUENTE NOME IDENTIFICATIVO: " + username);
	}
}
