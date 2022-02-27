package academy.devdojo.springboot2.requests;

import lombok.Builder;
import lombok.Data;

@Data
public class GamePostRequestBody {
    private String name;
}
