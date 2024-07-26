package pl.rstrzalkowski.githubclient.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.rstrzalkowski.githubclient.client.RepositoryDto;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
class RepositoryApiDto {
    private String owner;
    private String name;
    private List<RepositoryBranchApiDto> branches;

    public RepositoryApiDto(RepositoryDto dto) {
        this.name = dto.getName();
    }
}
