package dev.jujumba.newsfromfaridsenpai.repositories;

import dev.jujumba.newsfromfaridsenpai.models.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jujumba
 */
@Repository
public interface ApiKeysRepository extends JpaRepository<ApiKey, Integer> {
    boolean existsByValue(String value);
}
