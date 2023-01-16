package br.dev.yann.rssreader.auth.user.record;

import jakarta.validation.constraints.NotBlank;

public record UserDefaultRes(
		@NotBlank
		String Message
		) {

}
