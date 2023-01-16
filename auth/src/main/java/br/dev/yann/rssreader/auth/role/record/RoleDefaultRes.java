package br.dev.yann.rssreader.auth.role.record;

import jakarta.validation.constraints.NotBlank;

public record RoleDefaultRes(
		@NotBlank
		String Message) {}