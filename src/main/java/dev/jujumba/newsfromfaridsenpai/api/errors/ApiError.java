package dev.jujumba.newsfromfaridsenpai.api.errors;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Jujumba
 */
@Data
public class ApiError {
    private String message;
    private HttpStatus status;

    public ApiError() {
        message = "You didn't provide a correct API-key! Provide a correct API-key in an Authorization header (i.e Authorization: API-KEY)";
        status = HttpStatus.FORBIDDEN;
    }

    public ApiError(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
