package io.github.yanncarvalho.rssreader.rss.rss;

import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.SWAGGER_CONVERT_TO_RSS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.yanncarvalho.rssreader.rss.rss.record.ConvertToRssRes;
import io.github.yanncarvalho.rssreader.rss.user.record.RssReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequestMapping("v1")
@Tag(name = "Rss")
public class RssController {

  @Autowired
  private RssService service;
	
  @PostMapping(value = "convertToRss", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Operation(description = SWAGGER_CONVERT_TO_RSS)
  public ConvertToRssRes convertToRss(@RequestParam(defaultValue = "0") @PositiveOrZero Integer page,
		  @RequestParam(defaultValue = "5") @PositiveOrZero Integer size,
		  @RequestBody @NotNull RssReq rss) {

	Page<Rss> findRssByUrls = service.findRssByUrls(rss.urls(), PageRequest.of(page, size));
	var urlsFound = findRssByUrls.map(Rss::getOriginalLink).toList();
	var urlsNotFound = rss.urls();
	urlsNotFound.removeAll(urlsFound);
	return new ConvertToRssRes(findRssByUrls, urlsNotFound);
  }
}



