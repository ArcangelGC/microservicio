package com.papeleria.pedidos.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name="pedidos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Pedido {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="cliente_id", nullable=false)
    private Long clienteId;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private EstadoPedido estado;
    @Column(name="fecha_pedido", nullable=false)
    private LocalDateTime fechaPedido;
    @Column(name="fecha_entrega")
    private LocalDateTime fechaEntrega;
    @Column(name="total", nullable=false)
    private Double total;
    @Column(length=300)
    private String observaciones;
    @JsonIgnore
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetallePedido> detalles;
    public enum EstadoPedido { PENDIENTE, CONFIRMADO, EN_PROCESO, ENVIADO, ENTREGADO, CANCELADO }
}
