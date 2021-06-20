package es.project.history.CryptoInfo;

import es.project.history.Repository.CryptoInfoRepository;
import es.project.history.Repository.GraphicalCoinInformationRepository;
import es.project.history.Requests.AlarmQueryObject;
import es.project.history.Requests.AlarmResponseObject;
import es.project.history.Requests.AlarmTrueKafkaObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoInfoService {

    @Autowired
    private CryptoInfoRepository cryptoInfoRepository;

    @Autowired
    private GraphicalCoinInformationRepository graphicalCoinInformationRepository;

    public ArrayList<Coin> getCoinList(){
        ArrayList<Coin> coins = new ArrayList<>(cryptoInfoRepository.findAll());

        return coins;
    }

    public void saveCoinList(ArrayList<Coin> coins){
        System.out.println("Saving list with size: " + coins.size());
        for(int i = 0; i< coins.size() ; i++){
            cryptoInfoRepository.save(coins.get(i));
        }
    }

    public GraphicalCoinInformation getGraphInfo(String id){
        GraphicalCoinInformation coinInfo = new GraphicalCoinInformation();
        boolean exists = graphicalCoinInformationRepository.findGraphicalCoinInformationById(id).isPresent();
        if(exists){
            coinInfo = graphicalCoinInformationRepository.findGraphicalCoinInformationById(id).get();
        }
        return coinInfo;
    }

    public ArrayList<GraphicalCoinInformation> getAllGraphInfo(){
        ArrayList<GraphicalCoinInformation> graphInfo = new ArrayList<>(graphicalCoinInformationRepository.findAll());
        return graphInfo;
    }

    public void saveGraphicalPoints(ArrayList<GraphicalCoinInformation> plotInfo){
        for(int i = 0; i< plotInfo.size() ; i++){
            graphicalCoinInformationRepository.save(plotInfo.get(i));
        }
    }

    public AlarmResponseObject isAlarmTrue(AlarmTrueKafkaObject object){
        ArrayList<Integer> whichAreTrue = new ArrayList<>();
        System.out.println(object);
        for(int i = 0; i < object.getAlarms().size(); i++){
            AlarmQueryObject al = object.getAlarms().get(i);
            if(isSingleAlarmTrue(al.getCoin(),al.getCondition(),al.getValue())){
                whichAreTrue.add(i);
            }
        }

        AlarmResponseObject response = new AlarmResponseObject();
        response.setUserID(object.getUserID());
        response.setAlarmsThatAreTrue(whichAreTrue);

        return response;
    }

    private boolean isSingleAlarmTrue(String coin, String condition ,long value){
        boolean exists = cryptoInfoRepository.findCoinById(coin).isPresent();
        if(exists){
            Coin coinSearch = cryptoInfoRepository.findCoinById(coin).get();
            System.out.println("checking alarm " + coin +  " with condition : " + condition + " with value : " + value);
            if(condition.equals("Above")){
                if(coinSearch.getMarket_data().getCurrent_price().getUsd() > value){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                if(coinSearch.getMarket_data().getCurrent_price().getUsd() < value){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else{
            return false;
        }
    }

}
