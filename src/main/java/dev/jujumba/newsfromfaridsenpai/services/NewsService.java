package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jujumba
 */
@Service
public class NewsService {
    private final NewsRepository repository;
    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }
    public boolean exists(News news) {
        return repository.existsByUrl(news.getUrl()) && repository.existsByFullTitle(news.getFullTitle());
    }

    public boolean existsByFullTitle(String fullTitle) {
        return repository.existsByFullTitle(fullTitle);
    }

    public boolean existsByUrl(String url) {
        return repository.existsByUrl(url);
    }
    public List<News> findAll() {
        return repository.findAll();
    }
    public void save(News news) {
        repository.save(news);
    }

    public void delete(News news) {
        repository.delete(news);
    }
}
