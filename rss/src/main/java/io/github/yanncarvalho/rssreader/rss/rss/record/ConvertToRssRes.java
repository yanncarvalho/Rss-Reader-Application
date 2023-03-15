package io.github.yanncarvalho.rssreader.rss.rss.record;

import java.util.List;

import org.springframework.data.domain.Page;

import io.github.yanncarvalho.rssreader.rss.rss.Rss;

/**
 * Paged content response record.
 *
 * @param content {@link #content}.
 * @param pageNumber {@link #pageNumber}.
 * @param pageSize {@link #pageSize}.
 * @param numberOfElements {@link #numberOfElements}.
 * @param first {@link #first}.
 * @param last {@link #last}.
 * @param empty {@link #empty}.
 * 
 * @author Yann Carvalho
 */
public record ConvertToRssRes(
		
		 /**Page number*/
		int pageNumber,
		
		 /**Page size*/
		int pageSize,
		
		 /**number of elements in the Page*/
		long numberOfElements,
		
		 /**{@code true} if it is the first page, {@code false} if not**/
		boolean isFirstPage,
		
		 /**{@code true} if it is the last page, {@code false} if not*/
		boolean isLastPage,
		
		 /**urlNotFound wrapped in a List*/
		List<String>  urlsNotFound, 
		
		 /**Content to be displayed wrapped in a List*/
		List<Rss> content) {
	
	/**
	 * Constructs a {@code PageRes} with PageImpl as a parameter
	 * @param page page with content and page information 
	 */
	public ConvertToRssRes(Page<Rss> page, List<String> urlsNotFound){
		this(page.getPageable().getPageNumber(), 
			 page.getPageable().getPageSize(), 
			 page.getTotalElements(),
			 page.isLast(), 
			 page.isFirst(),
			 urlsNotFound,
			 page.getContent());
	}
}

