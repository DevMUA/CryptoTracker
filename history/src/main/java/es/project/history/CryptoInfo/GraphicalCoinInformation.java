package es.project.history.CryptoInfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@Entity
public class GraphicalCoinInformation {

    @Id
    private String id;

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch= FetchType.EAGER,targetEntity = GraphParsedInfo.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "coin_graph_parsed_fk", referencedColumnName = "id")
    private List<GraphParsedInfo> plotPoints;

}

