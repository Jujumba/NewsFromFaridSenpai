package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.model.News;
import dev.jujumba.newsfromfaridsenpai.repos.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    private MyRepository repository;
    @Autowired
    public MyService(MyRepository myRepository) {
        this.repository = myRepository;
    }
    public boolean exists(News news) {
        return repository.existsByTitleAndUrl(news.getTitle(), news.getUrl());
    }

    public void save(News news) {
        repository.save(news);
    }

    public void delete(News news) {
        repository.delete(news);
    }
}
