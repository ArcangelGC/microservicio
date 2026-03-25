package com.papeleria.clientes.service;
import com.papeleria.clientes.model.Cliente;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testCrearCliente() {
        Cliente cliente = Cliente.builder()
                .id(1L)
                .nombre("Kimberly")
                .apellido("Mendoza")
                .email("kim@gmail.com")
                .telefono("9531234567")
                .fechaRegistro(LocalDate.now())
                .activo(true)
                .build();

        assertNotNull(cliente);
        assertEquals("Kimberly", cliente.getNombre());
        assertEquals("Mendoza", cliente.getApellido());
        assertEquals("kim@gmail.com", cliente.getEmail());
        assertTrue(cliente.getActivo());
    }
}