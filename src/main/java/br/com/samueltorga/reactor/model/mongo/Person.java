package br.com.samueltorga.reactor.model.mongo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
@Builder
public class Person {

    @Id
    private String id;
    @NotNull
    @NotBlank(message = "The name of person cannot be empty")
    private String name;

}
