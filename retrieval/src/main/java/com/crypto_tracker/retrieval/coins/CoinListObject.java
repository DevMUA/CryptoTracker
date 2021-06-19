package com.crypto_tracker.retrieval.coins;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinListObject {

    private String id;
    private String symbol;
    private String name;

}
