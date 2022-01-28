package com.es.phoneshop.model.product;

import java.util.Arrays;
import java.util.Comparator;

public class ProductSearchComparator implements Comparator<Product> {
    private final String query;

    ProductSearchComparator(String query) {
        this.query = query;
    }

    @Override
    public int compare(Product left, Product right) {
        if(query == null || query.isEmpty()){
            return 0;
        }

        if (left.getDescription().equalsIgnoreCase(query)) {
            return 1;
        }
        if (right.getDescription().equalsIgnoreCase(query)) {
            return -1;
        }
        if (left.getDescription().equalsIgnoreCase(right.getDescription())) {
            return 0;
        }

        String[] queryTokens = query.toLowerCase().split(" ");
        String[] leftTokens = left.getDescription().toLowerCase().split(" ");
        String[] rightTokens = right.getDescription().toLowerCase().split(" ");

        int leftWordsAmount = leftTokens.length;
        int rightWordsAmount = rightTokens.length;


        float leftMatch = Arrays.stream(leftTokens)
                .filter(leftItem -> Arrays.asList(queryTokens).contains(leftItem))
                .count();

        float rightMatch = Arrays.stream(rightTokens)
                .filter(rightItem -> Arrays.asList(queryTokens).contains(rightItem))
                .count();

        if (leftMatch != 0 || rightMatch != 0) {
            return (int) Math.signum(rightMatch / rightWordsAmount - leftMatch / leftWordsAmount);
        } else {
            leftMatch = 0;
            rightMatch = 0;
            for (String queryItem : queryTokens) {
                leftMatch += (int) Arrays.stream(leftTokens).filter(leftItem -> leftItem.contains(queryItem)).count();
                rightMatch += (int) Arrays.stream(rightTokens).filter(rightItem -> rightItem.contains(queryItem)).count();
            }
            return (int) Math.signum(rightMatch / rightWordsAmount - leftMatch / leftWordsAmount);
        }
    }
}
