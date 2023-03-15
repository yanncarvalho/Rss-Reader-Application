package io.github.yanncarvalho.rssreader.rss.user.record;

import java.util.List;

/**
 * Not Found Urls Response
 *
 * @param notFoundRss {@link #notFoundRss}.
 * 
 * @author Yann Carvalho
 */
public record AddRes(
		 /**Rss not found to be displayed wrapped in a List*/
		List<String> notFoundRss) {}