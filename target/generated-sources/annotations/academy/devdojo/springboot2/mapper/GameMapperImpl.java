package academy.devdojo.springboot2.mapper;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.domain.Game.GameBuilder;
import academy.devdojo.springboot2.requests.GamePostRequestBody;
import academy.devdojo.springboot2.requests.GamePutRequestBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-03T21:45:32-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class GameMapperImpl extends GameMapper {

    @Override
    public Game toGame(GamePostRequestBody gamePostRequestBody) {
        if ( gamePostRequestBody == null ) {
            return null;
        }

        GameBuilder game = Game.builder();

        game.name( gamePostRequestBody.getName() );

        return game.build();
    }

    @Override
    public Game toGame(GamePutRequestBody gamePutRequestBody) {
        if ( gamePutRequestBody == null ) {
            return null;
        }

        GameBuilder game = Game.builder();

        if ( gamePutRequestBody.getId() != null ) {
            game.id( gamePutRequestBody.getId() );
        }
        game.name( gamePutRequestBody.getName() );

        return game.build();
    }
}
