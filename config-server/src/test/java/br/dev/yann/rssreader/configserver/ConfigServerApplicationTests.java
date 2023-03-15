package br.dev.yann.rssreader.configserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import io.github.yanncarvalho.rssreader.configserver.ConfigServerApplication;

@SpringBootTest
@ActiveProfiles("native")	
@ContextConfiguration(classes = ConfigServerApplication.class)
class ConfigServerApplicationTests {

	@Test
	@DisplayName("if app is healthy then initialize fine")
	void ifAppIsHeahtyThenInitializeFine() {}

}
