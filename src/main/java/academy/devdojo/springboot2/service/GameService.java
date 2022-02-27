package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.repository.GameRepository;
import academy.devdojo.springboot2.requests.GamePostRequestBody;
import academy.devdojo.springboot2.requests.GamePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public List<Game> listAll() {
        return gameRepository.findAll();
    }

    public Game findByIdOrThrowBadRequestException(long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game not found :("));

    }

    public Game save(GamePostRequestBody gamePostRequestBody) {
        Game game = Game.builder().name(gamePostRequestBody.getName()).build();
        return gameRepository.save(game);
    }

    public void delete(long id) {
        gameRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(GamePutRequestBody gamePutRequestBody) {
        final Game savedGame = findByIdOrThrowBadRequestException(gamePutRequestBody.getId());
        Game game = Game.builder()
                .id(savedGame.getId())
                .name(gamePutRequestBody.getName())
                .build();
        gameRepository.save(game);
    }
}
