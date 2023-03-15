package io.github.yanncarvalho.rssreader.rss.rss.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import io.github.yanncarvalho.rssreader.rss.rss.Rss;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

@Component
public class RssConvertor {

   @Autowired
   private RssRequestFromHttp job;

   @Autowired
   private JAXBContext context;

	/**
	 * Asynchronously search an RSS given a URL. if not found returns {@code null}
	 *
	 * @param url url to search the RSS.
	 *
	 * @return A {@link CompletableFuture} of RSS
	 */
   @Async
   public @Nullable CompletableFuture<Rss> getFromUrl(String url)  {
	  try {
          Rss rss = (Rss) context.createUnmarshaller().unmarshal(job.getXml(url));
	      rss.setOriginalLink(url);
	      return CompletableFuture.completedFuture(rss);
	    } catch (JAXBException | IOException e) {
	      return null;
	    }
   }

	/**
	 * Search RSSs by a given List of URLs. if not found returns
	 * {@code an Empty List}
	 *
	 * @param url List URLs wrapped in a {@link List}.
	 *
	 * @see #getFromUrl
	 * @return RSSs wrapped in a {@link List}
	 */
   public List<Rss> getRssFromUrlList(List<String> urlList) {
 	 var rss = new ArrayList<Rss>();
 	 var futures = urlList.stream().map(this::getFromUrl)
 		 	      .filter(Objects::nonNull)
 		 	      .toList();
 	 if (futures.isEmpty()) {
 	     return new ArrayList<>();
 	 }
	 CompletableFuture.allOf(futures.toArray(new CompletableFuture[urlList.size()])).join();
	 for (CompletableFuture<Rss> completableFuture : futures) {
	      try {
		       rss.add(completableFuture.get());
	     	} catch (InterruptedException | ExecutionException e) {}
	  }
 	 return rss;
   }



}
