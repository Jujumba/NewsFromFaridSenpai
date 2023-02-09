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
public class NewsService extends AbstractService<News> {
    @Autowired
    public NewsService(NewsRepository newsRepository) {
        super(newsRepository);
    }
    public boolean existsByUrl(String url) {
        return ((NewsRepository) repository).existsByUrl(url);
    }
    public List<News> getWhereTitleLike(String query) {
        return ((NewsRepository) repository).findByTitleIsContainingIgnoreCase(query);
    }
}
