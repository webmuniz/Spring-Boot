package academy.devdojo.springboot2.service;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.exceptions.BadRequestException;
import academy.devdojo.springboot2.mapper.GameMapper;
import academy.devdojo.springboot2.repository.GameRepository;
import academy.devdojo.springboot2.requests.GamePostRequestBody;
import academy.devdojo.springboot2.requests.GamePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Page<Game> listAll(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    public List<Game> listAllNonPageable() {
        return gameRepository.findAll();
    }

    public List<Game> findByName(String name) {
        return gameRepository.findByName(name);
    }

    public Game findByIdOrThrowBadRequestException(long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Game not found"));
    }
    //Only commit if the request is complete | Not rollback to Exceptions checked -> (rollbackOn = Exception.class)

    @Transactional(rollbackOn = Exception.class)
    public Game save(GamePostRequestBody gamePostRequestBody) {
//        Game game = Game.builder().name(gamePostRequestBody.getName()).build();
//        return gameRepository.save(game);
        //With Map Structure plugin:
        return gameRepository.save(GameMapper.INSTANCE.toGame(gamePostRequestBody));
    }

    public void delete(long id) {
        gameRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(GamePutRequestBody gamePutRequestBody) {
        Game savedGame = findByIdOrThrowBadRequestException(gamePutRequestBody.getId());
//        Game game = Game.builder()
//                .id(savedGame.getId())
//                .name(gamePutRequestBody.getName())
//                .build();
        //With Map Structure plugin:
        Game game = GameMapper.INSTANCE.toGame(gamePutRequestBody);
        game.setId(savedGame.getId());
        gameRepository.save(game);
    }
}
