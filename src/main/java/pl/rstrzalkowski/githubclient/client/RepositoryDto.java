package pl.rstrzalkowski.githubclient.client;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class RepositoryDto {
    private String name;
    private boolean fork;
}
