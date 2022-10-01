package dev.jujumba.newsfromfaridsenpai.repositories;

import dev.jujumba.newsfromfaridsenpai.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Jujumba
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    boolean existsByTitleAndUrl(String title, String url);
    boolean existsByTitle(String title);
    boolean existsByFullTitle(String fullTitle);
    boolean existsByUrl(String url);
    @Override
    List<News> findAll();
}
