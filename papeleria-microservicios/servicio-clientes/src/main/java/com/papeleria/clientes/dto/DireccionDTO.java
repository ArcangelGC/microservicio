package com.papeleria.clientes.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DireccionDTO {
    @NotBlank private String calle;
    @NotBlank private String ciudad;
    @NotBlank private String estado;
    private String codigoPostal;
    private Boolean esPrincipal;
    @NotNull private Long clienteId;
}
