package com.es.phoneshop.model.product.productdao;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface ProductBuilder {
    ProductBuilder setId(Long id);

    ProductBuilder setCode(String code);

    ProductBuilder setDescription(String description);

    ProductBuilder setPrice(BigDecimal price);

    ProductBuilder setCurrency(Currency currency);

    ProductBuilder setStock(int stock);

    ProductBuilder setImageUrl(String imageUrl);

    ProductBuilder setPriceHistory(List<PriceHistory> priceHistoryList);

    Product build();
}