package com.raphael.project.ninjas.dto;

import jakarta.validation.constraints.*;

public record NinjaRequest(
        @NotBlank(message = "ninja.nome.not.blank")
        @Size(min = 3, max = 20, message = "ninja.nome.size")
        String nome,
        @NotBlank(message = "ninja.vila.not.blank")
        @Pattern(regexp = "FOLHA|SOM|AREIA", message = "ninja.vila.invalida")
        String vila,
        @NotBlank(message = "ninja.rank.not.blank")
        @Pattern(regexp = "GENIN|CHUNIN|JONIN|KAGE", message = "ninja.rank.invalido")
        String rank,
        @NotNull(message = "ninja.poder.not.null")
        @Min(value = 10, message = "ninja.poder.min")
        @Max(value = 9999, message = "ninja.poder.max")
        Double poder
) {
}
