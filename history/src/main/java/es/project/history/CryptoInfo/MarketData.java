package es.project.history.CryptoInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@Entity
public class MarketData {

    @Id
    @GeneratedValue
    private int id;

    @Fetch(FetchMode.SELECT)
    @OneToOne(fetch= FetchType.EAGER,targetEntity = CurrentPrice.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "coin_current_price_fk", referencedColumnName = "id")
    private CurrentPrice current_price;

    @Fetch(FetchMode.SELECT)
    @OneToOne(fetch= FetchType.EAGER,targetEntity = MarketCap.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "coin_market_cap_fk", referencedColumnName = "id")
    private MarketCap market_cap;

    private float price_change_percentage_24h;
    private long total_supply;
    private long max_supply;
    private long circulating_supply;
}
