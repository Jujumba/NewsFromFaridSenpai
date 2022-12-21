package dev.jujumba.newsfromfaridsenpai.repositories;

import dev.jujumba.newsfromfaridsenpai.models.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jujumba
 */
@Repository
public interface ApiKeysRepository extends JpaRepository<ApiKey, Integer> {
    boolean existsByValue(String value);
    @Query("from api_keys keys where keys.user_id = ?1")
    List<ApiKey> findAllByUser(Integer id); //may be simpler
}
