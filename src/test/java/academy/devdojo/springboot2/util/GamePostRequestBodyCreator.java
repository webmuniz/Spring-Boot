package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.requests.GamePostRequestBody;

public class GamePostRequestBodyCreator {

    public static GamePostRequestBody createGamePostRequestBody() {
        return GamePostRequestBody.builder()
                .name(GameCreator.createGameToBeSaved().getName())
                .build();
    }
}
