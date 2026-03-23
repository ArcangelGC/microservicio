package com.papeleria.productos.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity @Table(name="productos")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Producto {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable=false, length=150)
    private String nombre;
    @Column(length=300)
    private String descripcion;
    @NotNull @DecimalMin("0.0") @Column(nullable=false)
    private Double precio;
    @Column(name="codigo_barras", unique=true, length=50)
    private String codigoBarras;
    @Column(name="unidad_medida", length=30)
    private String unidadMedida;
    @Column(nullable=false)
    private Boolean activo = true;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="categoria_id")
    private Categoria categoria;
}
