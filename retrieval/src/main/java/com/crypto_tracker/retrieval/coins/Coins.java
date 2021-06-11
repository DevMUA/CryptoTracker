package com.crypto_tracker.retrieval.coins;

import com.crypto_tracker.retrieval.kafka.KafkaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class Coins {

    @Autowired
    private KafkaController kafkaController;

    private static final String TOPIC = "coinsInfo";

    private static ArrayList<String> coins = new ArrayList<>(
            Arrays.asList("bitcoin", "ethereum", "cardano"));

    private static final String base_url = "https://api.coingecko.com/api/v3/";
    private static final String end_url =
            "?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false";

    @Scheduled(fixedRate = 1000)
    public void getNews() throws IOException {
        for(String c: coins){
            URL url = new URL(base_url + c + end_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();

            StringBuffer content = new StringBuffer();
            if(status < 299) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                kafkaController.sendMessage(TOPIC, content.toString());
                System.out.println(content.toString());
            }else{
                System.out.println(String.format("error: %d", status));
            }
            con.disconnect();
        }
    }
}
