package br.dev.yann.rssreader.auth.user.record;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JWT response record.
 *
 * @param acessToken {@link #acessToken}.
 * @param tokenType {@link #tokenType}.
 * @param expirateTime {@link #expirateTime}.
 * 
 * @author Yann Carvalho
 */
public record JWTRes(
	/** 
	 * Token generated.
	 * @see JWTService
	 */	
	@JsonProperty("access_token")
	String token,
	
	/** 
	 * Token type.
	 * Usually with JWT this field is set as {@code "Bearer"}.
	 */	
	@JsonProperty("token_type")
	String tokenType,
	
	/** 
	 * When token will expire.
	 * @see JWTService
	 */	
	@JsonProperty("expires_in")
	Date expirateTime) {}

