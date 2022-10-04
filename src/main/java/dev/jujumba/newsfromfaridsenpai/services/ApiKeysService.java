package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.repositories.ApiKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
