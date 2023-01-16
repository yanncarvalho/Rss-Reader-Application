package br.dev.yann.rssreader.auth.user.record;

import org.bouncycastle.util.Strings;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateReq(
	
	@Size(min = 3, max = 255) @NotBlank
	String name,
	boolean hasName,
		
	@Size(min = 3, max = 255) @NotBlank
	@Pattern(regexp = "^[A-Z0-9_\\.]+$") 
	String username,
	boolean hasUsername,
		
	@Size(min = 3, max = 255) @NotBlank
	String password,		
	boolean hasPassword){		
	
	@JsonCreator
	public UpdateReq( String name, String username, String password) {
		this(name == null ? "STANDARD_VALUE" : name, 
			name != null, 
			username == null ? "STANDARD_VALUE" : Strings.toUpperCase(username), 
			username != null, 
			password == null ? "STANDARD_VALUE" : password, 
			password != null);

	}



}
