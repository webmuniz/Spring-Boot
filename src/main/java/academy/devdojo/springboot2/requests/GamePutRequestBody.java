package academy.devdojo.springboot2.requests;

import lombok.Data;

@Data
public class GamePutRequestBody {
    private Long id;
    private String name;
}
