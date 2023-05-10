package com.es.phoneshop.model.comparator;

import com.es.phoneshop.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class SearchComparator implements Comparator<Product> {
    private String description;

    public SearchComparator(String description) {
        this.description = description;
    }

    @Override
    public int compare(Product product1, Product product2) {
        if (description == null) return 0;
        List words = Stream.of(description.split("[^A-Za-z0-9I]+"))
                .distinct().toList();
        return (int) (words.stream().filter(word -> product2.getDescription().contains(description)).count() -
                        words.stream().filter(word -> product1.getDescription().contains(description)).count());
    }
}
