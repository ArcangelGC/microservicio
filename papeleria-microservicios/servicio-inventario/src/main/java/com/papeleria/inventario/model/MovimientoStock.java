package com.papeleria.inventario.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="movimientos_stock")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MovimientoStock {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name="inventario_id",nullable=false)
    private Inventario inventario;
    @Enumerated(EnumType.STRING) @Column(nullable=false)
    private TipoMovimiento tipo;
    @Column(nullable=false)
    private Integer cantidad;
    @Column(name="stock_anterior")
    private Integer stockAnterior;
    @Column(name="stock_nuevo")
    private Integer stockNuevo;
    @Column(length=200)
    private String motivo;
    @Column(name="fecha_movimiento")
    private LocalDateTime fechaMovimiento;
    @Column(name="referencia_id")
    private Long referenciaId;
    public enum TipoMovimiento { ENTRADA, SALIDA, AJUSTE }
}
