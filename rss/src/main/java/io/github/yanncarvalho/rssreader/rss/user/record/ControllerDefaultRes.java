package io.github.yanncarvalho.rssreader.rss.user.record;

import io.github.yanncarvalho.rssreader.rss.user.UserController;

/**
 * {@link UserController} Default requese record.
 *
 * @param message {@link #message}.
 *
 * @author Yann Carvalho
 */
public record ControllerDefaultRes(

		/**Default message*/
		String message) {}
