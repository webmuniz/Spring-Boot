package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Game;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class GameService {

    private static final List<Game> games;

    static {
        games = new ArrayList<>(List.of(
                new Game(1L, "Red Dead Redemption II"),
                new Game(2L, "The Witcher III")
        ));
    }

    public List<Game> listAll() {
        return games;
    }

    public Game findById(long id) {
        return games.stream()
                .filter(game -> game.getId() == id) //If Long use .equals()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game not found :("));
    }

    public Game save(Game game) {
        game.setId(ThreadLocalRandom.current().nextLong(3, 10000));
        games.add(game);
        return game;
    }

    public void delete(long id) {
        games.remove(findById(id));
    }

    public void replace(Game game) {
        delete(game.getId());
        games.add(game);
    }
}
