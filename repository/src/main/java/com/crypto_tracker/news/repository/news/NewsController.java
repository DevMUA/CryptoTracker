package com.crypto_tracker.news.repository.news;

import com.crypto_tracker.news.repository.jpa.New;
import com.crypto_tracker.news.repository.kafka.KafkaController;
import com.crypto_tracker.news.repository.repos.NewsRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
public class NewsController {

    private final NewsRepository newsRep;

    @Autowired
    private KafkaController kafkaController;

    private static final String TOPIC = "responseNews";

    @Autowired
    public NewsController(NewsRepository newsRep){ this.newsRep = newsRep; }

    @GetMapping("/api/news/{id}/{coins}")
    public List<New> getNews(@PathVariable int id, @PathVariable String[] coins){
        List<New> news = new ArrayList<>();
        for(String s: coins){
            news.addAll(newsRep.findByTitleContains(s));
        }
        return news;
    }

    @KafkaListener(topics = "requestNews")
    void requestNews(String request){
        try{
            JSONObject jsonObj = new JSONObject(request);
            //String id = jsonObj.getString("id");
            JSONObject res = parseNews(jsonObj);
            kafkaController.sendMessage(TOPIC, res.toString());
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject parseNews(JSONObject json) throws JSONException, IOException {
        List<New> news = new ArrayList<>();
        String id = json.getString("id");
        JSONArray jsonArray = json.getJSONArray("coins");
        for(int i = 0; i<jsonArray.length(); i++){
            news.addAll(newsRep.findByTitleContains(jsonArray.getJSONObject(i).toString()));
        }
        JSONObject res = new JSONObject();
        res.put(id, news);
        return res;
    }
}
