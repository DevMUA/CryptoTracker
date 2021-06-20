package com.crypto_tracker.news.repository.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Author {
    private String name;

    public Author(String name) {
        this.name = name;
    }

    public Author() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}