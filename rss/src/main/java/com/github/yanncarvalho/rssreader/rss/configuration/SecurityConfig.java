package com.github.yanncarvalho.rssreader.rss.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
 


    /**
     * Security filter chain setup.
     * @param httpSecurity the httpSecurity allows configuring web based security for specific http requests.
     * @return httpSecurity with filter chain setup.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				  .csrf().disable()
				  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				  .and().authorizeHttpRequests(
						authorize -> authorize.anyRequest().permitAll())
				  .build();
	}

    /**
     * Authentication manager setup.
     * @param configuration the authentication configuration.
     * @return {@link AuthenticationConfiguration#getAuthenticationManager AuthenticationManager}.
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

  




}

