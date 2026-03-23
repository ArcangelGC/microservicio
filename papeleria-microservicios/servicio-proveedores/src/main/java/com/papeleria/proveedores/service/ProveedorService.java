package com.papeleria.proveedores.service;
import com.papeleria.proveedores.dto.*;
import com.papeleria.proveedores.exception.ResourceNotFoundException;
import com.papeleria.proveedores.model.*;
import com.papeleria.proveedores.model.Compra.EstadoCompra;
import com.papeleria.proveedores.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class ProveedorService {
    private final ProveedorRepository provRepo;
    private final CompraRepository compraRepo;

    public List<Proveedor> listar() { return provRepo.findByActivoTrue(); }
    public Proveedor buscarPorId(Long id) {
        return provRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Proveedor no encontrado: "+id));
    }
    public Proveedor crear(ProveedorDTO dto) {
        return provRepo.save(Proveedor.builder()
            .nombre(dto.getNombre()).rfc(dto.getRfc()).email(dto.getEmail())
            .telefono(dto.getTelefono()).direccion(dto.getDireccion()).activo(true).build());
    }
    public Proveedor actualizar(Long id, ProveedorDTO dto) {
        Proveedor p = buscarPorId(id);
        p.setNombre(dto.getNombre()); p.setRfc(dto.getRfc());
        p.setEmail(dto.getEmail()); p.setTelefono(dto.getTelefono());
        p.setDireccion(dto.getDireccion());
        return provRepo.save(p);
    }
    public void eliminar(Long id) { Proveedor p=buscarPorId(id); p.setActivo(false); provRepo.save(p); }

    public Compra registrarCompra(CompraDTO dto) {
        Proveedor prov = buscarPorId(dto.getProveedorId());
        List<DetalleCompra> detalles = dto.getDetalles().stream().map(d ->
            new DetalleCompra(d.getProductoId(), d.getCantidad(), d.getPrecioUnitario(),
                d.getCantidad() * d.getPrecioUnitario())
        ).collect(Collectors.toList());
        double total = detalles.stream().mapToDouble(DetalleCompra::getSubtotal).sum();
        return compraRepo.save(Compra.builder()
            .proveedor(prov).fechaCompra(LocalDateTime.now())
            .total(total).estado(EstadoCompra.PENDIENTE)
            .numeroFactura(dto.getNumeroFactura()).detalles(detalles).build());
    }
    public List<Compra> comprasPorProveedor(Long provId) { return compraRepo.findByProveedorId(provId); }
}
