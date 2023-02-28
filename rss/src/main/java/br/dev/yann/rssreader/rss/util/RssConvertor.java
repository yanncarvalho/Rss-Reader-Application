package br.dev.yann.rssreader.rss.util;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

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
   public CompletableFuture<Rss> getFromUrl(String url)  {
	  try {
          Rss rss = (Rss) context.createUnmarshaller().unmarshal(job.getXml(url));
	      rss.setOriginalLink(url);
	      return CompletableFuture.completedFuture(rss);

	    } catch (JAXBException | IOException e) {
	      throw new RuntimeException(e) ;
	    }
	  }
 

}
