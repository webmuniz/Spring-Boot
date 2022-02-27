package academy.devdojo.springboot2.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BadRequestExceptionDetails {
    private LocalDateTime timestamp;
    private int status;
    private String title;
    private String details;
    private String developerMessage;
}
