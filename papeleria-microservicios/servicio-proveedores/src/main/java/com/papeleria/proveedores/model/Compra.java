package com.papeleria.proveedores.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name="compras")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Compra {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name="proveedor_id",nullable=false)
    private Proveedor proveedor;
    @Column(name="fecha_compra", nullable=false)
    private LocalDateTime fechaCompra;
    @Column(nullable=false)
    private Double total;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private EstadoCompra estado;
    @Column(name="numero_factura", length=50)
    private String numeroFactura;
    //@ElementCollection @CollectionTable(name="detalle_compras", joinColumns=@JoinColumn(name="compra_id"))
    //private List<DetalleCompra> detalles;
    //public enum EstadoCompra { PENDIENTE, RECIBIDA, CANCELADA }

    @JsonIgnore
    @ElementCollection
    @CollectionTable(name = "detalle_compras", joinColumns = @JoinColumn(name = "compra_id"))
    private List<DetalleCompra> detalles;
    public enum EstadoCompra { PENDIENTE, RECIBIDA, CANCELADA }
}
