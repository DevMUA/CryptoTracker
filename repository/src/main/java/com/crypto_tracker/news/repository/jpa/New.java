package com.crypto_tracker.news.repository.jpa;

import javax.persistence.*;
import java.util.List;

@Entity
public class New {
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


    public New() {

    }


    public New(String id, String title, String author, String p_date, String date_precision,
               String link, String clean_url, String summary, String rights, int rank,
               String topic, String country, String language, List<Author> authors, String media,
               Boolean is_opinion, String twitter_account, double score)
    {
        set_id(id);
        this.title = title;
        this.author = author;
        this.published_date = p_date;
        this.published_date_precision = date_precision;
        this.link = link;
        this.clean_url = clean_url;
        this.summary = summary;
        this.rights = rights;
        this.rank = rank;
        this.topic = topic;
        this.country = country;
        this.language = language;
        this.authors = authors;
        this.media = media;
        this.is_opinion = is_opinion;
        this.twitter_account = twitter_account;
        this._score = score;
    }


    public void set_id(String id) {
        this._id = id;
    }

    @Id
    public String get_id() {
        return _id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setDate_precision(String date_precision) {
        this.published_date_precision = date_precision;
    }

    public String getDate_precision() {
        return published_date_precision;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setClean_url(String clean_url) {
        this.clean_url = clean_url;
    }

    public String getClean_url() {
        return clean_url;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getRights() {
        return rights;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getMedia() {
        return media;
    }

    public void setIs_opinion(Boolean is_opinion) {
        this.is_opinion = is_opinion;
    }

    public Boolean getIs_opinion() {
        return is_opinion;
    }

    public void setTwitter_account(String twitter_account) {
        this.twitter_account = twitter_account;
    }

    public String getTwitter_account() {
        return twitter_account;
    }

    public void setScore(double score) {
        this._score = score;
    }

    public double getScore() {
        return _score;
    }
}