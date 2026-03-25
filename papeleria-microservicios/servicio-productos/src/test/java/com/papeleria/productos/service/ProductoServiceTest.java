package com.papeleria.productos.service;
import com.papeleria.productos.model.Producto;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void testCrearProducto() {

        Producto producto = Producto.builder()
                .id(1L)
                .nombre("Cuaderno")
                .descripcion("Cuaderno profesional")
                .precio(45.5)
                .codigoBarras("123456789")
                .unidadMedida("pieza")
                .activo(true)
                .build();

        assertNotNull(producto);
        assertEquals("Cuaderno", producto.getNombre());
        assertEquals(45.5, producto.getPrecio());
        assertEquals("123456789", producto.getCodigoBarras());
        assertTrue(producto.getActivo());
    }
}