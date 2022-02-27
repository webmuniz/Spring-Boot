package academy.devdojo.springboot2.repository;

import academy.devdojo.springboot2.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

}
