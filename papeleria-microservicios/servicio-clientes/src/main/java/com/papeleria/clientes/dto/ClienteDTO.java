package com.papeleria.clientes.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ClienteDTO {
    @NotBlank(message="El nombre es obligatorio")
    private String nombre;
    @NotBlank(message="El apellido es obligatorio")
    private String apellido;
    @Email(message="Email inválido") @NotBlank
    private String email;
    private String telefono;
    private LocalDate fechaRegistro;
    private Boolean activo;
}
