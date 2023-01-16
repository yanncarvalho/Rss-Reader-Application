package br.dev.yann.rssreader.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import br.dev.yann.rssreader.auth.user.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
	
    static String [] permitAllPaths = {
    		"/save",
			"/login"
    };
    
    static String[] adminPaths = {
			"/admin/**"
	};
    
	@Autowired
	private JWTFilter jwtFilter;

	
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;
    
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				  .csrf().disable()
				  .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				  .and().authorizeHttpRequests(
						authorize -> authorize
							.requestMatchers(adminPaths).hasRole(UserRole.ADMIN.toString())
							.requestMatchers(SecurityConfigurations.permitAllPaths).permitAll()
							.anyRequest().authenticated())
				  .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				  .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
				  .and().build();
	}
    
	
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    AuthenticationEntryPoint authenticationEntryPoint(){
        return new ExceptionFilter();
    }
    
   

}

