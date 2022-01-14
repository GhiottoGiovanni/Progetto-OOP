package com.twitter.exceptions;

public class NotExistingAccountException extends Exception{
	public NotExistingAccountException(String username){
		super("Non esiste nessun account Twitter con il seguente nome identificativo: " + username);
	}
}
