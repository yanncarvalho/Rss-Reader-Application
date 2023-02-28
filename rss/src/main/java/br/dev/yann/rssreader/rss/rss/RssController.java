package br.dev.yann.rssreader.rss.rss;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("v1")
public class RssController {

  @Autowired
  private RssService service;

  @PostMapping(value = "getUserContents", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
   public List<Rss> getUrls(@RequestBody Map<String, List<String>> urls) throws InterruptedException, ExecutionException {
	   return service.getRss(urls.get("url"));
   }
}
