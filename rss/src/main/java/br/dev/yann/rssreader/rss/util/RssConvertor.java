package br.dev.yann.rssreader.rss.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
   private RequestXmlFromHttp job;
   
   @Autowired 
   private JAXBContext context;
        
   @Async
   private CompletableFuture<Rss> getFromUrl(String url)  {
	  try {
          Rss rss = (Rss) context.createUnmarshaller().unmarshal(job.getXml(url));
	      rss.setOriginalLink(url);
	      return CompletableFuture.completedFuture(rss);

	    } catch (JAXBException | IOException e) {
	      throw new RuntimeException(e) ;
	    }
   }
   
   
   public List<Rss> getRssFromUrList(List<String> urlList) {
 	 var rss = new ArrayList<Rss>();
 	 try {
 		   
 		    var futures = urlList.stream().map(u -> this.getFromUrl(u)).toList();
 		    CompletableFuture.allOf(futures.toArray(new CompletableFuture[urlList.size()])).join();
 		    for (CompletableFuture<Rss> completableFuture : futures) {
 		    	Rss rss2 = completableFuture.get();
		     	rss.add(rss2);
 		     }
 			
 	   } catch (InterruptedException | ExecutionException e) {
 			new RuntimeException(e);
 	    }
 	 return rss;
   }

 

}
