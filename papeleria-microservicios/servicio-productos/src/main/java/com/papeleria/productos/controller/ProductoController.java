package com.papeleria.productos.controller;
import com.papeleria.productos.dto.*;
import com.papeleria.productos.model.*;
import com.papeleria.productos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/productos") @RequiredArgsConstructor
public class ProductoController {
    private final ProductoService svc;
    @GetMapping public ResponseEntity<List<Producto>> listar() { return ResponseEntity.ok(svc.listar()); }
    @GetMapping("/{id}") public ResponseEntity<Producto> porId(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }
    @GetMapping("/buscar") public ResponseEntity<List<Producto>> buscar(@RequestParam String nombre) { return ResponseEntity.ok(svc.buscarPorNombre(nombre)); }
    @GetMapping("/categoria/{catId}") public ResponseEntity<List<Producto>> porCategoria(@PathVariable Long catId) { return ResponseEntity.ok(svc.porCategoria(catId)); }
    @PostMapping public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(svc.crear(dto)); }
    @PutMapping("/{id}") public ResponseEntity<Producto> actualizar(@PathVariable Long id,@Valid @RequestBody ProductoDTO dto) { return ResponseEntity.ok(svc.actualizar(id,dto)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { svc.eliminar(id); return ResponseEntity.noContent().build(); }
    @GetMapping("/categorias") public ResponseEntity<List<Categoria>> categorias() { return ResponseEntity.ok(svc.listarCategorias()); }
    @PostMapping("/categorias") public ResponseEntity<Categoria> crearCat(@Valid @RequestBody CategoriaDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(svc.crearCategoria(dto)); }
    @GetMapping("/health") public ResponseEntity<?> health() { return ResponseEntity.ok(Map.of("status","UP","servicio","productos")); }
}
