package es.project.history.HistoricAPI;



import es.project.history.CryptoInfo.Coin;
import es.project.history.CryptoInfo.CryptoInfoService;
import es.project.history.CryptoInfo.GraphicalCoinInformation;
import es.project.history.Kafka.KafkaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@CrossOrigin("*")
public class HistoricController {

    @Autowired
    private CryptoInfoService cryptoInfoService;

    @CrossOrigin
    @GetMapping("/api/coins")
    public ArrayList<Coin> getAllCoins(){
        return cryptoInfoService.getCoinList();
    }

    @CrossOrigin
    @GetMapping("/api/historic={coinID}")
    public GraphicalCoinInformation getGraphicalInfo(@PathVariable String coinID){
        return cryptoInfoService.getGraphInfo(coinID);
    }

    @CrossOrigin
    @GetMapping("/api/historicAll")
    public ArrayList<GraphicalCoinInformation> getGAllraphicalInfo(){
        return cryptoInfoService.getAllGraphInfo();
    }

}
