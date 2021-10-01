package com.es.phoneshop.model.product.productdao;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class ProductBuilderImpl implements ProductBuilder {
    Product product = new Product();

    @Override
    public ProductBuilder setId(Long id) {
        product.setId(id);
        return this;
    }

    @Override
    public ProductBuilder setCode(String code) {
        product.setCode(code);
        return this;
    }

    @Override
    public ProductBuilder setDescription(String description) {
        product.setDescription(description);
        return this;
    }

    @Override
    public ProductBuilder setPrice(BigDecimal price) {
        product.setPrice(price);
        return this;
    }

    @Override
    public ProductBuilder setCurrency(Currency currency) {
        product.setCurrency(currency);
        return this;
    }

    @Override
    public ProductBuilder setStock(int stock) {
        product.setStock(stock);
        return this;
    }

    @Override
    public ProductBuilder setImageUrl(String imageUrl) {
        product.setImageUrl(imageUrl);
        return this;
    }

    @Override
    public ProductBuilder setPriceHistory(List<PriceHistory> priceHistoryList) {
        product.setPriceHistoryList(priceHistoryList);
        return this;
    }

    @Override
    public Product build() {
        return product;
    }
}