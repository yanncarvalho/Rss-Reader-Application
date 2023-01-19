package br.dev.yann.rssreader.auth.user.record;

import br.dev.yann.rssreader.auth.user.UserController;
import jakarta.validation.constraints.NotBlank;

/**
 * {@link UserController} Default requese record.
 *
 * @param message {@link #message}.
 *
 * @author Yann Carvalho
 */
public record ControllerDefaultRes(

		/**Default message*/
		@NotBlank
		String message) {}
