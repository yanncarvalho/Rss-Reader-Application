package com.github.yanncarvalho.rssreader.rss.configuration;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class SpringAsyncConfig {
	
	@Value("${thread.poolsize}")
	private int poolSize;
	
	@Value("${thread.maxpoolsize}")
	private int maxPoolSize;
	
	@Value("${thread.nameprefix}")
	private String namePrefix;
	
    @Bean
    Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setThreadNamePrefix(namePrefix);
        executor.initialize();
        return executor;
    }

}
