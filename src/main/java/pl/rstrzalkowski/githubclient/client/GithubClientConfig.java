package pl.rstrzalkowski.githubclient.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
class GithubClientConfig {

    @Value("${github.api.url}")
    private String githubApiUrl;


    @Bean
    GithubHttpClient githubHttpClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(githubApiUrl)
                .build();

        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);

        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builderFor(restClientAdapter)
                        .build();
        return httpServiceProxyFactory.createClient(GithubHttpClient.class);
    }
}
