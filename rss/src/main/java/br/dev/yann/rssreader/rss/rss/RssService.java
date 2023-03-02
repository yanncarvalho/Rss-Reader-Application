package br.dev.yann.rssreader.rss.rss;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.yann.rssreader.rss.util.RssConvertor;

@Service
public class RssService {

  @Autowired
  private RssConvertor conv;	
  
  @Autowired
  private RssRepository respository;

  public Optional<Rss> getRss(String url) {
	return respository.findByUrls(url);
  }
  
  public List<Rss> getRssList(List<String> rssUrls) {
   var rss = respository.findAllByUrls(rssUrls);

   List<String> urlsFounds = rss.stream().map(Rss::getOriginalLink).toList();
   rssUrls.removeAll(urlsFounds);
   if(!rssUrls.isEmpty()) {
	   List<Rss> rssDownloaded = updateByUrlList(rssUrls);
	   rss.addAll(rssDownloaded);
   }

   return rss;

  }
 
  public void removeById(List<ObjectId> ids) {
	  respository.removeById(ids);
  }
  
  public void removeByUrl(List<String> url) {
	  respository.removeByUrl(url);
  }
	
  public List<Rss> updateByUrlList(List<String> url) {
	  List<Rss> rssList = conv.getRssFromUrList(url);
	  respository.upsertAll(rssList);
	  return rssList;
  }
	
 
}



