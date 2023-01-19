package br.dev.yann.rssreader.auth.user.record;

import static br.dev.yann.rssreader.auth.configuration.DefaultValue.JWT_BEARER;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * JWT response record.
 *
 * @param acessToken {@link #acessToken}.
 * @param tokenType {@link #tokenType}.
 * @param expirateTime {@link #expirateTime}.
 * 
 * @author Yann Carvalho
 */
public record JwtRes(
	/** 
	 * Token generated.
	 * @see JwtService
	 */	
    @Schema(example = "token") 
	@JsonProperty("access_token")
	String token,
	
	/** 
	 * Token type.
	 * Usually with JWT this field is set as {@link DefaultValue#JWT_BEARE}.
	 */	
    @Schema(example = JWT_BEARER) 
	@JsonProperty("token_type")
	String tokenType,
	
	/** 
	 * When token will expire.
	 * @see JwtService
	 */	
	@JsonProperty("expires_in")
	Date expirateTime) {}

