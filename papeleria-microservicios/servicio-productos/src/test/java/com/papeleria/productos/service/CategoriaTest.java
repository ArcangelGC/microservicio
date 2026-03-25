package com.papeleria.productos.service;
import com.papeleria.productos.model.Categoria;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testCategoriaValida() {
        Categoria categoria = Categoria.builder()
                .nombre("Papelería")
                .descripcion("Productos de oficina")
                .build();

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);

        assertTrue(violations.isEmpty(), "No debería haber errores de validación");
    }

    @Test
    void testNombreVacio() {
        Categoria categoria = Categoria.builder()
                .nombre("")
                .descripcion("Descripción")
                .build();

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);

        assertFalse(violations.isEmpty(), "Debe fallar por nombre vacío");
    }

    @Test
    void testNombreNull() {
        Categoria categoria = Categoria.builder()
                .nombre(null)
                .descripcion("Descripción")
                .build();

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);

        assertFalse(violations.isEmpty(), "Debe fallar por nombre null");
    }

    @Test
    void testBuilderYLombok() {
        Categoria categoria = Categoria.builder()
                .id(1L)
                .nombre("Escolar")
                .descripcion("Útiles escolares")
                .build();

        assertEquals(1L, categoria.getId());
        assertEquals("Escolar", categoria.getNombre());
        assertEquals("Útiles escolares", categoria.getDescripcion());
    }

    @Test
    void testEqualsAndHashCode() {
        Categoria c1 = Categoria.builder()
                .id(1L)
                .nombre("Arte")
                .build();

        Categoria c2 = Categoria.builder()
                .id(1L)
                .nombre("Arte")
                .build();

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}