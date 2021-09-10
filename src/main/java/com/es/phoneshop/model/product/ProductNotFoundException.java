package com.es.phoneshop.model.product;

public class ProductNotFoundException extends RuntimeException {
  public ProductNotFoundException() {
  }

  public ProductNotFoundException(String message) {
    super(message);
  }

  public ProductNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProductNotFoundException(Throwable cause) {
    super(cause);
  }
}
