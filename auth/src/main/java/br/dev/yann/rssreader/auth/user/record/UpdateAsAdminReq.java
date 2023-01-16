package br.dev.yann.rssreader.auth.user.record;

import java.util.UUID;

import org.bouncycastle.util.Strings;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateAsAdminReq(
		@NotNull
		UUID id,
		
		@Size(min = 3, max = 255) @NotBlank
		String name,
		boolean hasName,
		
		@Size(min = 3, max = 255) @NotBlank
		@Pattern(regexp = "^[A-Z0-9_\\.]+$") 
		String username,
		boolean hasUsername,
		
		@NotBlank
		String role,
		boolean hasRole) {

	@JsonCreator
	public UpdateAsAdminReq(UUID id, String name, String username, String role) {
		this(id,
			name == null ? "STANDARD_VALUE" : name, 
			name != null, 
			username == null ? "STANDARD_VALUE" : Strings.toUpperCase(username), 
			username != null, 
			role == null ? "STANDARD_VALUE" : Strings.toUpperCase(role), 
			role != null);

	}

}
