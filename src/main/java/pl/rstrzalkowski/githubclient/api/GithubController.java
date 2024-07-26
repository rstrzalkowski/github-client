package pl.rstrzalkowski.githubclient.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.rstrzalkowski.githubclient.exception.GithubClientException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/github")
public class GithubController {

    private final GithubService githubService;

    @GetMapping("/{username}/repositories")
    public List<RepositoryApiDto> getGithubRepositoriesByUsername(@PathVariable String username) throws GithubClientException {
        return githubService.getGithubRepositoriesByUsername(username);
    }
}
