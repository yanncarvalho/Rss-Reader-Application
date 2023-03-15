package io.github.yanncarvalho.rssreader.rss.user;

import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.ATTRIBUTE_UUID;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.RSS_DELETED;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.RSS_DELETED_ALL;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.SWAGGER_DELETE_ALL_RSS;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.SWAGGER_DELETE_RSS;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.SWAGGER_FIND_URL;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.SWAGGER_GET_CONTENT;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.SWAGGER_HAS_URL;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.SWAGGER_INSERT_RSS;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.yanncarvalho.rssreader.rss.rss.Rss;
import io.github.yanncarvalho.rssreader.rss.user.record.AddRes;
import io.github.yanncarvalho.rssreader.rss.user.record.ControllerDefaultRes;
import io.github.yanncarvalho.rssreader.rss.user.record.FindUrlRes;
import io.github.yanncarvalho.rssreader.rss.user.record.HasUrlRes;
import io.github.yanncarvalho.rssreader.rss.user.record.PageRes;
import io.github.yanncarvalho.rssreader.rss.user.record.RssReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("v1")
@Tag(name = "Rss")
public class UserController {
	  
  @Autowired
  private UserService service;

  @GetMapping(value = "getContent", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(description = SWAGGER_GET_CONTENT)
  @ResponseStatus(HttpStatus.OK)
  public PageRes<Rss> getRss(@RequestParam(defaultValue = "0")  @Valid @NotNull @PositiveOrZero Integer page,
		          @RequestParam(defaultValue = "5")  @Valid @NotNull  @PositiveOrZero Integer size,
		          @RequestAttribute(name = ATTRIBUTE_UUID) UUID userId) {
	    var paged = service.getRss(userId, PageRequest.of(page, size));
	    return new PageRes<>(paged);
  }

  @GetMapping(value = "findUrl", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Operation(description = SWAGGER_FIND_URL)
  public FindUrlRes findUrl(@RequestAttribute(name = ATTRIBUTE_UUID) UUID userId) {
        var user = service.findOrCreateUser(userId);
	    return new FindUrlRes(user.getUserId(), user.getUrls());
  }

  @PostMapping(value = "hasUrl", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Operation(description = SWAGGER_HAS_URL)
  public HasUrlRes hasUrl(@RequestAttribute(name = ATTRIBUTE_UUID) UUID userId,
		  				@Valid @RequestBody @NotNull RssReq rss) {
        var foundUrls = service.getUrlList(userId, rss.urls());
	    return new HasUrlRes(foundUrls);
  }

  @PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Operation(description = SWAGGER_INSERT_RSS)
  public AddRes add(@RequestAttribute(name = ATTRIBUTE_UUID) UUID userId,
		  @Valid @RequestBody @NotNull  RssReq rss) {
        var notFoundUrls = service.insertRss(userId, rss.urls());
	    return new AddRes(notFoundUrls);
  }

  @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Operation(description = SWAGGER_DELETE_RSS)
  public ControllerDefaultRes delete(@RequestAttribute(name = ATTRIBUTE_UUID) UUID userId,
		  @Valid @RequestBody @NotNull  RssReq rss) {
       	service.deleteRss(userId, rss.urls());
    	return new ControllerDefaultRes(RSS_DELETED);
  }

  @DeleteMapping(value = "deleteAll", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @Operation(description = SWAGGER_DELETE_ALL_RSS)
  public ControllerDefaultRes deleteAll(@RequestAttribute(name = ATTRIBUTE_UUID) UUID userId) {
       	service.deleteAllRss(userId);
    	return new ControllerDefaultRes(RSS_DELETED_ALL);
  }

}
