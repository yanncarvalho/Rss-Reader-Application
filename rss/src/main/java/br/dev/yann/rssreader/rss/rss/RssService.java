package br.dev.yann.rssreader.rss.rss;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.yann.rssreader.rss.util.RssConvertor;

@Service
public class RssService {

  @Autowired
  private RssConvertor conv;	
  

  //@Autowired
  //private RssRepository respository;

  public Set<String> findAll(long id) {

    return null;
  }

  public void deleteRss(long id, Set<String> rssUrls) {
	  return;
  }

  public void addRss(long id, Set<String> rssUrls) {
	  return;
  }

  //public void deleteAllRss(long id) { this.dao.deleteAllRss(id); }

  public List<String> getRssList(long id, List<String> rssUrls) {

    return null;
  }
  
  public List<Rss> getRss(List<String> rssUrls) {

	 
	 var futures = rssUrls.stream().map(u -> conv.getFromUrl(u)).toList();
	 CompletableFuture.allOf(futures.toArray(new CompletableFuture[rssUrls.size()])).join();
	 return futures.stream().map(t -> {
		try {
			
			return t.get();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}).toList();
	 

  }

  public String getUserRssContents(long id, int page, int size, int offset) {

    return null;
  }

  public String convertRssUrls(List<String> urls, int page, int size, int offset) {
 
    return null;
  }
}



