package com.crypto_tracker.retrieval.coins;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class Coin {

    private String id;
    private String symbol;
    private String name;
    private Image image;
    private Description description;
    private MarketData market_data;
}
