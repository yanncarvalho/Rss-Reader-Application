package io.github.yanncarvalho.rssreader.rss.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.yanncarvalho.rssreader.rss.rss.Rss;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

@Configuration
public class JAXBConfig {

	@Bean
	JAXBContext context() throws JAXBException {
	    return JAXBContext.newInstance(Rss.class);
	}
}
