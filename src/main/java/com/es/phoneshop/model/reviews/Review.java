package com.es.phoneshop.model.reviews;

public class Review {
    private String Name;
    private Integer rating;
    private String comment;

    public Review() {
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getName() {
        return Name;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
