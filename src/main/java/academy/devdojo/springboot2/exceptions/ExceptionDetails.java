package academy.devdojo.springboot2.exceptions;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected LocalDateTime timestamp;
    protected int status;
    protected String title;
    protected String details;
    protected String developerMessage;
}
