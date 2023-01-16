package br.dev.yann.rssreader.auth.role.record;

import jakarta.validation.constraints.NotBlank;

public record SaveReq(
		@NotBlank
		String name) {}