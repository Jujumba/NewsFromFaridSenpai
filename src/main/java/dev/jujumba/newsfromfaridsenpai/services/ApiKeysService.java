package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.models.ApiKey;
import dev.jujumba.newsfromfaridsenpai.repositories.ApiKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void remove(int id) {
        apiKeysRepository.deleteApiKeyById(id);
    }

    public void save(ApiKey apiKey) {
        apiKeysRepository.save(apiKey);
    }
}
