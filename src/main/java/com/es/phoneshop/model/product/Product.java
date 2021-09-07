package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class Product {
    private Long id;
    private String code;
    private String description;
    /** null means there is no price because the product is outdated or new */
    private BigDecimal price;
    /** can be null if the price is null */
    private Currency currency;
    private int stock;
    private String imageUrl;

    public Product() {
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public Product(Long id, Product product) {
		this.id = id;
		this.code = product.getCode();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.currency = product.getCurrency();
		this.stock = product.getStock();
		this.imageUrl = product.getImageUrl();
	}

	public Product(String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
		this.id = null;
		this.code = code;
		this.description = description;
		this.price = price;
		this.currency = currency;
		this.stock = stock;
		this.imageUrl = imageUrl;
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
    
    @Override
	public boolean equals(Object o) {

		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		Product p = (Product) o;
		if (p.getCode().equals(this.getCode()) 
				&& p.getCurrency().equals(this.getCurrency())
				&& p.getDescription().equals(this.getDescription()) 
				&& p.getId().equals(this.getId())
				&& p.getImageUrl().equals(this.getImageUrl()) 
				&& p.getPrice().equals(this.getPrice())
				&& p.getStock() == this.getStock()) {
			return true;
		}
		return false;
	}

	public int hashCode() {

		return Objects.hash(this.code, 
				this.currency, 
				this.description, 
				this.id, 
				this.imageUrl, 
				this.price, 
				this.stock);

	}

	public String toString() {
		
		return this.getClass().toString() + "{" 
		+ "code=" + this.code
		+ "currency=" + this.currency
		+ "description=" + this.description
		+ "id=" + this.id
		+ "imageUrl=" + this.imageUrl
		+ "price=" + this.price
		+ "stock=" + this.stock;
		
	}
}
