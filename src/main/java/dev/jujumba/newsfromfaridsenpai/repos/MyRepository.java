package dev.jujumba.newsfromfaridsenpai.repos;
import dev.jujumba.newsfromfaridsenpai.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MyRepository extends JpaRepository<News, Integer> {
    boolean existsByTitleAndUrl(String title, String url);
}
