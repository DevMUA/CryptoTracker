package es.project.history.Kafka;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.project.history.CryptoInfo.Coin;
import es.project.history.CryptoInfo.GraphicalCoinInformation;
import es.project.history.Requests.AlarmTrueKafkaObject;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, List<Coin>> consumerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"group_id6");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        ObjectMapper om = new ObjectMapper();
        JavaType type = om.getTypeFactory().constructParametricType(List.class, Coin.class);
        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(), new JsonDeserializer<List<Coin>>(type, om, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<Coin>> fooListener(){
        ConcurrentKafkaListenerContainerFactory<String, List<Coin>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, List<GraphicalCoinInformation>> consumerFactory2(){
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"group_id5");
        ObjectMapper om = new ObjectMapper();
        JavaType type = om.getTypeFactory().constructParametricType(List.class, GraphicalCoinInformation.class);
        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(), new JsonDeserializer<List<GraphicalCoinInformation>>(type, om, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<GraphicalCoinInformation>> fooListener2(){
        ConcurrentKafkaListenerContainerFactory<String, List<GraphicalCoinInformation>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory2());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, List<AlarmTrueKafkaObject>> consumerFactory3(){
        Map<String,Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"group_id10");
        ObjectMapper om = new ObjectMapper();
        JavaType type = om.getTypeFactory().constructParametricType(List.class, AlarmTrueKafkaObject.class);
        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(), new JsonDeserializer<List<AlarmTrueKafkaObject>>(type, om, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, List<AlarmTrueKafkaObject>> fooListener3(){
        ConcurrentKafkaListenerContainerFactory<String, List<AlarmTrueKafkaObject>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory3());
        return factory;
    }



}