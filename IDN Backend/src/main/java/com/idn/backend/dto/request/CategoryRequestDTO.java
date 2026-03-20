package com.idn.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryRequestDTO(

                @NotBlank(message = "Category name must not be blank") String name,
                @NotNull Boolean isActive) {

}
