package br.dev.yann.rssreader.rss.rss;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.BulkOperations.BulkMode;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Nullable;

@Repository
public class RssRepository {
	
	@Autowired
	private MongoTemplate collection;
	
	private final String ORIGINAL_LINK = "originalLink";
	private final String DOCUMENT_ID = "id";
	
	private void remove(String key, List<?> parameter) {
		collection.bulkOps(BulkMode.UNORDERED, Rss.class)
				  .remove(queryFindBy(key, parameter))
				  .execute();
	}

	private Query queryFindBy(String key, List<?> parameter) {
		return new Query(Criteria.where(key).in(parameter));
	}
	
	public void upsertAll(List<Rss> listRss) {
	    	var urls = listRss.stream().map(Rss::getOriginalLink).toList();
	    	this.removeByUrl(urls);
	    	collection.bulkOps(BulkMode.UNORDERED, Rss.class).insert(listRss).execute();
	}
	
	public void removeById(List<ObjectId> ids) {
		this.remove(DOCUMENT_ID, ids);
	}
	
	public void removeByUrl(List<String> ids) {
		this.remove(ORIGINAL_LINK, ids);
	}
	
	public Page<Rss> findAllByIds(List<ObjectId> ids,  Pageable pageable) {
		var query = queryFindBy(DOCUMENT_ID, ids).with(pageable);
		return PageableExecutionUtils.getPage(
						collection.find(query, Rss.class), 
						pageable,       
		                () -> collection.count(query.skip(0).limit(0), Rss.class));
	}
	
	public Page<Rss> findAllByUrls(List<String> urls,  Pageable pageable) {
		var query = queryFindBy(ORIGINAL_LINK, urls).with(pageable);
		var found = collection.find(query, Rss.class);
		return PageableExecutionUtils
					.getPage(found, 
						pageable,       
						() -> collection.count(query.skip(0).limit(0), Rss.class));
	}
	
	public List<Rss> findAllByUrls(List<String> urls) {
		var query = queryFindBy(ORIGINAL_LINK, urls);
		return collection.find(query, Rss.class); 		
	}
	
	public @Nullable Rss findByUrl(String url) {
		var query = queryFindBy(ORIGINAL_LINK, List.of(url));
		var findOne = collection.findOne(query, Rss.class);
		return findOne;
	}
}
