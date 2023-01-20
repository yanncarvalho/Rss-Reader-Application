package br.dev.yann.rssreader.auth.configuration;

import static br.dev.yann.rssreader.auth.configuration.DefaultValue.JWT_BAD_EXCEPTION;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.JWT_ENCODE_EXCEPTION;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.oauth2.sdk.id.Issuer;

import br.dev.yann.rssreader.auth.user.User;

/**
 * JWT service to encode and decode.
 *
 * @author Yann Carvalho
 */
@Service
public class JwtService {
	
	/**
	 * Token expiry time in days
	 */
	@Value("${jwt.token.duration-in-days}")
	private int expiryTime;

	/**
	 * Token Secret
	 */
	private byte [] secret;

	/**
	 * Application issuer.
	 */
	@Value("${jwt.token.issuer : ''}")
	private String issuer;
	
	/**
	 * Construct
	 * 
	 * @param secret value used to calculate a SHA256 digest and assign the value to {@link #secret}
	 * 
	 * @see DigestUtils#sha256
	 */
	public JwtService(@Value("${jwt.token.secret}") String secret) {
		this.secret = DigestUtils.sha256(secret);
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
			var header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();
			var playload = new JWTClaimsSet.Builder()
										   .issuer(issuer)
										   .subject(user.getId().toString())
										   .expirationTime(Date.from(instantExpiration))
										   .build();
			
			var signedJWT = new SignedJWT(header, playload);
			signedJWT.sign(new MACSigner(secret));

			return signedJWT.serialize();

		} catch (JOSEException e) {
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
	public JWTClaimsSet decode(String token) {
		try {
  			var jwt = SignedJWT.parse(token);
  			
			var claims = JWTClaimsSet.parse(jwt.getPayload().toJSONObject());
			var exp = claims.getExpirationTime();
			var instantNow = Instant.now().atOffset(ZoneOffset.UTC);	

			if (exp == null || exp.toInstant().atOffset(ZoneOffset.UTC).isAfter(instantNow)
				|| claims.getIssuer() == null || !claims.getIssuer().equals(issuer) 
				|| StringUtils.isBlank(claims.getSubject())
				|| !jwt.verify(new MACVerifier(secret))) {
				throw new JOSEException();
			}
			
			return claims;
		} catch (ParseException | JOSEException e) {
			throw new BadJwtException(JWT_BAD_EXCEPTION, e);
		}
	}

}
