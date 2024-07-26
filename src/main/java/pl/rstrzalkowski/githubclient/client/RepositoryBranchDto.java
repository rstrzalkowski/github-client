package pl.rstrzalkowski.githubclient.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;


@NoArgsConstructor
@Getter
@Setter
public class RepositoryBranchDto {
    private String name;
    private String lastCommitSHA;

    @JsonProperty("commit")
    private void unpackLastCommitSHA(Map<String, String> commit) {
        this.lastCommitSHA = commit.get("sha");
    }
}
