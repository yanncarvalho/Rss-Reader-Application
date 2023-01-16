package br.dev.yann.rssreader.auth.role.record;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateReq(
		
		@NotNull
		UUID id,
		
		@Size(min = 3, max = 255) @NotBlank
		String name,
		
		boolean hasName,
				
		@NotNull
		Boolean flagActive,		
		
		boolean hasFlagActive
		){		
	@JsonCreator
	public UpdateReq(UUID id, String name, Boolean flagActive) {
		
		this(
			id,
			name == null ? "STANDARD_VALUE" : name, 
			name != null, 
			flagActive != null && flagActive, 
			flagActive != null
	  );
		}

	}

