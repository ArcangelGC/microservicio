package com.papeleria.ventas.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FacturaDTO {
    @NotNull private Long ventaId;
    private String rfcCliente;
    private String razonSocial;
}
