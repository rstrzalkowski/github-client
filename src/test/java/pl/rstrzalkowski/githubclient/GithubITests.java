package pl.rstrzalkowski.githubclient;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import pl.rstrzalkowski.githubclient.api.ErrorResponse;
import pl.rstrzalkowski.githubclient.api.RepositoryApiDto;
import pl.rstrzalkowski.githubclient.api.RepositoryBranchApiDto;

import java.io.IOException;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.when;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static wiremock.org.apache.commons.io.IOUtils.resourceToString;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@WireMockTest(httpPort = 8081)
public class GithubITests {

    @LocalServerPort
    private Integer port;

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("github.api.url", () -> "http://localhost:8081");
    }


    @BeforeEach
    void setUp() throws IOException {
        RestAssured.baseURI = "http://localhost:" + port + "/api";

        String responseBodyForExistentUser = resourceToString("/github/existent-user-repos.json", UTF_8);
        stubFor(get(urlEqualTo("/users/rstrzalkowski/repos"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(responseBodyForExistentUser)
                )
        );

        String responseBodyDesx = resourceToString("/github/branches-desx.json", UTF_8);
        stubFor(get(urlEqualTo("/repos/rstrzalkowski/DESX/branches"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(responseBodyDesx)
                )
        );

        String responseBodyForEmarket = resourceToString("/github/branches-emarket.json", UTF_8);
        stubFor(get(urlEqualTo("/repos/rstrzalkowski/E-Market/branches"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(responseBodyForEmarket)
                )
        );

        String responseBodyForNonExistentUser = resourceToString("/github/non-existent-user-repos.json", UTF_8);
        stubFor(get(urlEqualTo("/users/nonexistent/repos"))
                .willReturn(
                        aResponse()
                                .withStatus(404)
                                .withHeader("Content-Type", "application/json")
                                .withBody(responseBodyForNonExistentUser)
                )
        );

        String responseBodyForUserWithOnlyForks = resourceToString("/github/only-forks-repos.json", UTF_8);
        stubFor(get(urlEqualTo("/users/only-forks/repos"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody(responseBodyForUserWithOnlyForks)
                )
        );
    }

    @Test
    void shouldReturn200AndListOfNonForkedReposWithBranches() {
        Response response = when()
                .get("/github/rstrzalkowski/repositories");

        List<RepositoryApiDto> repositories = List.of(response.getBody().as(RepositoryApiDto[].class));

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON);

        assertEquals(2, repositories.size());
        for (RepositoryApiDto repository : repositories) {
            assertNotNull(repository.getName());
            assertNotNull(repository.getOwner());
            assertNotNull(repository.getBranches());
            for (RepositoryBranchApiDto branch : repository.getBranches()) {
                assertNotNull(branch.getLastCommitSHA());
                assertNotNull(branch.getName());
            }
        }
    }

    @Test
    void shouldReturn404AndErrorMessageForNonExistentUser() {
        Response response = when()
                .get("/github/nonexistent/repositories");

        ErrorResponse errorResponse = response.getBody().as(ErrorResponse.class);

        response.then()
                .statusCode(404)
                .contentType(ContentType.JSON);

        assertEquals(404, errorResponse.getStatus());
        assertEquals("User not found", errorResponse.getMessage());
    }

    @Test
    void shouldReturn200AndEmptyListWhenUserHasOnlyForks() {
        Response response = when()
                .get("/github/only-forks/repositories");

        List<RepositoryApiDto> repositories = List.of(response.getBody().as(RepositoryApiDto[].class));

        response.then()
                .statusCode(200)
                .contentType(ContentType.JSON);

        assertEquals(0, repositories.size());
    }
}
