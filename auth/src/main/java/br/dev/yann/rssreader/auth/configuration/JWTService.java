package br.dev.yann.rssreader.auth.configuration;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;

import br.dev.yann.rssreader.auth.user.User;

/**
 * JWT service to encode and decode.
 * 
 * @author Yann Carvalho
 */
@Service
public class JWTService {

  /**
   * Token duration in days.
   */	
  @Value("${jwt.token.duration-in-days}")
  private int expiryTime;
  
  /**
   * Token secret. The secret must be at least 256 bits long 
   * and not {@code null}.
   */	
  @Value("${jwt.token.secret}")
  private String secret;

  /**
   * Application issuer.
   */
  @Value("${jwt.token.issuer}")
  private String issuer;

  /**
   * <p> 
   *    Encode JWT. 
   * </p>
   * <p>
   *   The token is generated with claims:
   *   <ul>
   *     <li>iss - {@link Issuer}
   *     <li>sub - {@link User#getId User id}
   *     <li>exp - {@link expiryTime Expiry Time}
   * 	</ul>
   * </p>
   *  @param user user that requires token.
   *  @return String with token.
   *  @throws JwtEncodingException if unable to generate JWT.
   *  @see #decode
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

      var signer = new MACSigner(secret.getBytes());
      var signedJWT = new SignedJWT(header, playload);
      signedJWT.sign(signer);

      return signedJWT.serialize();

    } catch (JOSEException e) {
    	throw new JwtEncodingException("Error generating JWT", e);
    }
  }

  /**
   *  Checks the signature of Token with the specified verifier.
   *  
   *  @param token token.
   *  @param secret {@link secret token secret}.
   *  @return {@code true} if token is valid {@code false} if is not.
   *  
   */
  private boolean isTokenValid(String token, byte[] secret) throws ParseException, JOSEException {
    JWSObject jwsObject = JWSObject.parse(token);
    JWSVerifier verifier = new MACVerifier(secret);
    return jwsObject.verify(verifier);
  }

  /**
   *  Decode token.
   *   
   *  @param token token.
   *  @return {@link JWTClaimsSet} with JWT specification.
   *  @throws BadJwtException if token does not have any claim
   *  set correctly as in {@link #encode} or {@link #isTokenValid is token not valid}.
   */
  public JWTClaimsSet decode(String token){
	    try {
	        JWTClaimsSet claims = JWTParser.parse(token).getJWTClaimsSet();
	        
	        var exp = claims.getExpirationTime();
	        var instantNow = Instant.now().atOffset(ZoneOffset.UTC);
	          
			if((exp == null || exp.toInstant().atOffset(ZoneOffset.UTC).isAfter(instantNow))
	           && (claims.getIssuer() == null || !claims.getIssuer().equals(issuer))
	           &&  claims.getSubject() == null 
	           && !isTokenValid(token, secret.getBytes())) {
				throw new JOSEException();
	        }
	      
	        return claims;
	      } catch (ParseException | JOSEException e) {
	          throw new BadJwtException("Error validating JWT", e);
	      }
  }
  
}
