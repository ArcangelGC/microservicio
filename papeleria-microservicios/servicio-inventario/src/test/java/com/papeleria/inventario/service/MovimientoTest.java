package com.papeleria.inventario.service;

import com.papeleria.inventario.model.Inventario;
import com.papeleria.inventario.model.MovimientoStock;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class MovimientoTest {

    @Test
    void testCrearMovimientoStock() {
        // Creamos el inventario (necesario por la relación)
        Inventario inventario = Inventario.builder()
                .id(1L)
                .productoId(100L)
                .stockActual(10)
                .build();

        // Creamos el movimiento
        MovimientoStock movimiento = MovimientoStock.builder()
                .id(1L)
                .inventario(inventario)
                .tipo(MovimientoStock.TipoMovimiento.ENTRADA)
                .cantidad(5)
                .stockAnterior(10)
                .stockNuevo(15)
                .motivo("Reabastecimiento")
                .fechaMovimiento(LocalDateTime.now())
                .referenciaId(1001L)
                .build();

        // Validaciones
        assertNotNull(movimiento);
        assertEquals(1L, movimiento.getId());
        assertEquals(inventario, movimiento.getInventario());
        assertEquals(MovimientoStock.TipoMovimiento.ENTRADA, movimiento.getTipo());
        assertEquals(5, movimiento.getCantidad());
        assertEquals(10, movimiento.getStockAnterior());
        assertEquals(15, movimiento.getStockNuevo());
        assertEquals("Reabastecimiento", movimiento.getMotivo());
        assertNotNull(movimiento.getFechaMovimiento());
        assertEquals(1001L, movimiento.getReferenciaId());
    }

    @Test
    void testCrearMovimientoSalida() {
        // Creamos el inventario
        Inventario inventario = Inventario.builder()
                .id(2L)
                .productoId(200L)
                .stockActual(20)
                .build();

        // Creamos el movimiento de salida
        MovimientoStock movimiento = MovimientoStock.builder()
                .id(2L)
                .inventario(inventario)
                .tipo(MovimientoStock.TipoMovimiento.SALIDA)
                .cantidad(3)
                .stockAnterior(20)
                .stockNuevo(17)
                .motivo("Venta a cliente")
                .fechaMovimiento(LocalDateTime.now())
                .build();

        // Validaciones
        assertNotNull(movimiento);
        assertEquals(MovimientoStock.TipoMovimiento.SALIDA, movimiento.getTipo());
        assertEquals(3, movimiento.getCantidad());
        assertEquals(20, movimiento.getStockAnterior());
        assertEquals(17, movimiento.getStockNuevo());
        assertEquals("Venta a cliente", movimiento.getMotivo());
    }
}