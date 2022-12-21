package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.models.ApiKey;
import dev.jujumba.newsfromfaridsenpai.models.User;
import dev.jujumba.newsfromfaridsenpai.repositories.ApiKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jujumba
 */
@Service
public class ApiKeysService {
    private final ApiKeysRepository apiKeysRepository;

    @Autowired
    public ApiKeysService(ApiKeysRepository apiKeysRepository) {
        this.apiKeysRepository = apiKeysRepository;
    }

    public boolean exists(String apiKeyValue) {
        return apiKeysRepository.existsByValue(apiKeyValue);
    }

    public List<ApiKey> findAllByUser(User user) {
        return findAllByUser(user.getId());
    }

    public List<ApiKey> findAllByUser(int id) {
        return apiKeysRepository.findAllByUser(id);
    }
}
