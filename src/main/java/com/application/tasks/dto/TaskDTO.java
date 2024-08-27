package com.application.tasks.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {

    private Long id;
    @NotBlank(message = "La descripción es requerida") //El NotBlank valida que no sea nulo ni blanco de una vez
    private String description;
    @NotNull(message = "La fecha de creación es requerida")
    private LocalDateTime dateCreate;
    @NotNull(message = "La vigencia es requerida")
    private Boolean isCurrent;

}
