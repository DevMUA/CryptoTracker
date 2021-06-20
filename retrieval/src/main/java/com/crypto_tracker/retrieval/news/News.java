package com.crypto_tracker.retrieval.news;

import com.crypto_tracker.retrieval.kafka.KafkaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class News {

    @Autowired
    private KafkaController kafkaController;

    private static final String TOPIC = "FreeNews";

    private static ArrayList<String> coins = new ArrayList<>(
            Arrays.asList("bitcoin", "ethereum", "cardano"));

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

    @Scheduled(fixedRate = 3600000)
    public void getNews() throws IOException{
        String from;
        String to;
        Date date = new Date();
        to = formatter.format(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // set to the current time
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1); // set to the previous month
        date = calendar.getTime();
        from = formatter.format(date);

        for(String c: coins){
            URL url = new URL(String.format(
                    "https://free-news.p.rapidapi.com/v1/search?q=%s&lang=en&from=%s&to=%s", c, from, to));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("x-rapidapi-key", "c067ae4690mshc975fb0c5670919p158721jsn51bf5a78bd00");
            con.setRequestProperty("x-rapidapi-host", "free-news.p.rapidapi.com");

            int status = con.getResponseCode();

            StringBuffer content = new StringBuffer();
            if(status < 299) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                System.out.println(content.toString());
            }else{
                System.out.println(String.format("error: %d", status));
            }
            con.disconnect();
        }
    }
}
