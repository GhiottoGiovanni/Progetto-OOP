package com.twitter.exceptions;

public class NegativeNumberException extends Exception{
	public NegativeNumberException() {
		super("NUMERO NEGATIVO INSERITO: INSERIRE SOLO NUMERI POSITIVI");
	}
}
