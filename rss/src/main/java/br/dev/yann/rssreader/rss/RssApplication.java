package br.dev.yann.rssreader.rss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RssApplication {	
	public static void main(String[] args) {
		SpringApplication.run(RssApplication.class, args);
	}
}