package com.papeleria.pedidos.service;
import com.papeleria.pedidos.client.InventarioClient;
import com.papeleria.pedidos.dto.*;
import com.papeleria.pedidos.exception.ResourceNotFoundException;
import com.papeleria.pedidos.model.*;
import com.papeleria.pedidos.model.Pedido.EstadoPedido;
import com.papeleria.pedidos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service @RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepo;
    private final InventarioClient inventarioClient;

    public List<Pedido> listar() { return pedidoRepo.findAll(); }
    public Pedido buscarPorId(Long id) {
        return pedidoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Pedido no encontrado: "+id));
    }
    public List<Pedido> porCliente(Long clienteId) { return pedidoRepo.findByClienteId(clienteId); }
    public List<Pedido> porEstado(EstadoPedido estado) { return pedidoRepo.findByEstado(estado); }

    public Pedido crear(PedidoDTO dto) {
        List<DetallePedido> detalles = new ArrayList<>();
        double total = 0.0;
        Pedido pedido = Pedido.builder()
            .clienteId(dto.getClienteId())
            .estado(EstadoPedido.PENDIENTE)
            .fechaPedido(LocalDateTime.now())
            .observaciones(dto.getObservaciones())
            .total(0.0).build();
        pedido = pedidoRepo.save(pedido);

        for (DetallePedidoDTO d : dto.getDetalles()) {
            double subtotal = d.getCantidad() * d.getPrecioUnitario();
            total += subtotal;
            DetallePedido det = DetallePedido.builder()
                .pedido(pedido).productoId(d.getProductoId())
                .cantidad(d.getCantidad()).precioUnitario(d.getPrecioUnitario())
                .subtotal(subtotal).build();
            detalles.add(det);
            // Descontar del inventario
            try {
                Map<String,Object> inv = inventarioClient.obtenerInventario(d.getProductoId());
                Long invId = Long.valueOf(inv.get("id").toString());
                inventarioClient.registrarMovimiento(Map.of(
                    "inventarioId", invId, "tipo", "SALIDA",
                    "cantidad", d.getCantidad(), "motivo", "Pedido #"+pedido.getId(),
                    "referenciaId", pedido.getId()
                ));
            } catch (Exception e) {
                // Si inventario no responde, continuar (resilencia básica)
            }
        }
        pedido.setDetalles(detalles);
        pedido.setTotal(total);
        return pedidoRepo.save(pedido);
    }

    public Pedido cambiarEstado(Long id, EstadoPedido estado) {
        Pedido p = buscarPorId(id);
        p.setEstado(estado);
        if (estado == EstadoPedido.ENTREGADO) p.setFechaEntrega(LocalDateTime.now());
        return pedidoRepo.save(p);
    }
}
