package dev.jujumba.newsfromfaridsenpai.api;

import dev.jujumba.newsfromfaridsenpai.models.exceptions.ApiException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Jujumba
 */
@Getter
public class ApiResponse {
    private String message;
    private HttpStatus status;
    public ApiResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
    public ApiResponse(ApiException e) {
        this.message = e.getMessage();
        this.status = e.getStatus();
    }
}
