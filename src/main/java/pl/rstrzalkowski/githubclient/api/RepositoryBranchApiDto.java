package pl.rstrzalkowski.githubclient.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.rstrzalkowski.githubclient.client.RepositoryBranchDto;


@NoArgsConstructor
@Getter
@Setter
class RepositoryBranchApiDto {
    private String name;
    private String lastCommitSHA;

    public RepositoryBranchApiDto(RepositoryBranchDto dto) {
        this.name = dto.getName();
        this.lastCommitSHA = dto.getLastCommitSHA();
    }
}
