package com.crypto_tracker.news.repository.kafka;

import com.crypto_tracker.news.repository.jpa.*;
import com.crypto_tracker.news.repository.repos.NewsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class KafkaConsumer {

    private final NewsRepository newsRep;

    @Autowired
    public KafkaConsumer(NewsRepository newsRep){
        this.newsRep = newsRep;
    }

    @KafkaListener(topics= "FreeNews",groupId = "news")
    void consume(String news){
        try{
            JSONObject jsonObj = new JSONObject(news);
            parseNews(jsonObj);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private void parseNews(JSONObject json) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        New news[] = mapper.readValue(json.getJSONArray("articles").toString(), New[].class);
        for (New obj : news) {
            newsRep.save(obj);
        }
    }
}
