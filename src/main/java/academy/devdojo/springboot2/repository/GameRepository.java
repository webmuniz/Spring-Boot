package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Game;

import java.util.List;

public interface GameRepository {
    List<Game> listAll();
}
