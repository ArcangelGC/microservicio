package com.papeleria.clientes.repository;
import com.papeleria.clientes.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByClienteId(Long clienteId);
    List<Direccion> findByClienteIdAndEsPrincipalTrue(Long clienteId);
}
