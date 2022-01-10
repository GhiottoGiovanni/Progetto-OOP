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
				}
		    }
		});
	}
}
