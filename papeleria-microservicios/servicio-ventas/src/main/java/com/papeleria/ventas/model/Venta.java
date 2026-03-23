package com.papeleria.ventas.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name="ventas")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Venta {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="cliente_id", nullable=false)
    private Long clienteId;
    @Column(name="pedido_id")
    private Long pedidoId;
    @Column(name="fecha_venta", nullable=false)
    private LocalDateTime fechaVenta;
    @Column(nullable=false)
    private Double subtotal;
    @Column(nullable=false)
    private Double impuestos;
    @Column(nullable=false)
    private Double total;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private EstadoVenta estado;
    @Enumerated(EnumType.STRING) @Column(name="metodo_pago")
    private MetodoPago metodoPago;
    @JsonIgnore
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Factura> facturas;

    public enum EstadoVenta { PENDIENTE, PAGADA, CANCELADA, REEMBOLSADA }
    public enum MetodoPago  { EFECTIVO, TARJETA, TRANSFERENCIA, CREDITO }
}
