package com.github.yanncarvalho.rssreader.cloudgateway;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import io.github.yanncarvalho.rssreader.cloudgateway.CloudGatewayApplication;

@SpringBootTest
@ActiveProfiles("test")	
@ContextConfiguration(classes = CloudGatewayApplication.class)
class CloudGatewayApplicationTests {

	@Test
	@DisplayName("if app is healthy then initialize fine")
	void ifAppIsHeahtyThenInitializeFine() {}

}