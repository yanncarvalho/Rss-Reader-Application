package br.dev.yann.rssreader.auth.user.record;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JWTRes(
	@JsonProperty("access_token")
	String acessToken,
	@JsonProperty("token_type")
	String tokenType,
	@JsonProperty("expires_in")
	Date expirateTime) {}

