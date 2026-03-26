package com.papeleria.pedidos.service;

import com.papeleria.pedidos.model.DetallePedido;
import com.papeleria.pedidos.model.Pedido;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DetallePedidoTest {

    @Test
    void testCrearDetallePedido() {
        Pedido pedido = Pedido.builder()
                .id(1L)
                .build();

        DetallePedido detalle = DetallePedido.builder()
                .id(1L)
                .pedido(pedido)
                .productoId(100L)
                .cantidad(5)
                .precioUnitario(15.50)
                .subtotal(77.50)
                .build();

        assertNotNull(detalle);
        assertEquals(1L, detalle.getId());
        assertNotNull(detalle.getPedido());
        assertEquals(1L, detalle.getPedido().getId());
        assertEquals(100L, detalle.getProductoId());
        assertEquals(5, detalle.getCantidad());
        assertEquals(15.50, detalle.getPrecioUnitario());
        assertEquals(77.50, detalle.getSubtotal());
    }

    @Test
    void testDetallePedidoConValoresMinimos() {
        // Se crea un objeto Pedido mínimo (solo id)
        Pedido pedido = Pedido.builder()
                .id(2L)
                .build();

        DetallePedido detalle = DetallePedido.builder()
                .id(2L)
                .pedido(pedido)
                .productoId(200L)
                .cantidad(1)
                .precioUnitario(10.0)
                .subtotal(10.0)
                .build();

        assertNotNull(detalle);
        assertEquals(2L, detalle.getId());
        assertNotNull(detalle.getPedido());
        assertEquals(2L, detalle.getPedido().getId());
        assertEquals(200L, detalle.getProductoId());
        assertEquals(1, detalle.getCantidad());
        assertEquals(10.0, detalle.getPrecioUnitario());
        assertEquals(10.0, detalle.getSubtotal());
    }
}