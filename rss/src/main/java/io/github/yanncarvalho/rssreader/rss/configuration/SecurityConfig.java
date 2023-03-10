package io.github.yanncarvalho.rssreader.rss.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
		 
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
 
	@Autowired
	private SecurityFilter filter;
	

	static final String[] WHITELIST = {
			  "/swagger-ui/**",
			  "/swagger-ui",
			  "/swagger-ui.html",
			  "/v3/api-docs/**",
			  "/swagger-ui.html/**",
			  "/configuration/**",
			  "/swagger-resources/**",
			  "/webjars/**"
	};

	/** Security filter chain setup.
     * @param httpSecurity the httpSecurity allows configuring web based security for specific http requests.
     * @return httpSecurity with filter chain setup.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				  .csrf().disable()
				  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				  .and()
				  .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
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

