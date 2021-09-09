package com.es.phoneshop.model.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.es.phoneshop.enums.SortField;
import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.product.Product;

public class Filter{

	private SortField sortField;
	private SortOrder sortOrder;

	private List<String> queryWords;

	public Filter() {

	}

	public Filter(SortField sortField, SortOrder sortOrder, String query) {
		if(query == null) {
			query = "";
		}
		this.queryWords = new ArrayList<String>(
				Arrays.asList(
						query.split("\\s+")));
		this.setSortField(sortField);
		this.setSortOrder(sortOrder);
	}
	
	public Filter(String query) {
		this(null, null, query);
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
	
	public List<String> getQueryWords() {
		return queryWords;
	}

	public void setQueryWords(List<String> queryWords) {
		this.queryWords = queryWords;
	}
	
	public double percentOfWords(Product p) {

		if (queryWords.stream()
				.anyMatch(word -> word.equals(p.getCode()))) {
			return 1;
		}

		int wordsNum = (int) queryWords.stream()
				.filter(word -> {
			return p.getDescription().contains(word);})
				.count();

		return (double) wordsNum / p.getDescription().split("//s+").length;
	}
	
}
