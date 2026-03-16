package com.idn.backend.DTO.RequestDTO;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(

        @NotBlank(message = "Category name must not be blank") String name,
        Boolean isActive) {

}
