package com.papeleria.proveedores.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CompraDTO {
    @NotNull private Long proveedorId;
    private String numeroFactura;
    @NotEmpty private List<DetalleCompraDTO> detalles;
}
