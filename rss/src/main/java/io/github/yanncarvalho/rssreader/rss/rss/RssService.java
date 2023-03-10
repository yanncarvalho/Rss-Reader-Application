package io.github.yanncarvalho.rssreader.rss.rss;

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

import io.github.yanncarvalho.rssreader.rss.rss.util.RssConvertor;

@Service
public class RssService {

  @Autowired
  private RssConvertor conv;

  @Autowired
  private RssRepository respository;


  /**
   * Returns an Optional of {@link Rss rss}.
   *
   * Rss found is persisted in the database.
   *
   * @param url URL to be searched.
   * @return rss wrapped in a {@link Optional}.
   *
   * @see #updateByUrlList
   */
  public Optional<Rss> findRssByUrl(String url) {
     var found = respository.findByUrl(url);
     if(found == null || !isRssUpdated(found)) {
	  updateByUrlList(List.of(url));
	  found = respository.findByUrl(url);
     }
     return Optional.ofNullable(found);
  }

  /**
   * Returns a Plage of {@link Rss rss}.
   *
   * All Rss found is persisted in the database.
   *
   * @param rssUrls  URLs to be searched wrapped in a {@link List}.
   * @param pageable pages information.
   * @return rss wrapped in a {@link Plage}.
   *
   * @see #updateByUrlList
   */
  public Page<Rss> findRssByUrls(List<String> rssUrls, Pageable pageable) {
    var rss = respository.findAllByUrls(rssUrls, pageable);
    var urlsFoundAndUpdated = rss.stream().filter(this::isRssUpdated)
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

  /**
   * Returns a List of {@link Rss rss}.
   *
   * All Rss found is persisted in the database.
   *
   * @param rssUrls URLs to be searched wrapped in a {@link List}.
   * @return rss wrapped in a {@link List}.
   *
   * @see #updateByUrlList
   */
  public List<Rss> findRssByUrls(List<String> rssUrls) {
      var rss = respository.findAllByUrls(rssUrls);
      var urlsFoundAndUpdated = rss.stream()
	      			      .filter(this::isRssUpdated)
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

  /**
   * Returns a Plage of {@link Rss rss}.
   *
   * @param rssIds   rss ObjectId to be searched wrapped in a {@link List}.
   * @param pageable pages information.
   * @return rss wrapped in a {@link Plage}.
   */
  public Page<Rss> findRssByIds(List<ObjectId> rssIds, Pageable pageable) {
      return respository.findAllByIds(rssIds, pageable);
  }

  /**
   * Delete a List of {@link ObjectId ids}.
   *
   * @param rssIds  rss ObjectId to be searched wrapped in a {@link List}.
  */
  public void removeById(List<ObjectId> rssIds) {
      respository.removeById(rssIds);
  }

  /**
   * Delete in the database a List of Rss by them URLs.
   *
   * @param urls URLs to be searched wrapped in a {@link List}.
   */
  public void removeByUrl(List<String> urls) {
      respository.removeByUrl(urls);
  }

  /**
   * Update or Insert in the database a List of Rss by them URLs.
   *
   * @param urls URLs to be searched wrapped in a {@link List}.
   */
  public void updateByUrlList(List<String> urls) {
      List<Rss> rssList = conv.getRssFromUrlList(urls);
      if(rssList.isEmpty()) {
	     return;
      }
      respository.upsertAll(rssList);
  }

  /**
   * Check if a Rss is updated
   *
   * @param rss Rss to be checked
   * @return {@code true} if is updated, {@code false} if is not
   */
  private boolean isRssUpdated(Rss rss) {
      var updateTime = LocalDateTime.now().minus(24, ChronoUnit.HOURS);
      return rss != null && !rss.getLastUpdate().isBefore(updateTime);
  }

}



