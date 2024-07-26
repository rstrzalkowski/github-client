package pl.rstrzalkowski.githubclient.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
public class GithubClientException extends Exception {
    private HttpStatusCode status;
    private String message;
}
