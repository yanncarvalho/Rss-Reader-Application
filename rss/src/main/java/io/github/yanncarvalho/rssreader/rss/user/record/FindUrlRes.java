package io.github.yanncarvalho.rssreader.rss.user.record;

import java.util.List;
import java.util.UUID;

/**
 * Find Url response
 *
 * @param id {@link #id}.
 * @param url {@link #url}.
 * 
 * @author Yann Carvalho
 */
public record FindUrlRes(
		
		 /**User id*/
		UUID id,
		
		 /**User urls*/
		List<String> url) {}
