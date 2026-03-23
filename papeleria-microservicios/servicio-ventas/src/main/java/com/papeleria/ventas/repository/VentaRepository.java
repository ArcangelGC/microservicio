package com.papeleria.ventas.repository;
import com.papeleria.ventas.model.Venta;
import com.papeleria.ventas.model.Venta.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByClienteId(Long clienteId);
    List<Venta> findByEstado(EstadoVenta estado);
    List<Venta> findByFechaVentaBetween(LocalDateTime desde, LocalDateTime hasta);
}
