package com.github.yanncarvalho.rssreader.auth.configuration;

import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.JWT_BAD_EXCEPTION;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.JWT_ENCODE_EXCEPTION;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.github.yanncarvalho.rssreader.auth.user.User;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.oauth2.sdk.id.Issuer;

/**
 * JWT service to encode and decode.
 *
 * @author Yann Carvalho
 */
@Service("jwtService")
public class JwtService {
	
	/**
	 * Token expiry time in days
	 */
	@Value("${jwt.token.duration-in-hours}")
	private int expiryTime;

	/**
	 * JWT signer
	 */
	private Algorithm signer;
	
	/**
	 * Application issuer.
	 */
	@Value("${jwt.token.issuer}")
	private String issuer;
	
	/**
	 * Construct
	 * 
	 * @param secret value used to calculate a SHA256 digest and assign the value to {@link #secret}
	 * 
	 * @see DigestUtils#sha256
	 */
	public JwtService(@Value("${jwt.token.secret}") String secret) {
		this.signer = Algorithm.HMAC256(secret);
		
	}
	
	/**
	 * <p>
	 * Encode JWT.
	 * </p>
	 * <p>
	 * The token is generated with claims:
	 * <ul>
	 * <li>iss - {@link Issuer}
	 * <li>sub - {@link User#getId User id}
	 * <li>exp - {@link expiryTime Expiry Time}
	 * </ul>
	 * </p>
	 * 
	 * @param user user that requires token.
	 * @return String with token.
	 * @throws JwtEncodingException if unable to generate JWT.
	 * @see #decode
	 */
	public String encode(User user) {

		try {

			
			var instantExpiration = LocalDateTime.now().plusDays(expiryTime).toInstant(ZoneOffset.UTC);
            return JWT.create()
                    .withIssuer(issuer)
                    .withSubject(user.getId().toString())
                    .withExpiresAt(instantExpiration)
                    .sign(signer);
		} catch (JWTCreationException e) {
			throw new JwtEncodingException(JWT_ENCODE_EXCEPTION, e);
		}
	}

	/**
	 * Decode token.
	 *
	 * @param token token.
	 * @return {@link JWTClaimsSet} with JWT specification.
	 * @throws BadJwtException if token does not have any claim set correctly as in
	 *                         {@link #encode} or the signature of Token is not valid.
	 */
	public DecodedJWT decode(String token) {
		try {
	           return JWT.require(signer)
	                    .withIssuer(issuer)
	                    .build()
	                    .verify(token);
		} catch (JWTVerificationException e) {
			throw new BadJwtException(JWT_BAD_EXCEPTION, e);
		}
	}

}