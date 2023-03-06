package com.github.yanncarvalho.rssreader.rss.rss;

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

import com.github.yanncarvalho.rssreader.rss.user.record.RssReq;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequestMapping("v1")
public class RssController {

  @Autowired
  private RssService service;

  @PostMapping(value = "convertToRss", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Page<Rss> convertToRss(@RequestParam(defaultValue = "0") @PositiveOrZero Integer page,
		  @RequestParam(defaultValue = "5") @PositiveOrZero Integer size,
		  @RequestBody @NotNull RssReq rss) {
	return service.findRssByUrls(rss.urls(), PageRequest.of(page, size));
  }
}



