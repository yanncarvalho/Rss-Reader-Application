package io.github.yanncarvalho.rssreader.rss.user.record;

import java.util.List;
/**
 * Has Url Response
 *
 * @param urlsFound {@link #urlsFound}.
 * 
 * @author Yann Carvalho
 */
public record HasUrlRes(
		 /**Urls found to be displayed wrapped in a List*/
		List<String> urlsFound) {}
