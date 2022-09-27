package dev.jujumba.newsfromfaridsenpai.repositories;

import dev.jujumba.newsfromfaridsenpai.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyRepository extends JpaRepository<News, Integer> {
    boolean existsByTitleAndUrl(String title, String url);

    @Override
    List<News> findAll();
}
