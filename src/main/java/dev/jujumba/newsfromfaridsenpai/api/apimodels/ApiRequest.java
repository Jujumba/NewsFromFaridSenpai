package dev.jujumba.newsfromfaridsenpai.api.apimodels;

import lombok.Data;

/**
 * @author Jujumba
 */
@Data
public class ApiRequest {
    private int amount;

    public ApiRequest() {
        this.amount = 1;
    }

    public ApiRequest(int amount) {
        this.amount = amount;
    }
}

