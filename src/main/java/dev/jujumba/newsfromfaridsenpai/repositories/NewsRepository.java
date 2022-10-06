package dev.jujumba.newsfromfaridsenpai.repositories;

import dev.jujumba.newsfromfaridsenpai.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jujumba
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    boolean existsByFullTitle(String fullTitle);
    boolean existsByUrl(String url);
    @Query(value = "from News n order by n.dateTime desc")
    List<News> findAllAndSortByDate();
}
