package br.dev.yann.rssreader.auth.user.record;

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
	 * Usually with JWT this field is set as {@code "Bearer"}.
	 */	
    @Schema(example = "token_type") 
	@JsonProperty("token_type")
	String tokenType,
	
	/** 
	 * When token will expire.
	 * @see JwtService
	 */	
	@JsonProperty("expires_in")
	Date expirateTime) {}

