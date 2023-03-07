package com.github.yanncarvalho.rssreader.rss.rss;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.yanncarvalho.rssreader.rss.rss.util.RssConvertor;

@Service
public class RssService {

  @Autowired
  private RssConvertor conv;

  @Autowired
  private RssRepository respository;

  /**
   * @throws NullPointerException - if no value is present and the supplying function is null
   **/
  public Optional<Rss> findRssByUrl(String url) {
     var found = respository.findByUrl(url);
     if(found == null || !isRssUpdated(found)) {
	  updateByUrlList(List.of(url));
	  found = respository.findByUrl(url);
     }
     return Optional.ofNullable(found);
  }

  public Page<Rss> findRssByUrls(List<String> rssUrls, Pageable pageable) {
    var rss = respository.findAllByUrls(rssUrls, pageable);
    var urlsFoundAndUpdated = rss.stream().filter(r -> isRssUpdated(r))
    					  .map(Rss::getOriginalLink)
    					  .toList();
    var updateList = new ArrayList<>(rssUrls);
    updateList.removeAll(urlsFoundAndUpdated);
    if(!updateList.isEmpty()) {
	   updateByUrlList(updateList);
	   rss = respository.findAllByUrls(rssUrls, pageable);
    }
    return rss;
  }

  public List<Rss> findRssByUrls(List<String> rssUrls) {
      var rss = respository.findAllByUrls(rssUrls);
      var urlsFoundAndUpdated = rss.stream()
	      			      .filter(r -> isRssUpdated(r))
      				      .map(Rss::getOriginalLink)
      				      .toList();
      var updateList = rssUrls;
      updateList.removeAll(urlsFoundAndUpdated);
      if(!updateList.isEmpty()) {
  	   updateByUrlList(updateList);
  	   rss = respository.findAllByUrls(rssUrls);
      }
      return rss;
   }

  public Page<Rss> findRssByIds(List<ObjectId> rssId, Pageable pageable) {
      return respository.findAllByIds(rssId, pageable);
  }

  public void removeById(List<ObjectId> ids) {
      respository.removeById(ids);
  }

  public void removeByUrl(List<String> url) {
      respository.removeByUrl(url);
  }

  public void updateByUrlList(List<String> url) {
      List<Rss> rssList = conv.getRssFromUrList(url);
      if(rssList.isEmpty()) {
	  return;
      }
      respository.upsertAll(rssList);
  }

  private boolean isRssUpdated(Rss rss) {
      var updateTime = LocalDateTime.now().minus(24, ChronoUnit.HOURS);
      return rss != null && !rss.getLastUpdate().isBefore(updateTime);
  }

}



