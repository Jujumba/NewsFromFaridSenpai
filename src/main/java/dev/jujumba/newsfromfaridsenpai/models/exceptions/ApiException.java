package dev.jujumba.newsfromfaridsenpai.models.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Jujumba
 */
@Getter
public class ApiException extends RuntimeException {
    private final String message;
    private final HttpStatus status;
    public ApiException() {
        this.message = "You haven`t provided a correct API-key! Provide a correct API-key in the Authorization header (i.e Authorization: API-KEY)";
        this.status = HttpStatus.UNAUTHORIZED;
    }

    public ApiException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
