package io.github.yanncarvalho.rssreader.rss.user;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	private MongoTemplate collection;

	private static final String URLS = "urls";
	private static final String DOCUMENT_ID = "_id";

	public void addRssList(UUID userId, List<String> list) {
	    collection.upsert(
		       queryFindUserId(userId),
		       new Update().addToSet(URLS).each(list),
	               User.class);
	}

	public User save(User user) {
	   return collection.save(user);
	}
	public Optional<User> findByUserId(UUID userId) {
	    var user = collection.findById(userId, User.class);
	    return Optional.ofNullable(user);
	}

	public void removeRssList(UUID userId, List<String> urls) {
	   collection.findAndModify(
		    queryFindUserId(userId),
		    new Update().pullAll(URLS, urls.toArray()),
	            new FindAndModifyOptions().returnNew(true), User.class);
	}

	private Query queryFindUserId(UUID userId) {
	    return new Query().addCriteria(Criteria.where(DOCUMENT_ID).is(userId));
	}

	public void removeRssAll(UUID userId) {
	   collection.updateFirst(
		   queryFindUserId(userId),
		   new Update().set(URLS, Collections.emptyList()),
	           User.class);
	}
}
