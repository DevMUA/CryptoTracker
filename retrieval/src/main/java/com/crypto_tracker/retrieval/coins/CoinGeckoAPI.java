package com.crypto_tracker.retrieval.coins;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CoinGeckoAPI {

    private static final String URL = "https://api.coingecko.com/api/v3/";
    private String URL_COINLIST = "coins/list?include_platform=false";
    private String URL_INDIVIDUAL_COIN = "coins/bitcoin?tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false";

    private RestTemplate restTemplate;

    public CoinGeckoAPI(){
        this.restTemplate  = new RestTemplate();
    }

    private List<CoinListObject> getCoinList(){
        ResponseEntity<CoinListObject[]> response = restTemplate.getForEntity(URL+URL_COINLIST,CoinListObject[].class);
        CoinListObject[] objectList = response.getBody();

        List<CoinListObject> coinList = Arrays.asList(objectList);

        return coinList;
    }

    public List<Coin> getCoins(){
        List<CoinListObject> coinToCallList = getCoinList();
        List<Coin> coinList = new LinkedList<Coin>();
        for(int i = 0 ; i < coinToCallList.size(); i++){
            System.out.println("Calling for coin " + coinToCallList.get(i).getName());
            ResponseEntity<Coin> response = restTemplate.getForEntity(URL+String.format("coins/%s?tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false",coinToCallList.get(i).getId()),Coin.class);
            coinList.add(response.getBody());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //coinList.get(0).getDescription().getEn();
        return coinList;
    }

}
