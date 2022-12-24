package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.models.Image;
import dev.jujumba.newsfromfaridsenpai.repositories.ImagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jujumba
 */
@Service
@AllArgsConstructor
public class ImagesService {
    private final ImagesRepository imagesRepository;

    public void saveAll(List<Image> images) {
        imagesRepository.saveAll(images);
    }
}
