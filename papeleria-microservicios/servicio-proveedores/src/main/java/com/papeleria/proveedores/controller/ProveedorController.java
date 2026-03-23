package com.papeleria.proveedores.controller;
import com.papeleria.proveedores.dto.*;
import com.papeleria.proveedores.model.*;
import com.papeleria.proveedores.service.ProveedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/proveedores") @RequiredArgsConstructor
public class ProveedorController {
    private final ProveedorService svc;
    @GetMapping public ResponseEntity<List<Proveedor>> listar() { return ResponseEntity.ok(svc.listar()); }
    @GetMapping("/{id}") public ResponseEntity<Proveedor> porId(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }
    @PostMapping public ResponseEntity<Proveedor> crear(@Valid @RequestBody ProveedorDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(svc.crear(dto)); }
    @PutMapping("/{id}") public ResponseEntity<Proveedor> actualizar(@PathVariable Long id,@Valid @RequestBody ProveedorDTO dto) { return ResponseEntity.ok(svc.actualizar(id,dto)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { svc.eliminar(id); return ResponseEntity.noContent().build(); }
    @PostMapping("/compras") public ResponseEntity<Compra> compra(@Valid @RequestBody CompraDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(svc.registrarCompra(dto)); }
    @GetMapping("/{id}/compras") public ResponseEntity<List<Compra>> compras(@PathVariable Long id) { return ResponseEntity.ok(svc.comprasPorProveedor(id)); }
    @GetMapping("/health") public ResponseEntity<?> health() { return ResponseEntity.ok(Map.of("status","UP","servicio","proveedores")); }
}
