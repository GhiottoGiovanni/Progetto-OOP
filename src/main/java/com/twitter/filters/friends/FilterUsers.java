package com.twitter.filters.friends;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.twitter.APIcaller.Caller;
import com.twitter.models.User;

/**
 * <b>Classe</b> che eredita da {@link Filter}. Contiene metodo della costruzione della struttura dove saranno inseriti i dati degli utenti.
 * @author Giovanni Ghiotto
 * @author Mihail Bobeica
 * @version 1.0
 */

public abstract class FilterUsers extends Filter{
	/**
	 * Inizializza la lista degli amici
	 * @param friends Lista di amici
	 */
	
	public FilterUsers(ArrayList<User> friends) {
		super(friends);
	}
	
	/**
	 * Metodo che filtra la lista degli amici.
	 * @param <T> Tipo del parametro inserito.
	 * @param x Parametro del filtro.
	 * @return Lista filtrata degli amici.
	 */
	public abstract <T> ArrayList<User> filter(T x);

	/**
	 * @param filteredUsers Lista filtrata degli amici.
	 * @return Stringa Json contenente i risultati.
	 */
	public static String structureData(ArrayList<User> filteredUsers) {
		ArrayNode data = Caller.OBJECT_MAPPER.createArrayNode();
		int result_count = 0;
		for (User u : filteredUsers) {
			data.add(Caller.OBJECT_MAPPER.convertValue(u, JsonNode.class));
			result_count++;
		}
		return structureData(data, result_count);
	}
}
