package pl.rstrzalkowski.githubclient.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface GithubHttpClient {
    @GetExchange("/users/{username}/repos")
    List<RepositoryDto> getUserRepositories(@PathVariable String username);

    @GetExchange("/repos/{username}/{repositoryName}/branches")
    List<RepositoryBranchDto> getRepositoryBranches(@PathVariable String username, @PathVariable String repositoryName);
}
