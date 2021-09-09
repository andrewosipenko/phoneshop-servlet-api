package com.es.phoneshop.model.filter;

import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

public class Filter {

	private SortField sortField;
	private SortOrder sortOrder;

	private String[] queryWords;
	
	public Filter() {

	}

	public Filter(SortField sortField, SortOrder sortOrder, String query) {
		this.queryWords = query.split("//s+");
		this.setSortField(sortField);
		this.setSortOrder(sortOrder);
	}

	public SortField getSortField() {
		return sortField;
	}

	public void setSortField(SortField sortField) {
		this.sortField = sortField;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(SortOrder sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public int numberOfWords(Product p) {

		int wordsNum = 0;

		for (String word : queryWords) {
			if (word.contains(p.getDescription())) {
				wordsNum++;
			}
			if(word.equals(p.getCode())) {
				wordsNum += 10;
			}
		}
		
		return wordsNum;
	}

}
