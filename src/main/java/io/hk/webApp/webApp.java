package io.hk.webApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import io.framecore.Web.Listener.StartListener;
import io.framecore.redis.CacheHelp;

@ServletComponentScan
@SpringBootApplication(exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
public class webApp {
	
	public static void main(String[] args) {
		
		SpringApplication sa= new SpringApplication(webApp.class);
		//sa.addListeners(new StartListener());
		sa.run(args);

		//CacheHelp.set("fda", "33333333333333", 60000);
		
		//Test.run();
	}

}
