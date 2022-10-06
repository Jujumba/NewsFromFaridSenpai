package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.models.News;
import dev.jujumba.newsfromfaridsenpai.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return repository.existsByUrl(news.getUrl());
    }

    public boolean existsByFullTitle(String fullTitle) {
        return repository.existsByFullTitle(fullTitle);
    }

    public long getCount() {
        return repository.count();
    }

    public boolean existsByUrl(String url) {
        return repository.existsByUrl(url);
    }
    public List<News> findAll() {
        return repository.findAllAndSortByDate();
    }
    public boolean save(News news, LocalDateTime time) {
        if (!this.exists(news) && (LocalDateTime.now().getDayOfMonth() - time.getDayOfMonth() <= 2)) {
            repository.save(news);
            return true;
        } else return false;
    }

    public void delete(News news) {
        repository.delete(news);
    }
}
