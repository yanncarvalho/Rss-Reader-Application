package com.github.yanncarvalho.rssreader.rss.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.yanncarvalho.rssreader.rss.rss.Rss;
import com.github.yanncarvalho.rssreader.rss.user.record.RssReq;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequestMapping("v1")
public class UserController {

  @Autowired
  private UserService service;

  @GetMapping(value = "getContent", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Page<Rss> getRss(@RequestParam(defaultValue = "0") @PositiveOrZero Integer page,
		          @RequestParam(defaultValue = "5") @PositiveOrZero Integer size,
		          @RequestParam(name = "id") java.util.UUID userId) {
	return service.getRss(userId, PageRequest.of(page, size));
  }
  
  @GetMapping(value = "findUrl", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public User findUrl(@RequestParam(name = "id") java.util.UUID userId) {
      return service.findOrCreateUser(userId); 	
  }
  
  @PostMapping(value = "hasUrl", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<String> hasUrl(@RequestParam(name = "id") java.util.UUID userId,
	  	    	     @RequestBody @NotNull RssReq rss) {
      return service.getRssList(userId, rss.urls()); 	
  }
  
  @PostMapping(value = "add", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public List<String> add(@RequestParam(name = "id") java.util.UUID userId, 
	  		  @RequestBody @NotNull RssReq rss) {
      	var notAdd = service.insertRss(userId, rss.urls());
	return notAdd;
  }
  
  @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void delete(@RequestParam(name = "id") java.util.UUID userId, 
	  	     @RequestBody @NotNull RssReq rss) {
       	service.deleteRss(userId, rss.urls()); 	
  }
  
  @DeleteMapping(value = "deleteAll", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void deleteAll(@RequestParam(name = "id") java.util.UUID userId) {
       	service.deleteAllRss(userId); 	
  }
  
}



