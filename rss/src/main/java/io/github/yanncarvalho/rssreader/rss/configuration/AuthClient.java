package io.github.yanncarvalho.rssreader.rss.configuration;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("${application.auth.slug}")
public interface AuthClient {
	@GetMapping("/${application.auth.slug-find}")
	Map<String, String> getAuthUser(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

}
