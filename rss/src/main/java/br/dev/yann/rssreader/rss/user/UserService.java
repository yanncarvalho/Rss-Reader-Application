package br.dev.yann.rssreader.rss.user;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.dev.yann.rssreader.rss.rss.Rss;
import br.dev.yann.rssreader.rss.rss.RssService;

@Service
public class UserService {
  
  @Autowired
  private UserRepository userRespository;
  
  @Autowired
  private RssService rssService;

  public Page<Rss> getRss(UUID userId, Pageable pageable) {
    User user = findOrCreateUser(userId);
    return rssService.findRssByUrls(user.getUrls(), pageable);
  }

  public User findOrCreateUser(UUID userId) {
      return userRespository.findByUserId(userId)
	      		    .orElseGet(() -> userRespository.save(new User(userId)));
  }
  
  public void deleteRss(UUID userId, List<String> urls) {
      userRespository.removeRssList(userId, urls);
  }

  public List<String> insertRss(UUID userId, List<String> urls) {
      var rss = rssService.findRssByUrls(urls);
      var originalLinkList = rss.stream().map(Rss::getOriginalLink).toList();
      userRespository.addRssList(userId, urls);
      urls.removeAll(originalLinkList);
      return urls;
  }

  public void deleteAllRss(UUID userId) {
      userRespository.removeRssAll(userId);
  }
  
  public List<String> getRssList(UUID userId, List<String> rssUrls) {
      var urls = findOrCreateUser(userId).getUrls();
      rssUrls.retainAll(urls);
      return rssUrls;
  }
  
  public Page<Rss> convertToRss(List<String> urls, PageRequest page) {
      return rssService.findRssByUrls(urls, page);
  }
 

}


