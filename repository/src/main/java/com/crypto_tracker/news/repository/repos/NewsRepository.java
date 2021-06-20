package com.crypto_tracker.news.repository.repos;

import com.crypto_tracker.news.repository.jpa.New;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NewsRepository extends CrudRepository<New, String> {
    List<New> findByLanguage(String language);
    List<New> findByTopic(String topic);
    List<New> findByCountry(String country);
    List<New> findByTitleContains(String title);
}
