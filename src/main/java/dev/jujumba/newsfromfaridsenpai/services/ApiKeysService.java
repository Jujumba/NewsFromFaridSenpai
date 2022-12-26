package dev.jujumba.newsfromfaridsenpai.services;

import dev.jujumba.newsfromfaridsenpai.models.ApiKey;
import dev.jujumba.newsfromfaridsenpai.repositories.ApiKeysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jujumba
 */
@Service
public class ApiKeysService extends AbstractService<ApiKey> {
    @Autowired
    public ApiKeysService(ApiKeysRepository apiKeysRepository) {
        super(apiKeysRepository);
    }
    public boolean exists(String apiKeyValue) {
        return ((ApiKeysRepository) repository).existsByValue(apiKeyValue);
    }
}
