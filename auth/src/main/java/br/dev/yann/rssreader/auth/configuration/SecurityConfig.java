package br.dev.yann.rssreader.auth.configuration;

import org.apache.commons.lang3.ArrayUtils;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import br.dev.yann.rssreader.auth.user.UserRole;

/**
 * Application security configuration
 *
 * @author Yann Carvalho
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	/**
	 * Swagger Whitelist
	 */
	static final String[] SWAGGER_WHITELIST = {
			  "/swagger-ui/**",
			  "/swagger-ui.html",
			  "/v3/api-docs/**",
			  "/swagger-ui.html/**",
			  "/configuration/**",
			  "/swagger-resources/**",
			  "/webjars/**"
	};

	/**
	 * Controller whitelist
	 */
	static final String[] CONTROLLER_WHITELIST = {
    		"/v*/save",
			"/v*/login"
	};

	/**
	 * All elements of Whitelist
	 */
	static final String[] AUTH_WHITELIST = ArrayUtils.addAll(
			CONTROLLER_WHITELIST,
			SWAGGER_WHITELIST);
	/**
	 * System admin role paths
	 */
    static final String[] AUTH_ADMIN = {
			"/v*/admin/**"
	};

    /**
     * JWT Filter
     * @see JwtFilter
     */
    @Autowired
    private JwtFilter jwtFilter;

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
							.requestMatchers(SecurityConfig.AUTH_ADMIN).hasRole(UserRole.ADMIN.name())
							.requestMatchers(SecurityConfig.CONTROLLER_WHITELIST).permitAll()
							.anyRequest().authenticated())
				  .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
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

    /**
     * Ignore the swagger endpoints in WebSecurity
     *
     * @return WebSecurityCustomizer with endpoints to ignore
     */
    @Bean
    WebSecurityCustomizer ignoringCustomizer() {
        return web -> web.ignoring().requestMatchers(SecurityConfig.SWAGGER_WHITELIST);
    }



}

