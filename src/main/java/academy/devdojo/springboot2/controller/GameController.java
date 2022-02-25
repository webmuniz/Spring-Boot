package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.service.GameService;
import academy.devdojo.springboot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("games")
@Log4j2
@RequiredArgsConstructor
@Configuration

public class GameController {
    private final DateUtil dateUtil;
    private final GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> list(){
        log.info(dateUtil.formatLocalDateTimeToDataBaseStyle(LocalDateTime.now()));
        //return new ResponseEntity<>(gameService.listAll(), HttpStatus.OK);
        return ResponseEntity.ok(gameService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Game> findById(@PathVariable long id){
        return ResponseEntity.ok(gameService.findById(id));
    }
}
