package com.papeleria.inventario.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class InventarioDTO {
    @NotNull private Long productoId;
    @NotNull @Min(0) private Integer stockActual;
    @Min(0) private Integer stockMinimo;
    private Integer stockMaximo;
    private String ubicacionAlmacen;
}
