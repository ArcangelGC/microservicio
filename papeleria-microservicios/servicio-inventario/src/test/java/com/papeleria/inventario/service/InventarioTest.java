package com.papeleria.inventario.service;

import com.papeleria.inventario.model.Inventario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InventarioTest {

    @Test
    void testCrearInventario() {
        Inventario inventario = Inventario.builder()
                .id(1L)
                .productoId(100L)
                .stockActual(10)
                .stockMinimo(5)
                .stockMaximo(50)
                .ubicacionAlmacen("A-01")
                .build();

        assertNotNull(inventario);
        assertEquals(1L, inventario.getId());
        assertEquals(100L, inventario.getProductoId());
        assertEquals(10, inventario.getStockActual());
        assertEquals(5, inventario.getStockMinimo());
        assertEquals(50, inventario.getStockMaximo());
        assertEquals("A-01", inventario.getUbicacionAlmacen());
    }

    @Test
    void testInventarioConValoresMinimos() {
        Inventario inventario = Inventario.builder()
                .id(2L)
                .productoId(200L)
                .stockActual(0)
                .stockMinimo(5)
                .build();

        assertNotNull(inventario);
        assertEquals(2L, inventario.getId());
        assertEquals(200L, inventario.getProductoId());
        assertEquals(0, inventario.getStockActual());
        assertEquals(5, inventario.getStockMinimo());
        assertNull(inventario.getStockMaximo());
        assertNull(inventario.getUbicacionAlmacen());
    }
}