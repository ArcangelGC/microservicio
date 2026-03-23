package com.papeleria.pedidos.model;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="detalle_pedidos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DetallePedido {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name="pedido_id",nullable=false)
    private Pedido pedido;
    @Column(name="producto_id", nullable=false)
    private Long productoId;
    @Column(nullable=false)
    private Integer cantidad;
    @Column(name="precio_unitario", nullable=false)
    private Double precioUnitario;
    @Column(name="subtotal", nullable=false)
    private Double subtotal;
}
