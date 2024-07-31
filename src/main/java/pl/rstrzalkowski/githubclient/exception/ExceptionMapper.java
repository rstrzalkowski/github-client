package pl.rstrzalkowski.githubclient.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.rstrzalkowski.githubclient.api.ErrorResponse;

@ControllerAdvice
public class ExceptionMapper {

    @ExceptionHandler(GithubClientException.class)
    protected ResponseEntity<Object> handleGithubClientException(GithubClientException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(ex.getStatus().value());
        errorResponse.setMessage(ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }
}
