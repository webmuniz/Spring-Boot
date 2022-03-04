package academy.devdojo.springboot2.integration;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.repository.GameRepository;
import academy.devdojo.springboot2.requests.GamePostRequestBody;
import academy.devdojo.springboot2.util.GameCreator;
import academy.devdojo.springboot2.util.GamePostRequestBodyCreator;
import academy.devdojo.springboot2.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GameControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    @Autowired
    private GameRepository gameRepository;

    @Test
    @DisplayName("list returns list of games inside page object when successful")
    void list_ReturnsListOfGamesInsidePageObject_WhenSuccessful() {

        gameRepository.save(GameCreator.createValidGame());
        String expectedName = GameCreator.createValidGame().getName();

        final PageableResponse<Game> gamePage = testRestTemplate.exchange("/games", HttpMethod.GET,
                null, new ParameterizedTypeReference<PageableResponse<Game>>() {
                }).getBody();

        Assertions.assertThat(gamePage).isNotNull();

        Assertions.assertThat((gamePage.toList())).isNotEmpty().hasSize(1);

        Assertions.assertThat(gamePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of games when successful")
    void listAll_ReturnsListOfGames_WhenSuccessful() {
        final Game savedGame = gameRepository.save(GameCreator.createValidGame());

        String expectedName = savedGame.getName();

        List<Game> gameList = testRestTemplate.exchange("/games/all", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Game>>() {
                }).getBody();

        Assertions.assertThat(gameList).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(gameList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns game when successful")
    void findById_ReturnsGame_WhenSuccessful() {

        final Game savedGame = gameRepository.save(GameCreator.createValidGame());

        Long expectedId = savedGame.getId();

        Game game = testRestTemplate.getForObject("/games/{id}", Game.class, expectedId);

        Assertions.assertThat(game)
                .isNotNull();
        Assertions.assertThat(game.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns list of games when successful")
    void findByName_ReturnsListOfGames_WhenSuccessful() {
        final Game savedGame = gameRepository.save(GameCreator.createValidGame());

        String expectedName = savedGame.getName();

        String url = String.format("/games/find?name=%s", expectedName);

        List<Game> gameList = testRestTemplate.exchange(url, HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Game>>() {
                }).getBody();

        Assertions.assertThat(gameList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(gameList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of games when game is not found")
    void findByName_ReturnsEmptyListOfGames_WhenGameIsNotFound() {

        List<Game> gameList = testRestTemplate.exchange("/games/find?name=example", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Game>>() {
                }).getBody();

        Assertions.assertThat(gameList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns game when successful")
    void save_ReturnsGame_WhenSuccessful() {
        GamePostRequestBody gamePostRequestBody = GamePostRequestBodyCreator.createGamePostRequestBody();
        ResponseEntity<Game> entity = testRestTemplate.postForEntity("/games", gamePostRequestBody, Game.class);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(entity.getBody()).isNotNull();
        Assertions.assertThat(entity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("replace updates game when successful")
    void replace_UpdatesGame_WhenSuccessFull() {
        final Game savedGame = gameRepository.save(GameCreator.createValidGame());

        savedGame.setName("new name");

        final ResponseEntity<Void> entity = testRestTemplate
                .exchange("/games", HttpMethod.PUT, new HttpEntity<>(savedGame), Void.class);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes game when successful")
    void delete_RemoveGame_WhenSuccessFull() {
        final Game savedGame = gameRepository.save(GameCreator.createValidGame());

        final ResponseEntity<Void> entity = testRestTemplate
                .exchange("/games/{id}", HttpMethod.DELETE, null, Void.class, savedGame.getId());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
