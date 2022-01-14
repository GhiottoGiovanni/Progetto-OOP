package com.twitter.filters.friends;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.twitter.APIcaller.Caller;
import com.twitter.models.User;

/**
 * Classe che eredita da {@link Filter}. Contiene metodo della costruzione della struttura dove saranno inseriti i dati degli utenti.
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
	 * @return Ritorna i dati strutturati.
	 */
	public static String structureData(ArrayNode data, int result_count) {
		ObjectNode root = Caller.OBJECT_MAPPER.createObjectNode();
		
		ObjectNode meta = Caller.OBJECT_MAPPER.createObjectNode();
		meta.put("result_count", result_count);
		
		root.set("data", data);
		root.set("meta", meta);
		try {
			return Caller.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(root);
		} catch (JsonProcessingException e) {
			e.toString();
			e.printStackTrace();
			return e.toString();
		}
	}
}
