package com.papeleria.proveedores.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleCompraDTO {
    @NotNull private Long productoId;
    @NotNull @Min(1) private Integer cantidad;
    @NotNull @DecimalMin("0.0") private Double precioUnitario;
}
