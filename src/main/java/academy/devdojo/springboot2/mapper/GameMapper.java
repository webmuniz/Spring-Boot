package academy.devdojo.springboot2.mapper;

import academy.devdojo.springboot2.domain.Game;
import academy.devdojo.springboot2.requests.GamePostRequestBody;
import academy.devdojo.springboot2.requests.GamePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class GameMapper {
    public static final GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);
    public abstract Game toGame(GamePostRequestBody gamePostRequestBody);
    public abstract Game toGame(GamePutRequestBody gamePutRequestBody);
}
