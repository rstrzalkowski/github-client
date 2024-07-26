package pl.rstrzalkowski.githubclient.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class UserNotFoundException extends GithubClientException {
    public UserNotFoundException(HttpStatusCode status) {
        super(status, "User not found");
    }
}
