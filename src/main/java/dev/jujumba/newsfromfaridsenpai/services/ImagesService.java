package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.models.Image;
import dev.jujumba.newsfromfaridsenpai.repositories.ImagesRepository;
import org.springframework.stereotype.Service;

/**
 * @author Jujumba
 */
@Service
public class ImagesService extends AbstractService<Image> {
    public ImagesService(ImagesRepository repository) {
        super(repository);
    }
}
