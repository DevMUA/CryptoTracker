package com.crypto_tracker.retrieval.coins;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class GraphicalCoinInformation {

    private String id;
    private List<GraphParsedInfo> plotPoints;

}
