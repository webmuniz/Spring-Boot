package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Game;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GameService {

    private List<Game> games =  List.of(new Game(1L,"Red Dead Redemption II"), new Game(2L,"The Witcher III"));

    public List<Game> listAll(){
        return games;
    }

    public Game findById(long id) {
        return games.stream()
                .filter(game -> game.getId() == id) //If Long use .equals()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game not found :("));
    }

}
