package com.papeleria.proveedores.model;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data @NoArgsConstructor @AllArgsConstructor
public class DetalleCompra {
    @Column(name="producto_id", nullable=false)
    private Long productoId;
    @Column(nullable=false)
    private Integer cantidad;
    @Column(name="precio_unitario", nullable=false)
    private Double precioUnitario;
    @Column(nullable=false)
    private Double subtotal;
}
