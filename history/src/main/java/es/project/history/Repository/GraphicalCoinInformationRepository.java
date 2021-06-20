package es.project.history.Repository;

import es.project.history.CryptoInfo.Coin;
import es.project.history.CryptoInfo.GraphicalCoinInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface GraphicalCoinInformationRepository extends JpaRepository<GraphicalCoinInformation,String> {

    @Query("SELECT a FROM GraphicalCoinInformation a WHERE a.id = ?1")
    Optional<GraphicalCoinInformation> findGraphicalCoinInformationById(String id);
}
