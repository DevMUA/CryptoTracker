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
    private long total_supply;
    private long max_supply;
    private long circulating_supply;
}
