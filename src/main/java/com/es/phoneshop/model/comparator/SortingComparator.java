package com.es.phoneshop.model.comparator;

import com.es.phoneshop.enums.SortOrder;
import com.es.phoneshop.model.filter.Filter;
import com.es.phoneshop.model.product.Product;

public class SortingComparator {

	public static int sortProducts(Product p1, Product p2, Filter filter) {
		
		int result = 0;
		
		if(filter.getSortField() == null || filter.getSortOrder() == null) {
			return (int)(10*(filter.percentOfWords(p2)-filter.percentOfWords(p1)));
		} else {
			
			switch(filter.getSortField()) {
			case DESCRIPTION:
				result = p1.getDescription().compareTo(p2.getDescription());
				return (filter.getSortOrder() == SortOrder.ASC) ? result : -result;
			case PRICE:
				result = p1.getPrice().compareTo(p2.getPrice());
				return (filter.getSortOrder() == SortOrder.ASC) ? result : -result;
			default:
				break;
			
			}
			return 0;
		}
	}
	
}
