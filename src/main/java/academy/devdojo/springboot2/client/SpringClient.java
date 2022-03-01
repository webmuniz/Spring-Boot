package academy.devdojo.springboot2.client;

import academy.devdojo.springboot2.domain.Game;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
        ResponseEntity<Game> entity = new RestTemplate().getForEntity(
                "http://localhost:8080/games/{id}", Game.class, 6);
        log.info(entity);

        Game object = new RestTemplate().getForObject("http://localhost:8080/games/{id}", Game.class, 7);
        log.info(object);

        //Using Arrays
        Game[] games = new RestTemplate().getForObject("http://localhost:8080/games/all", Game[].class);
        log.info(Arrays.toString(games));
        //Using Lists
        ResponseEntity<List<Game>> games2 = new RestTemplate().exchange(
                "http://localhost:8080/games/all", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Game>>() {
                });
        log.info(games2.getBody());

//        Game kingdom = Game.builder().name("Kingdom Heart").build();
//        final Game kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/games/", kingdom, Game.class);
//        log.info("Saved Game: {}", kingdomSaved);

        Game metalGear = Game.builder().name("Metal Gear Solid 3").build();
        ResponseEntity<Game> metalGearSaved = new RestTemplate()
                .exchange(
                        "http://localhost:8080/games/",
                        HttpMethod.POST,
                        new HttpEntity<>(metalGear, createJsonHeader()),
                        Game.class);
        log.info("Saved Game: {}", metalGearSaved);


        Game gameToBeUpdated = metalGearSaved.getBody();
        gameToBeUpdated.setName("Metal Gear Solid V - Phantom Pain");

//UPDATE

        ResponseEntity<Void> metalGearUpdated = new RestTemplate()
                .exchange(
                        "http://localhost:8080/games/",
                        HttpMethod.PUT,
                        new HttpEntity<>(metalGear, createJsonHeader()),
                        Void.class);
        log.info(metalGearUpdated);

//DELETE

        ResponseEntity<Void> metalGearDeleted = new RestTemplate()
                .exchange(
                        "http://localhost:8080/games/{id}",
                        HttpMethod.DELETE,
                        null,
                        Void.class,
                        gameToBeUpdated.getId());
        log.info(metalGearDeleted);
    }

    private static HttpHeaders createJsonHeader(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
