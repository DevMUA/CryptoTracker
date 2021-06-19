package com.crypto_tracker.retrieval.coins;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class MarketData {

    private CurrentPrice current_price;
    private MarketCap market_cap;
    private float price_change_percentage_24h;
    private int total_supply;
    private int max_supply;
    private int circulating_supply;
}
