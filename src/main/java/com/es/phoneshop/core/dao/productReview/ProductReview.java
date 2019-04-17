package com.es.phoneshop.core.dao.productReview;

import java.io.Serializable;

public class ProductReview implements Serializable {
    private String name;
    private Short rating;
    private String comment;
    private Long productId;
    private Long reviewId;
    private boolean approved;

    public ProductReview() {
    }

    public ProductReview(String name, Short rating, String comment, Long productId) {
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
    }

    public ProductReview(String name, Short rating, String comment, Long productId, Long reviewId, boolean approved) {
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
        this.reviewId = reviewId;
        this.approved = approved;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getRating() {
        return rating;
    }

    public void setRating(Short rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "name='" + name + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", productId=" + productId +
                '}';
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
