package io.hk.webApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ServletComponentScan
@EnableScheduling
@SpringBootApplication(exclude = {MongoAutoConfiguration.class,MongoDataAutoConfiguration.class})
public class webApp implements WebMvcConfigurer{

	public static void main(String[] args) {

		SpringApplication sa= new SpringApplication(webApp.class);
		//sa.addListeners(new StartListener());
		sa.run(args);

		//CacheHelp.set("fda", "33333333333333", 60000);

		//Test.run();
	}

	/**
	 * 开启跨域
	 * @param registry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowCredentials(true)
				.allowedHeaders("*")
				.allowedOrigins("*")
				.allowedMethods("*");
	}


}
