package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.requests.GamePostRequestBody;
import academy.devdojo.springboot2.requests.GamePutRequestBody;
import academy.devdojo.springboot2.service.GameService;
import academy.devdojo.springboot2.util.GameCreator;
import academy.devdojo.springboot2.util.GamePostRequestBodyCreator;
import academy.devdojo.springboot2.util.GamePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Game controller")
class GameControllerTest {

    @InjectMocks //When you want to test the class itself
    private GameController gameController;

    @Mock //For all classes that are inside the class
    private GameService gameServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Game> gamePage = new PageImpl<>(List.of(GameCreator.createValidGame()));

        BDDMockito.when(gameServiceMock.listAll(ArgumentMatchers.any())).thenReturn(gamePage);

        BDDMockito.when(gameServiceMock.listAllNonPageable()).thenReturn(List.of(GameCreator.createValidGame()));

        BDDMockito.when(gameServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(GameCreator.createValidGame());

        BDDMockito.when(gameServiceMock.findByname(ArgumentMatchers.anyString()))
                .thenReturn(List.of(GameCreator.createValidGame()));

        BDDMockito.when(gameServiceMock.save(ArgumentMatchers.any(GamePostRequestBody.class)))
                .thenReturn(GameCreator.createValidGame());

        BDDMockito.doNothing().when(gameServiceMock).replace(ArgumentMatchers.any(GamePutRequestBody.class));

        BDDMockito.doNothing().when(gameServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list returns list of games inside page object when successful")
    void list_ReturnsListOfGamesInsidePageObject_WhenSuccessful() {
        String expectedName = GameCreator.createValidGame().getName();

        Page<Game> gamePage = gameController.list(null).getBody();

        Assertions.assertThat(gamePage).isNotNull();

        Assertions.assertThat(gamePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(gamePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of games when successful")
    void listAll_ReturnsListOfGames_WhenSuccessful() {
        String expectedName = GameCreator.createValidGame().getName();
        List<Game> games = gameController.listAll().getBody();
        Assertions.assertThat(games)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(games.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns game when successful")
    void findById_ReturnsGame_WhenSuccessful() {
        Long expectedGame = GameCreator.createValidGame().getId();
        Game game = gameController.findById(1).getBody();
        Assertions.assertThat(game)
                .isNotNull();
        Assertions.assertThat(game.getId()).isNotNull().isEqualTo(expectedGame);
    }

    @Test
    @DisplayName("findByName returns list of games when successful")
    void findByName_ReturnsListOfGames_WhenSuccessful() {
        String expectedName = GameCreator.createValidGame().getName();
        List<Game> games = gameController.findByName("GameName").getBody();
        Assertions.assertThat(games)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(games.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns an empty list of games when game is not found")
    void findByName_ReturnsEmptyListOfGames_WhenGameIsNotFound() {

        //Overriding the behavior
        BDDMockito.when(gameServiceMock.findByname(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Game> games = gameController.findByName("GameName").getBody();
        Assertions.assertThat(games)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns game when successful")
    void save_ReturnsGame_WhenSuccessful() {
        Game game = gameController.save(GamePostRequestBodyCreator.createGamePostRequestBody()).getBody();

        Assertions.assertThat(game).isNotNull().isEqualTo(GameCreator.createValidGame());
    }

    @Test
    @DisplayName("replace updates game when successful")
    void replace_UpdatesGame_WhenSuccessFull() {

        Assertions.assertThatCode(() -> gameController.replace(GamePutRequestBodyCreator.createGamePutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = gameController.replace(GamePutRequestBodyCreator.createGamePutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete removes game when successful")
    void delete_RemoveGame_WhenSuccessFull() {

        Assertions.assertThatCode(() -> gameController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = gameController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}