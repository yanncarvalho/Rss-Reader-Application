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

@Service
public class JWTToken {

  @Value("${jwt.token.duration-in-days}")
  private Integer expiryTime;
	
  @Value("${jwt.token.secret}")
  private String secret;
	
  @Value("${jwt.token.issuer}")
  private String issuer;
	
  public String getToken(User user) {

    try {

      var instantNow = LocalDateTime.now().plusDays(expiryTime).toInstant(ZoneOffset.UTC);
      var header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();
      var playload = new JWTClaimsSet.Builder()
                                    .issuer(issuer)
                                    .subject(user.getId().toString())
                                    .expirationTime(Date.from(instantNow))
                                    .build();

      var signer = new MACSigner(secret.getBytes());
      var signedJWT = new SignedJWT(header, playload);
      signedJWT.sign(signer);

      return signedJWT.serialize();

    } catch (JOSEException e) {
    	throw new JwtEncodingException("Error generating JWT", e);
    }
  }

  private boolean isTokenValid(String token, byte[] secret) throws ParseException, JOSEException {
    JWSObject jwsObject = JWSObject.parse(token);
    JWSVerifier verifier = new MACVerifier(secret);
    return jwsObject.verify(verifier);
  }

  public JWTClaimsSet getClaims(String token){
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
