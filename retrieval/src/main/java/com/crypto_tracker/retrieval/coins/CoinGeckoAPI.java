package com.crypto_tracker.retrieval.coins;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
        for(int i = 0 ; i < 20/*coinToCallList.size()*/; i++){
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

    public List<GraphicalCoinInformation> getGraphPoints(){
        List<CoinListObject> coinToCallList = getCoinList();
        List<GraphicalCoinInformation> graphPoints = new LinkedList<>();
        for(int i = 0 ; i < 20/*coinToCallList.size()*/; i++){
            System.out.println("Calling for coin " + coinToCallList.get(i).getName());
            ResponseEntity<GraphPoint> response = restTemplate.getForEntity(URL+String.format("coins/%s/market_chart?vs_currency=usd&days=30",coinToCallList.get(i).getId()),GraphPoint.class);
            GraphicalCoinInformation newCoin = new GraphicalCoinInformation();
            newCoin.setId(coinToCallList.get(i).getName());
            newCoin.setPlotPoints(parseGraphInfo(response.getBody()));
            graphPoints.add(newCoin);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return graphPoints;
    }

    private List<GraphParsedInfo> parseGraphInfo(GraphPoint point){
        List<GraphParsedInfo> listParsed = new LinkedList<>();

        for(int i = 0; i < point.getPrices().length; i++) {
            GraphParsedInfo newGraphPoint = new GraphParsedInfo();
            newGraphPoint.setTime(Long.valueOf(point.getPrices()[i][0]));
            newGraphPoint.setValue(Float.valueOf(point.getPrices()[i][1]));
            listParsed.add(newGraphPoint);
        }

        return listParsed;

    }

}
