package br.dev.yann.rssreader.auth.configuration;

import static br.dev.yann.rssreader.auth.configuration.DefaultValue.APP_LICENSE;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.JWT_BEARER;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_SECURITY_DESCRIPTION;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Swagger configurations
 *
 * @author Yann Carvalho
 */
@Configuration
public class SwaggerConfig {

	/**
	 * Swagger Title
	 */
	@Value("${spring.application.name}")
	private String title;

	/**
	 * Application version
	 */
    @Value("${application.version}")
    private String version;

	/**
	 * Security Scheema
	 */
    private String securitySchema = "bearerAuth";

	/**
	 * Application license link
	 */
    private String licenseUrl = APP_LICENSE;
   

    /**
     * Configure the OpenAPI components.
     *
     * @return Returns fully configure OpenAPI object
     * @see OpenAPI
     */
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI()
        		.info(new Info().title(title)
                       .contact(new Contact().name("Yann Carvalho")).version(version)
                       .license(new License()
                    		   .name("Apache 2.0")
                    		   	.url(licenseUrl)))
        	     .addSecurityItem(new SecurityRequirement()
                         .addList(securitySchema))
                 .components(new Components()
                         .addSecuritySchemes(securitySchema, new SecurityScheme()
                                 .name(securitySchema)
                                 .type(SecurityScheme.Type.HTTP)
                                 .scheme(JWT_BEARER.toLowerCase())
                                 .description(SWAGGER_SECURITY_DESCRIPTION)
                                 .bearerFormat("JWT")));
    }




}



