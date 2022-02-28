package academy.devdojo.springboot2.requests;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GamePostRequestBody {
    @NotEmpty(message = "The game name cannot be empty") //Include Null
    private String name;
}
