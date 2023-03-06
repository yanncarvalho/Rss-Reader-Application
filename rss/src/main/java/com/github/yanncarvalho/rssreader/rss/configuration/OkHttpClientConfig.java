package com.github.yanncarvalho.rssreader.rss.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import okhttp3.OkHttpClient;

@Configuration
public class OkHttpClientConfig {
	
	@Value("${okhttp.connect-timeout-in-minutes}")
	private long connectTimeout;
	
	@Value("${okhttp.read-timeout-in-minutes}")
	private long readTimeout;
	
	@Value("${okhttp.call-timeout-in-minutes}")
	private long callTimeout;
	
    @Bean(name = "okHttpClient")
    OkHttpClient client() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(connectTimeout, TimeUnit.MINUTES)
                .readTimeout(readTimeout, TimeUnit.MINUTES)
                .callTimeout(callTimeout, TimeUnit.MINUTES)
                .build();
    }

}
