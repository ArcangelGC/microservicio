package com.papeleria.ventas.repository;
import com.papeleria.ventas.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    Optional<Factura> findByNumeroFactura(String numero);
    List<Factura> findByVentaId(Long ventaId);
}
