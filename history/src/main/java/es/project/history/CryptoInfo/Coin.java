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
public class Coin {

    @Id
    private String id;
    private String symbol;
    private String name;

    @Fetch(FetchMode.SELECT)
    @OneToOne(fetch= FetchType.EAGER,targetEntity = Image.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "coin_image_fk", referencedColumnName = "id")
    private Image image;

    @Fetch(FetchMode.SELECT)
    @OneToOne(fetch= FetchType.EAGER,targetEntity = Description.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "coin_description_fk", referencedColumnName = "id")
    private Description description;

    @Fetch(FetchMode.SELECT)
    @OneToOne(fetch= FetchType.EAGER,targetEntity = MarketData.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "coin_market_data_fk", referencedColumnName = "id")
    private MarketData market_data;
}
