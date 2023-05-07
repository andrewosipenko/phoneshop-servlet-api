package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Product implements Cloneable {
    private Long id;
    private String code;
    private String description;
    /**
     * null means there is no price because the product is outdated or new
     */
    private BigDecimal price;
    /**
     * can be null if the price is null
     */
    private Currency currency;
    private int stock;
    private String imageUrl;
    /**
     * created inner class Changer for comfortable updating of product
     */
    private Changer changer;

    public Product() {
        this.changer = new Changer();
    }

    /**
     * constructor for inner initialization
     */
    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.changer = new Changer();
    }

    /**
     * constructor for saving from client
     */
    public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.changer = new Changer();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Changer changer() {
        return this.changer;
    }

    /**
     * like builder but changer
     */
    public class Changer {

        private Changer() {
        }

        public Changer id(Long id) {
            if (!Product.this.id.equals(id) && id != null) {
                Product.this.id = id;
            }
            return this;
        }

        public Changer code(String code) {
            if (!Product.this.code.equals(code) && code != null) {
                Product.this.code = code;
            }
            return this;
        }

        public Changer description(String description) {
            if (!Product.this.description.equals(description) && description != null) {
                Product.this.description = description;
            }
            return this;
        }

        public Changer price(BigDecimal price) {
            if (!Product.this.price.equals(price) && price != null) {
                Product.this.price = price;
            }
            return this;
        }

        public Changer currency(Currency currency) {
            if (!Product.this.currency.equals(currency) && currency != null) {
                Product.this.currency = currency;
            }
            return this;
        }

        public Changer stock(int stock) {
            if (Product.this.stock != stock) {
                Product.this.stock = stock;
            }
            return this;
        }

        public Changer imageUrl(String imageUrl) {
            if (!Product.this.imageUrl.equals(imageUrl) && imageUrl != null) {
                Product.this.imageUrl = imageUrl;
            }
            return this;
        }

        public Product change() {
            return Product.this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return stock == product.stock
                && Objects.equals(id, product.id)
                && Objects.equals(code, product.code)
                && Objects.equals(description, product.description)
                && Objects.equals(price, product.price)
                && Objects.equals(currency, product.currency)
                && Objects.equals(imageUrl, product.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, description, price, currency, stock, imageUrl);
    }

    @Override
    protected Product clone() throws CloneNotSupportedException {
        return (Product) super.clone();
    }
}