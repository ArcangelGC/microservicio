package com.papeleria.proveedores.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Entity @Table(name="proveedores")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Proveedor {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable=false, length=150)
    private String nombre;
    @Column(name="rfc", unique=true, length=20)
    private String rfc;
    @Email @Column(unique=true, length=150)
    private String email;
    @Column(length=20)
    private String telefono;
    @Column(length=200)
    private String direccion;
    @Column(nullable=false)
    private Boolean activo = true;
    //@OneToMany(mappedBy="proveedor", cascade=CascadeType.ALL)
    //private List<Compra> compras;

    @JsonIgnore
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL)
    private List<Compra> compras;
}
