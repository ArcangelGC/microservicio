package com.papeleria.pedidos.service;

import com.papeleria.pedidos.model.Pedido;
import com.papeleria.pedidos.model.DetallePedido;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void testCrearPedido() {
        LocalDateTime fechaPedido = LocalDateTime.now();
        LocalDateTime fechaEntrega = fechaPedido.plusDays(3);

        Pedido pedido = Pedido.builder()
                .id(1L)
                .clienteId(100L)
                .estado(Pedido.EstadoPedido.CONFIRMADO)
                .fechaPedido(fechaPedido)
                .fechaEntrega(fechaEntrega)
                .total(250.75)
                .observaciones("Entrega en horario de oficina")
                .detalles(List.of())
                .build();

        assertNotNull(pedido);
        assertEquals(1L, pedido.getId());
        assertEquals(100L, pedido.getClienteId());
        assertEquals(Pedido.EstadoPedido.CONFIRMADO, pedido.getEstado());
        assertEquals(fechaPedido, pedido.getFechaPedido());
        assertEquals(fechaEntrega, pedido.getFechaEntrega());
        assertEquals(250.75, pedido.getTotal());
        assertEquals("Entrega en horario de oficina", pedido.getObservaciones());
        assertNotNull(pedido.getDetalles());
        assertTrue(pedido.getDetalles().isEmpty());
    }

    @Test
    void testPedidoConValoresMinimos() {
        LocalDateTime fechaPedido = LocalDateTime.now();

        Pedido pedido = Pedido.builder()
                .id(2L)
                .clienteId(200L)
                .estado(Pedido.EstadoPedido.PENDIENTE)
                .fechaPedido(fechaPedido)
                .total(99.99)
                .build();

        assertNotNull(pedido);
        assertEquals(2L, pedido.getId());
        assertEquals(200L, pedido.getClienteId());
        assertEquals(Pedido.EstadoPedido.PENDIENTE, pedido.getEstado());
        assertEquals(fechaPedido, pedido.getFechaPedido());
        assertNull(pedido.getFechaEntrega());
        assertEquals(99.99, pedido.getTotal());
        assertNull(pedido.getObservaciones());
        assertNull(pedido.getDetalles());
    }
}