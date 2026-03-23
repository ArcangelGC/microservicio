package com.papeleria.productos.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductoDTO {
    @NotBlank private String nombre;
    private String descripcion;
    @NotNull @DecimalMin("0.0") private Double precio;
    private String codigoBarras;
    private String unidadMedida;
    private Boolean activo;
    @NotNull private Long categoriaId;
}
