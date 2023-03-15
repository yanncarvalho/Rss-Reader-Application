package io.github.yanncarvalho.rssreader.rss.rss.record;

import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.REGEX_HTTP_HTTPS;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.REGEX_HTTP_HTTPS_MESSAGE;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

/**
 * Paged content response record.
 *
 * @param urls {@link #urls}.
 * 
 * @author Yann Carvalho
 */
public record ConvertToRssReq(
		/**Urls to be displayed wrapped in a List*/
       @NotEmpty
       List<@Pattern(regexp = REGEX_HTTP_HTTPS, message = REGEX_HTTP_HTTPS_MESSAGE) String> urls) {}
