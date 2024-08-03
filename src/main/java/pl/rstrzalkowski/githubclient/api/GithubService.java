package pl.rstrzalkowski.githubclient.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import pl.rstrzalkowski.githubclient.client.GithubHttpClient;
import pl.rstrzalkowski.githubclient.exception.GithubClientException;
import pl.rstrzalkowski.githubclient.exception.UserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubHttpClient githubHttpClient;

    public List<RepositoryApiDto> getGithubRepositoriesByUsername(String username) throws GithubClientException {
        try {
            return githubHttpClient.getUserRepositories(username).parallelStream()
                    .filter(r -> !r.isFork())
                    .map(RepositoryApiDto::new)
                    .peek(dto -> {
                        List<RepositoryBranchApiDto> branches = getRepositoryBranches(username, dto.getName());
                        dto.setOwner(username);
                        dto.setBranches(branches);
                    })
                    .toList();
        } catch (HttpClientErrorException httpEx) {
            throw new UserNotFoundException(httpEx.getStatusCode());
        }
    }

    private List<RepositoryBranchApiDto> getRepositoryBranches(String username, String repositoryName) {
        return githubHttpClient.getRepositoryBranches(username, repositoryName)
                .stream()
                .map(RepositoryBranchApiDto::new)
                .toList();
    }
}
