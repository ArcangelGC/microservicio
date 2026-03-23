package com.papeleria.ventas.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="facturas")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Factura {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name="venta_id", nullable=false)
    private Venta venta;
    @Column(name="numero_factura", unique=true, nullable=false, length=50)
    private String numeroFactura;
    @Column(name="fecha_emision", nullable=false)
    private LocalDateTime fechaEmision;
    @Column(name="rfc_cliente", length=20)
    private String rfcCliente;
    @Column(name="razon_social", length=200)
    private String razonSocial;
    @Column(nullable=false)
    private Double total;
    @Column(name="xml_sat", columnDefinition="TEXT")
    private String xmlSat;
}
