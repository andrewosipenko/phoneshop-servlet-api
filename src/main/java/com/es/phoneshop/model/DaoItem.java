package com.es.phoneshop.model;

public class DaoItem {
    private Long id;

    public DaoItem(Long id) {
        this.id = id;
    }

    public DaoItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
