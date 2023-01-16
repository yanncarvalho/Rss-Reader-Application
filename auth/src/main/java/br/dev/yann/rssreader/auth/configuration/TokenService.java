package br.dev.yann.rssreader.auth.configuration;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
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
public class TokenService {

  @Value("${jwt.token.duration-in-days}")
  private Integer expiryTime;
	
  @Value("${jwt.token.secret}")
  private String secret;
	
  @Value("${jwt.token.issuer}")
  private String issuer;
	
  public String getToken(User user) {

    try {

      long daysFromNow = new Date().getTime() + TimeUnit.DAYS.toMillis(expiryTime);

      var header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();
      var claims = new JWTClaimsSet.Builder()
                                    .issuer(issuer)
                                    .subject(user.getId().toString())
                                    .expirationTime(new Date(daysFromNow))
                                    .build();

      var signer = new MACSigner(secret.getBytes());

      var signedJWT = new SignedJWT(header, claims);

      signedJWT.sign(signer);

      return signedJWT.serialize();

    } catch (JOSEException e) {
    	throw new RuntimeException("erro ao gerar token jwt", e);
    }
  }

  private boolean isTokenValid(String token, byte[] secret) throws ParseException, JOSEException {
    JWSObject jwsObject = JWSObject.parse(token);
    JWSVerifier verifier = new MACVerifier(secret);
    return jwsObject.verify(verifier);
  }

  public JWTClaimsSet getClaims(String token) {

	    try {
	        JWTClaimsSet claims = JWTParser.parse(token).getJWTClaimsSet();
	 
	        if(claims.getExpirationTime() == null || claims.getExpirationTime().before(new Date())){
	          return null;
	        }

	        if(claims.getIssuer() == null || !claims.getIssuer().equals(issuer)){
	          return null;
	        }

	        if(claims.getSubject() == null){
	          return null;
	        } 

	        if (!isTokenValid(token, secret.getBytes())) {
	          return null;
	        }
	      
	        return claims;
	      } catch (ParseException | JOSEException e) {
	        return null;

	      }
  }
  
}
