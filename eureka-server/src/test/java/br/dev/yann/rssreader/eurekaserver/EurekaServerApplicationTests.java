package br.dev.yann.rssreader.eurekaserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import io.github.yanncarvalho.rssreader.eurekaserver.EurekaServerApplication;

@SpringBootTest
@ActiveProfiles("test")	
@ContextConfiguration(classes = EurekaServerApplication.class)
class EurekaServerApplicationTests {

	@Test
	@DisplayName("if app is healthy then initialize fine")
	void ifAppIsHeahtyThenInitializeFine() {}
}
