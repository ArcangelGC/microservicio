package com.papeleria.inventario.dto;
import com.papeleria.inventario.model.MovimientoStock.TipoMovimiento;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MovimientoDTO {
    @NotNull private Long inventarioId;
    @NotNull private TipoMovimiento tipo;
    @NotNull @Min(1) private Integer cantidad;
    private String motivo;
    private Long referenciaId;
}
