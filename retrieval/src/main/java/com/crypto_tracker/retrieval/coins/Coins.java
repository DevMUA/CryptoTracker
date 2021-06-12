package com.crypto_tracker.retrieval.coins;

import com.crypto_tracker.retrieval.kafka.KafkaController;
import jdk.nashorn.internal.parser.JSONParser;
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    private static final String TOPIC = "coinsLastMonth";
    private static final String TOPIC2 = "coinsLiveUpdate";

    private String coins[] = new String[] {"bitcoin","ethereum","cardano"};
    private String str_coins = "bitcoin,ethereum,cardano";
    private String currencies = "btc,eth,ltc,bch,bnb,eos,xrp,xlm,link,dot,yfi,usd,aed,ars,aud,bdt,bhd,bmd,brl,cad,chf,clp,cny,czk,dkk,eur,gbp,hkd,huf,idr,ils,inr,jpy,krw,kwd,lkr,mmk,mxn,myr,ngn,nok,nzd,php,pkr,pln,rub,sar,sek,sgd,thb,try,twd,uah,vef,vnd,zar,xdr,xag,xau,bits,sats";
    private static final String base_url = "https://api.coingecko.com/api/v3/";
    private static final String end_url = "/market_chart?vs_currency=usd&days=30";
    private static final String last_updated = "simple/price?ids=bitcoin&vs_currencies=usd&include_last_updated_at=true";

    @Autowired
    Coins() throws IOException, JSONException {
        JSONObject json = new JSONObject();
        for(String c: coins){
            URL url = new URL(base_url + "coins/" + c + end_url);
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
                JSONObject obj1 = new JSONObject();
                JSONObject obj2 = new JSONObject(content.toString());
                obj1.put("usd", obj2);
                json.put(c, obj1);
            }else{
                System.out.println(String.format("error: %d", status));
            }
            con.disconnect();
        }
        kafkaController.sendMessage(TOPIC, json.toString());
        System.out.println(json.toString());
    }

    @Scheduled(fixedRate = 1000)
    public void getNews() throws IOException {
        URL url = new URL(base_url +
                String.format("simple/price?ids=%s&vs_currencies=usd&include_last_updated_at=true", str_coins));
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
            kafkaController.sendMessage(TOPIC2, content.toString());
            System.out.println(content.toString());
        }else{
            System.out.println(String.format("error: %d", status));
        }
        con.disconnect();
    }
}
