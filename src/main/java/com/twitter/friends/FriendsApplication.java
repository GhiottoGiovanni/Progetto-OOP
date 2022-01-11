package com.twitter.friends;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.twitter.models.TwitterUser;

@SpringBootApplication
public class FriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendsApplication.class, args);
		
		// TODO aggiungere un file con le seguenti opzioni
		/* scegliere se fare il caricamento da locale oppure sempre dall'api
		 * ...
		 * */
		
		// AFFINCHE' IL CODICE SOTTO VENGA ESEGUITO E' NECESSARIO LANCIARE L'APPLICAZIONE COME SPRING BOOT APPLICATION
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
		    @Override
		    public void run()
		    {
		        // delete stored data
		    	try {
					FileUtils.cleanDirectory(Paths.get(TwitterUser.FRIENDS_STORAGE_DIR).toFile());
					System.out.println("App: file temporanei cancellati con successo!");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
					System.out.println("qualcosa è andato storto nella cancellazione dei file temporanei");
				}
		    }
		});
	}
}
