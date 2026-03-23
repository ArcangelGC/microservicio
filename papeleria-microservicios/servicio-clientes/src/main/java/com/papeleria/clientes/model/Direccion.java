package com.papeleria.clientes.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity @Table(name="direcciones")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Direccion {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable=false, length=200)
    private String calle;
    @NotBlank @Column(nullable=false, length=100)
    private String ciudad;
    @NotBlank @Column(nullable=false, length=100)
    private String estado;
    @Column(name="codigo_postal", length=10)
    private String codigoPostal;
    @Column(nullable=false)
    private Boolean esPrincipal = false;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cliente_id", nullable=false)
    private Cliente cliente;
}
