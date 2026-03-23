package com.papeleria.productos.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CategoriaDTO {
    @NotBlank private String nombre;
    private String descripcion;
}
