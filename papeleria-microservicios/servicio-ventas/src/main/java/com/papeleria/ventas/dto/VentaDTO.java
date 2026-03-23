package com.papeleria.ventas.dto;
import com.papeleria.ventas.model.Venta.MetodoPago;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class VentaDTO {
    @NotNull private Long clienteId;
    private Long pedidoId;
    @NotNull private Double subtotal;
    @NotNull private MetodoPago metodoPago;
}
