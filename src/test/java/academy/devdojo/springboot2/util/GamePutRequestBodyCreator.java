package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.requests.GamePutRequestBody;

public class GamePutRequestBodyCreator {

    public static GamePutRequestBody createGamePutRequestBody() {
        return GamePutRequestBody.builder()
                .id(GameCreator.createValidUpdatedGame().getId())
                .name(GameCreator.createValidUpdatedGame().getName())
                .build();
    }
}
