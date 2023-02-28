package br.dev.yann.rssreader.rss.rss;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface RssRepository extends MongoRepository<Rss, String> {

	 Optional<Rss> findByOriginalLink(String originalLink);
}
