package es.project.history.Repository;

import es.project.history.CryptoInfo.Coin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface CryptoInfoRepository extends JpaRepository<Coin,String> {

    @Query("SELECT a FROM Coin a WHERE a.id = ?1")
    Optional<Coin> findCoinById(String id);
}
