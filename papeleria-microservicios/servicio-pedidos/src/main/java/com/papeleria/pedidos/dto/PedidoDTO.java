package com.papeleria.pedidos.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PedidoDTO {
    @NotNull private Long clienteId;
    private String observaciones;
    @NotEmpty private List<DetallePedidoDTO> detalles;
}
