package com.crypto_tracker.retrieval.coins;

import com.crypto_tracker.retrieval.kafka.KafkaController;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@EnableScheduling
@Component
public class Coins {

    @Autowired
    private KafkaController kafkaController;

    @Autowired
    private CoinGeckoAPI coinGeckoAPI;

    //private static final String TOPIC = "coinsLastMonth";
    private static final String TOPIC = "coinsLiveUpdate";
    private static final String TOPIC2 = "historic";

    private String coins[] = new String[] {"bitcoin","ethereum","cardano"};
    private String str_coins = "bitcoin,ethereum,cardano";
    private static final String base_url = "https://api.coingecko.com/api/v3/";
    private static final String end_url = "/market_chart?vs_currency=usd&days=30";
    private static final String last_updated = "simple/price?ids=bitcoin&vs_currencies=usd&include_last_updated_at=true";


    @Scheduled(fixedRate = 1000)
    public void getNews() throws IOException {
//        URL url = new URL(base_url +
//                String.format("simple/price?ids=%s&vs_currencies=usd&include_last_updated_at=true", str_coins));
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//
//        int status = con.getResponseCode();
//
//        StringBuffer content = new StringBuffer();
//        if(status < 299) {
//            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();
//            kafkaController.sendMessage(TOPIC2, content.toString());
//            System.out.println(content.toString());
//        }else{
//            System.out.println(String.format("error: %d", status));
//        }
//        con.disconnect();
    }
    @Scheduled(fixedRate = 3000000)
    public void pushCoinsToKafka(){
        ArrayList<Coin> message = new ArrayList<>(coinGeckoAPI.getCoins());
        kafkaController.sendMessage(TOPIC, message);
    }

    @Scheduled(fixedRate = 3000000)
    public void pushHistoricToKafka(){
        ArrayList<GraphicalCoinInformation> message2 = new ArrayList<>(coinGeckoAPI.getGraphPoints());
        kafkaController.sendMessage2(TOPIC2,message2);
    }

}
