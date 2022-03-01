package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.util.GameCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for Game repository")
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    @DisplayName("Save persists game when successful")
    void save_PersistGame_WhenSuccessful() {
        Game gameToBeSaved = GameCreator.createGameToBeSaved();
        final Game gameSaved = this.gameRepository.save(gameToBeSaved);

        Assertions.assertThat(gameSaved).isNotNull();

        Assertions.assertThat(gameSaved.getId()).isNotNull();

        Assertions.assertThat(gameSaved.getName()).isEqualTo(gameToBeSaved.getName());
    }

    @Test
    @DisplayName("Save update game when successful")
    void save_UpdateGame_WhenSuccessful() {
        Game gameToBeUpdated = GameCreator.createGameToBeSaved();
        final Game gameSaved = this.gameRepository.save(gameToBeUpdated);
        gameSaved.setName("Armor");

        final Game gameUpdated = this.gameRepository.save(gameSaved);

        Assertions.assertThat(gameUpdated).isNotNull();

        Assertions.assertThat(gameUpdated.getId()).isNotNull();

        Assertions.assertThat(gameUpdated.getName()).isEqualTo(gameToBeUpdated.getName());
    }

    @Test
    @DisplayName("Delete removes game when successful")
    void delete_RemovesGame_WhenSuccessful() {
        Game gameToBeDeleted = GameCreator.createGameToBeSaved();
        final Game gameSaved = this.gameRepository.save(gameToBeDeleted);

        this.gameRepository.delete(gameSaved);

        final Optional<Game> gameOptional = this.gameRepository.findById(gameSaved.getId());

        Assertions.assertThat(gameOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by name returns list of game when successful")
    void findByName_ReturnsListOfGame_WhenSuccessful() {
        Game gameToBeSaved = GameCreator.createGameToBeSaved();
        final Game gameSaved = this.gameRepository.save(gameToBeSaved);

        final String name = gameSaved.getName();

        final List<Game> games = this.gameRepository.findByName(name);

        Assertions.assertThat(games)
                .isNotEmpty()
                .contains(gameSaved);
    }

    @Test
    @DisplayName("Find by name returns empty list of game when no game is found")
    void findByName_ReturnsEmptyList_WhenGameIsNotFound() {

        final List<Game> games = this.gameRepository.findByName("GameNotFound");

        Assertions.assertThat(games).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstrainViolationException when name is empty")
    void save_ThrowsConstrainViolationException_WhenNameIsEmpty() {
        Game game = new Game();

//        Assertions.assertThatThrownBy(() -> this.gameRepository.save(game))
//                .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.gameRepository.save(game))
                .withMessageContaining("The game name cannot be empty");
    }
}