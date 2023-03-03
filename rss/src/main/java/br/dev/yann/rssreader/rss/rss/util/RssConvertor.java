package br.dev.yann.rssreader.rss.rss.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import br.dev.yann.rssreader.rss.rss.Rss;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

@Component
public class RssConvertor {
	
   @Autowired 
   private RssRequestFromHttp job;
   
   @Autowired 
   private JAXBContext context;
        
   @Async
   private CompletableFuture<Rss> getFromUrl(String url)  {
	  try {
          Rss rss = (Rss) context.createUnmarshaller().unmarshal(job.getXml(url));
	      rss.setOriginalLink(url);
	      return CompletableFuture.completedFuture(rss);
	    } catch (JAXBException | IOException e) {
	      return null;
	    }
   }
   
   
   public List<Rss> getRssFromUrList(List<String> urlList) {
 	 var rss = new ArrayList<Rss>();
 	 var futures = urlList.stream().map(u -> this.getFromUrl(u))
 		 	      .filter(Objects::nonNull)
 		 	      .toList();
 	 if (futures.isEmpty()) {
 	     return new ArrayList<>();
 	 }
	 CompletableFuture.allOf(futures.toArray(new CompletableFuture[urlList.size()])).join();
	 for (CompletableFuture<Rss> completableFuture : futures) {
	      try {   	
		  rss.add(completableFuture.get());
	      	} catch (InterruptedException | ExecutionException e) {
	      	    continue;
	      	}
	  }
 	 return rss;
   }

 

}
