package com.papeleria.ventas.service;
import com.papeleria.ventas.dto.*;
import com.papeleria.ventas.exception.ResourceNotFoundException;
import com.papeleria.ventas.model.*;
import com.papeleria.ventas.model.Venta.EstadoVenta;
import com.papeleria.ventas.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service @RequiredArgsConstructor
public class VentaService {
    private final VentaRepository ventaRepo;
    private final FacturaRepository facturaRepo;

    public List<Venta> listar() { return ventaRepo.findAll(); }

    public Venta buscarPorId(Long id) {
        return ventaRepo.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Venta no encontrada: " + id));
    }

    public List<Venta> porCliente(Long clienteId) { return ventaRepo.findByClienteId(clienteId); }
    public List<Venta> porEstado(EstadoVenta estado) { return ventaRepo.findByEstado(estado); }

    public List<Venta> porRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        return ventaRepo.findByFechaVentaBetween(desde, hasta);
    }

    public Venta crear(VentaDTO dto) {
        double impuestos = dto.getSubtotal() * 0.16; // IVA 16%
        double total = dto.getSubtotal() + impuestos;
        return ventaRepo.save(Venta.builder()
            .clienteId(dto.getClienteId())
            .pedidoId(dto.getPedidoId())
            .fechaVenta(LocalDateTime.now())
            .subtotal(dto.getSubtotal())
            .impuestos(impuestos)
            .total(total)
            .estado(EstadoVenta.PENDIENTE)
            .metodoPago(dto.getMetodoPago())
            .build());
    }

    public Venta cambiarEstado(Long id, EstadoVenta estado) {
        Venta v = buscarPorId(id);
        v.setEstado(estado);
        return ventaRepo.save(v);
    }

    public Factura emitirFactura(FacturaDTO dto) {
        Venta venta = buscarPorId(dto.getVentaId());
        if (venta.getEstado() != EstadoVenta.PAGADA)
            throw new IllegalStateException("Solo se puede facturar una venta PAGADA");
        String numero = "FAC-" + LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) + "-" + venta.getId();
        return facturaRepo.save(Factura.builder()
            .venta(venta)
            .numeroFactura(numero)
            .fechaEmision(LocalDateTime.now())
            .rfcCliente(dto.getRfcCliente())
            .razonSocial(dto.getRazonSocial())
            .total(venta.getTotal())
            .build());
    }

    public List<Factura> facturasPorVenta(Long ventaId) {
        return facturaRepo.findByVentaId(ventaId);
    }
}
