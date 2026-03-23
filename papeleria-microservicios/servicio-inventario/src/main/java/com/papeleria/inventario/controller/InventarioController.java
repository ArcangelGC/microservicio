package com.papeleria.inventario.controller;
import com.papeleria.inventario.dto.*;
import com.papeleria.inventario.model.*;
import com.papeleria.inventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/inventario") @RequiredArgsConstructor
public class InventarioController {
    private final InventarioService svc;
    @GetMapping public ResponseEntity<List<Inventario>> listar() { return ResponseEntity.ok(svc.listar()); }
    @GetMapping("/{id}") public ResponseEntity<Inventario> porId(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }
    @GetMapping("/producto/{productoId}") public ResponseEntity<Inventario> porProducto(@PathVariable Long productoId) { return ResponseEntity.ok(svc.buscarPorProducto(productoId)); }
    @GetMapping("/stock-bajo") public ResponseEntity<List<Inventario>> stockBajo() { return ResponseEntity.ok(svc.stockBajo()); }
    @PostMapping public ResponseEntity<Inventario> crear(@Valid @RequestBody InventarioDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(svc.crear(dto)); }
    @PostMapping("/movimiento") public ResponseEntity<MovimientoStock> movimiento(@Valid @RequestBody MovimientoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(svc.registrarMovimiento(dto)); }
    @GetMapping("/{id}/historial") public ResponseEntity<List<MovimientoStock>> historial(@PathVariable Long id) { return ResponseEntity.ok(svc.historial(id)); }
    @GetMapping("/health") public ResponseEntity<?> health() { return ResponseEntity.ok(Map.of("status","UP","servicio","inventario")); }
}
