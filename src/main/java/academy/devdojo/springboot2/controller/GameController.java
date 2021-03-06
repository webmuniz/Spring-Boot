package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.requests.GamePostRequestBody;
import academy.devdojo.springboot2.requests.GamePutRequestBody;
import academy.devdojo.springboot2.service.GameService;
import academy.devdojo.springboot2.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public ResponseEntity<Page<Game>> list(Pageable pageable) {
        //return new ResponseEntity<>(gameService.listAll(), HttpStatus.OK);
        return ResponseEntity.ok(gameService.listAll(pageable));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Game>> listAll() {
        return ResponseEntity.ok(gameService.listAllNonPageable());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Game> findById(@PathVariable long id) {
        return ResponseEntity.ok(gameService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "by-id/{id}")
    public ResponseEntity<Game> findByIdAuth(@PathVariable long id,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return ResponseEntity.ok(gameService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Game>> findByName(@RequestParam(required = false) String name) {
        return ResponseEntity.ok(gameService.findByName(name));
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Game> save(@RequestBody @Valid GamePostRequestBody gamePostRequestBody) {
        return new ResponseEntity<>(gameService.save(gamePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        gameService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody GamePutRequestBody gamePutRequestBody) {
        gameService.replace(gamePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
