package com.papeleria.inventario.model;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="inventario")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Inventario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name="producto_id", nullable=false, unique=true)
    private Long productoId;
    @Column(name="stock_actual", nullable=false)
    private Integer stockActual = 0;
    @Column(name="stock_minimo", nullable=false)
    private Integer stockMinimo = 5;
    @Column(name="stock_maximo")
    private Integer stockMaximo;
    @Column(name="ubicacion_almacen", length=50)
    private String ubicacionAlmacen;
}
