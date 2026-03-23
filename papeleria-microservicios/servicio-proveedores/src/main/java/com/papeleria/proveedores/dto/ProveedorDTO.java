package com.papeleria.proveedores.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProveedorDTO {
    @NotBlank private String nombre;
    private String rfc;
    @Email private String email;
    private String telefono;
    private String direccion;
}
