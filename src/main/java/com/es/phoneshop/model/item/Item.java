package com.es.phoneshop.model.item;

import java.io.Serializable;

public class Item implements Serializable {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
