package com.papeleria.clientes.service;
import com.papeleria.clientes.model.Direccion;
import com.papeleria.clientes.model.Cliente;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DireccionTest {

    @Test
    void testCrearDireccion() {

        // Creamos un cliente (necesario por la relación)
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Kimberly")
                .apellido("Mendoza")
                .email("kim@gmail.com")
                .activo(true)
                .build();

        // Creamos la dirección
        Direccion direccion = Direccion.builder()
                .id(1L)
                .calle("Av. Principal 123")
                .ciudad("Huajuapan")
                .estado("Oaxaca")
                .codigoPostal("69000")
                .esPrincipal(true)
                .cliente(cliente)
                .build();

        // Validaciones
        assertNotNull(direccion);
        assertEquals("Av. Principal 123", direccion.getCalle());
        assertEquals("Huajuapan", direccion.getCiudad());
        assertEquals("Oaxaca", direccion.getEstado());
        assertEquals("69000", direccion.getCodigoPostal());
        assertTrue(direccion.getEsPrincipal());
        assertEquals(cliente, direccion.getCliente());
    }
}