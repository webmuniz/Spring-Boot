package academy.devdojo.springboot2.domain;

public class Game {
    private String name;

    public Game(String name) {
        this.name = name;
    }

    public Game() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
