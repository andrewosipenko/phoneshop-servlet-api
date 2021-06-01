package com.es.phoneshop.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
public class PriceHistory implements Comparable<PriceHistory> {
    private Date dateTime;
    private BigDecimal price;

    @Override
    public int compareTo(PriceHistory o) {
        return getDateTime().compareTo(o.getDateTime());
    }
}
