package com.papeleria.inventario.repository;
import com.papeleria.inventario.model.MovimientoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoStock, Long> {
    List<MovimientoStock> findByInventarioIdOrderByFechaMovimientoDesc(Long inventarioId);
}
