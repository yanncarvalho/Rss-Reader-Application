package io.github.yanncarvalho.rssreader.rss.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.github.yanncarvalho.rssreader.rss.rss.Rss;
import io.github.yanncarvalho.rssreader.rss.rss.RssService;

@Service
public class UserService {

  @Autowired
  private UserRepository userRespository;

  @Autowired
  private RssService rssService;

  /**
   * Returns an Page of {@link Rss rss}.
   *
   * If user do not exist is created a new user.
   *
   * @param userId   user Id
   * @param pageable pages information.
   * @return rss wrapped in a {@link Page}.
   *
   * @see #findOrCreateUser
   */
  public Page<Rss> getRss(UUID userId, Pageable pageable) {
      User user = findOrCreateUser(userId);
      return rssService.findRssByUrls(user.getUrls(), pageable);
  }

  /**
   * Find or create a new User.
   *
   * @param userId   user Id
   * @return user found or created
   *
   */
  public User findOrCreateUser(UUID userId) {
      return userRespository.findByUserId(userId)
	      		    .orElseGet(() -> userRespository.save(new User(userId)));
  }

  /**
   * Insert URLs by user.
   *
   * If a URL is not a valid Rss, it will not be inserted.
   *
   * @param userId user Id
   * @param urls   URLs to be searched wrapped in a {@link List}.
   *
   * @return a {@link List} of URLs could be inserted.
   */
  public List<String> insertRss(UUID userId, List<String> urls) {
      var rss = rssService.findRssByUrls(urls);
      var originalLinkList = rss.stream().map(Rss::getOriginalLink).toList();
      userRespository.addRssList(userId, urls);
      urls.removeAll(originalLinkList);
      return urls;
  }

  /**
   * Delete URLs by a user
   *
   * @param userId user Id
   * @param urls   URLs to be searched wrapped in a {@link List}.
   *
   */
  public void deleteRss(UUID userId, List<String> urls) {
      userRespository.removeRssList(userId, urls);
  }

  /**
   * Delete All URLs for a user
   *
   * @param userId user Id
   */
  public void deleteAllRss(UUID userId) {
      userRespository.removeRssAll(userId);
  }

  /**
   * check if a user contains a list of URLs
   *
   * If the user does not exist, a new user is created.
   *
   * @param userId  userId user Id
   * @param rssUrls URLs to be searched wrapped in a {@link List}.
   *
   * @return the {@link List} of URLs informed  which contains in the user
   */
  public List<String> getUrlList(UUID userId, List<String> rssUrls) {
      var urls = findOrCreateUser(userId).getUrls();
      rssUrls.retainAll(urls);
      return rssUrls;
  }

}


