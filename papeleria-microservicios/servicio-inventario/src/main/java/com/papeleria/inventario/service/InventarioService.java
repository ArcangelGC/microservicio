package com.papeleria.inventario.service;
import com.papeleria.inventario.dto.*;
import com.papeleria.inventario.exception.ResourceNotFoundException;
import com.papeleria.inventario.model.*;
import com.papeleria.inventario.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class InventarioService {
    private final InventarioRepository invRepo;
    private final MovimientoRepository movRepo;

    public List<Inventario> listar() { return invRepo.findAll(); }
    public Inventario buscarPorId(Long id) {
        return invRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Inventario no encontrado: "+id));
    }
    public Inventario buscarPorProducto(Long productoId) {
        return invRepo.findByProductoId(productoId).orElseThrow(()->new ResourceNotFoundException("Inventario no encontrado para producto: "+productoId));
    }
    public List<Inventario> stockBajo() { return invRepo.findByStockActualLessThanEqual(5); }

    public Inventario crear(InventarioDTO dto) {
        return invRepo.save(Inventario.builder()
            .productoId(dto.getProductoId()).stockActual(dto.getStockActual())
            .stockMinimo(dto.getStockMinimo()!=null?dto.getStockMinimo():5)
            .stockMaximo(dto.getStockMaximo()).ubicacionAlmacen(dto.getUbicacionAlmacen()).build());
    }

    public MovimientoStock registrarMovimiento(MovimientoDTO dto) {
        Inventario inv = buscarPorId(dto.getInventarioId());
        int anterior = inv.getStockActual();
        int nuevo;
        switch(dto.getTipo()) {
            case ENTRADA -> nuevo = anterior + dto.getCantidad();
            case SALIDA -> {
                if (anterior < dto.getCantidad()) throw new IllegalStateException("Stock insuficiente. Disponible: "+anterior);
                nuevo = anterior - dto.getCantidad();
            }
            default -> nuevo = dto.getCantidad();
        }
        inv.setStockActual(nuevo);
        invRepo.save(inv);
        return movRepo.save(MovimientoStock.builder()
            .inventario(inv).tipo(dto.getTipo()).cantidad(dto.getCantidad())
            .stockAnterior(anterior).stockNuevo(nuevo).motivo(dto.getMotivo())
            .fechaMovimiento(LocalDateTime.now()).referenciaId(dto.getReferenciaId()).build());
    }
    public List<MovimientoStock> historial(Long inventarioId) {
        return movRepo.findByInventarioIdOrderByFechaMovimientoDesc(inventarioId);
    }
}
