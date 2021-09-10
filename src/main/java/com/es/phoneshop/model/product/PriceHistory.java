package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PriceHistory {
  private LocalDate startDate;
  private BigDecimal price;

  public PriceHistory() {
  }

  public PriceHistory(BigDecimal price) {
    this.startDate = LocalDate.now();
    this.price = price;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PriceHistory that = (PriceHistory) o;

    if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
    return price != null ? price.equals(that.price) : that.price == null;
  }

  @Override
  public int hashCode() {
    int result = startDate != null ? startDate.hashCode() : 0;
    result = 31 * result + (price != null ? price.hashCode() : 0);
    return result;
  }
}
