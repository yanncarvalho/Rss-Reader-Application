package br.dev.yann.rssreader.auth.user.record;

import org.bouncycastle.util.Strings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SaveReq(
		@Size(min = 3, max = 255) @NotBlank
		@Pattern(regexp = "^[A-Z0-9_\\.]+$") 
		String username,
		@Size(min = 3, max = 255) @NotBlank
		String password,
		@Size(min = 3, max = 255) @NotBlank
		String name
		) {
	
	  public SaveReq(String username, String password, String name) {
	        this.username = Strings.toUpperCase(username);
	        this.password = password;
	        this.name = name;
	    }

}

