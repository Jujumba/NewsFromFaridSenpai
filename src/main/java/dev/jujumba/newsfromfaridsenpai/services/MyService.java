package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.model.News;
import dev.jujumba.newsfromfaridsenpai.repositories.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {
    private final MyRepository repository;
    @Autowired
    public MyService(MyRepository myRepository) {
        this.repository = myRepository;
    }
    public boolean exists(News news) {
        return repository.existsByTitleAndUrl(news.getTitle(), news.getUrl());
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
