package br.dev.yann.rssreader.auth.user.record;

import java.util.List;

import org.springframework.data.domain.PageImpl;

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
public record PageRes<T>(

		 /**Content to be displayed wrapped in a List*/
		List<T> content,
		
		 /**Page number*/
		int pageNumber,
		
		 /**Page size*/
		int pageSize,
		
		 /**number of elements in the Page*/
		long numberOfElements,
		
		 /**{@code true} tf it is the first page, {@code false} if not**/
		boolean isFirstPage,
		
		 /**{@code true} tf it is the last page, {@code false} if not*/
		boolean isLastPage) {
	
	/**
	 * Constructs a {@code PageRes} with PageImpl as a parameter
	 * @param page page with content and page information 
	 */
	public PageRes(PageImpl<T> page){
		this(page.getContent(),
			 page.getPageable().getPageNumber(), 
			 page.getPageable().getPageSize(), 
			 page.getTotalElements(),
			 page.isLast(), 
			 page.isFirst());
	}
}

