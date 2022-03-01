package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.service.GameService;
import academy.devdojo.springboot2.util.GameCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Game controller")
class GameControllerTest {

    @InjectMocks //When you want to test the class itself
    private GameController gameController;

    @Mock //For all classes that are inside the class
    private GameService gameServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Game> gamePage = new PageImpl<>(List.of(GameCreator.createValidGame()));
        BDDMockito.when(gameServiceMock.listAll(ArgumentMatchers.any())).thenReturn(gamePage);
    }

    @Test
    @DisplayName("List returns list of games inside page object when successful")
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
    @DisplayName("")
    void listAll() {
    }

    @Test
    @DisplayName("")
    void findById() {
    }

    @Test
    @DisplayName("")
    void findByName() {
    }

    @Test
    @DisplayName("")
    void save() {
    }

    @Test
    @DisplayName("")
    void delete() {
    }

    @Test
    @DisplayName("")
    void replace() {
    }
}