package br.com.samueltorga.reactor.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PersonCreateDTO {

    private String id;
    @NotNull
    @NotBlank(message = "The name of person cannot be empty")
    private String name;

}
