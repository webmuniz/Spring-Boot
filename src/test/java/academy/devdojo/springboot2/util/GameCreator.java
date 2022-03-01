package academy.devdojo.springboot2.util;

import academy.devdojo.springboot2.domain.Game;

public class GameCreator {

    public static Game createGameToBeSaved() {
        return Game.builder().name("Silent Hill").build();
    }

    public static Game createValidGame() {
        return Game.builder().name("Silent Hill 2").id(1L).build();
    }

    public static Game createValidUpdatedGame() {
        return Game.builder().name("Silent Hill 3").id(1L).build();
    }
}
