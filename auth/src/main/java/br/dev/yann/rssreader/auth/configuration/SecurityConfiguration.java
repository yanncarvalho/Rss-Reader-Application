package br.dev.yann.rssreader.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.HandlerExceptionResolver;

import br.dev.yann.rssreader.auth.user.UserRole;

/**
 * Application security configuration
 * 
 * @author Yann Carvalho
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	/**
	 * System WhiteList 
	 */
	static final String[] AUTH_WHITELIST = {
    		"/save",
			"/login",
			"/swagger-ui",
			"/favicon.ico",
			"/swagger-ui.html",
			"/api-docs",
            // -- Swagger UI v2
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v2/api-docs",
            "/v3/api-docs",  
            "/swagger-resources/**", 
            "/swagger-ui/**",
            // other public endpoints of your API may be appended to this array
    };
	
	/**
	 * System admin role paths 
	 */
    static final String[] AUTH_ADMIN = {
			"/admin/**"
	};
    
    /**
     * Handler Exception Filter 
 	 * @see ExceptionFilter
     */
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
    
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
						authorize -> authorize
							.requestMatchers(AUTH_ADMIN).hasRole(UserRole.ADMIN.name())
							.requestMatchers(AUTH_WHITELIST).permitAll()
							.anyRequest().authenticated())
				  .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
				  .and().build();
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
    
    /**
     * Authentication entry point setup.
     * @return {@link ExceptionFilter}.
     */
    @Bean
    AuthenticationEntryPoint authenticationEntryPoint(){
        return new ExceptionFilter();
    }
    
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(AUTH_WHITELIST);
    }

}

