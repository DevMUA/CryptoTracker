package com.crypto_tracker.news.repository.jpa;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@Entity
public class New {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String author;
    private String published_date;
    private String published_date_precision;
    private String link;
    private String clean_url;
    private String summary;
    private String rights;
    private int rank;
    private String topic;
    private String country;
    private String language;
    @OneToMany
    private List<Author> authors;
    private String media;
    private Boolean is_opinion;
    private String twitter_account;
    private double _score;
    private String _id;
}