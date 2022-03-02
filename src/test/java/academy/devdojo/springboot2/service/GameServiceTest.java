package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.exceptions.BadRequestException;
import academy.devdojo.springboot2.repository.GameRepository;
import academy.devdojo.springboot2.util.GameCreator;
import academy.devdojo.springboot2.util.GamePostRequestBodyCreator;
import academy.devdojo.springboot2.util.GamePutRequestBodyCreator;
import lombok.experimental.StandardException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Game service")
class GameServiceTest {

    @InjectMocks //When you want to test the class itself
    private GameService gameService;

    @Mock //For all classes that are inside the class
    private GameRepository gameRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Game> gamePage = new PageImpl<>(List.of(GameCreator.createValidGame()));

        BDDMockito.when(gameRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(gamePage);

        BDDMockito.when(gameRepositoryMock.findAll()).thenReturn(List.of(GameCreator.createValidGame()));

        BDDMockito.when(gameRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(GameCreator.createValidGame()));

        BDDMockito.when(gameRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(GameCreator.createValidGame()));

        BDDMockito.when(gameRepositoryMock.save(ArgumentMatchers.any(Game.class)))
                .thenReturn(GameCreator.createValidGame());

        BDDMockito.doNothing().when(gameRepositoryMock).delete(ArgumentMatchers.any(Game.class));
    }

    @Test
    @DisplayName("list returns list of games inside page object when successful")
    void listAll_ReturnsListOfGamesInsidePageObject_WhenSuccessful() {
        String expectedName = GameCreator.createValidGame().getName();

        Page<Game> gamePage = gameService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(gamePage).isNotNull();

        Assertions.assertThat(gamePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(gamePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll returns list of games when successful")
    void listAllNonPageable_ReturnsListOfGames_WhenSuccessful() {
        String expectedName = GameCreator.createValidGame().getName();
        List<Game> games = gameService.listAllNonPageable();
        Assertions.assertThat(games)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(games.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns game when successful")
    void findByIdOrThrowBadRequestException_ReturnsGame_WhenSuccessful() {
        Long expectedGame = GameCreator.createValidGame().getId();
        Game game = gameService.findByIdOrThrowBadRequestException(1);
        Assertions.assertThat(game)
                .isNotNull();
        Assertions.assertThat(game.getId()).isNotNull().isEqualTo(expectedGame);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when game is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequest_WhenGameNotFound(){
        BDDMockito.when(gameRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(()-> gameService.findByIdOrThrowBadRequestException(1));
    }


    @Test
    @DisplayName("findByName returns list of games when successful")
    void findByName_ReturnsListOfGames_WhenSuccessful() {
        String expectedName = GameCreator.createValidGame().getName();
        List<Game> games = gameService.findByName("GameName");
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
        BDDMockito.when(gameRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Game> games = gameService.findByName("GameName");
        Assertions.assertThat(games)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns game when successful")
    void save_ReturnsGame_WhenSuccessful() {
        Game game = gameService.save(GamePostRequestBodyCreator.createGamePostRequestBody());

        Assertions.assertThat(game).isNotNull().isEqualTo(GameCreator.createValidGame());
    }

    @Test
    @DisplayName("replace updates game when successful")
    void replace_UpdatesGame_WhenSuccessFull() {

        Assertions.assertThatCode(() -> gameService.replace(GamePutRequestBodyCreator.createGamePutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete removes game when successful")
    void delete_RemoveGame_WhenSuccessFull() {

        Assertions.assertThatCode(() -> gameService.delete(1))
                .doesNotThrowAnyException();
    }

}