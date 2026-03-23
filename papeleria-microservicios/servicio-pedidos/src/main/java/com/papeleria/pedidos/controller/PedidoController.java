package com.papeleria.pedidos.controller;
import com.papeleria.pedidos.dto.PedidoDTO;
import com.papeleria.pedidos.model.Pedido;
import com.papeleria.pedidos.model.Pedido.EstadoPedido;
import com.papeleria.pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/pedidos") @RequiredArgsConstructor
public class PedidoController {
    private final PedidoService svc;
    @GetMapping public ResponseEntity<List<Pedido>> listar() { return ResponseEntity.ok(svc.listar()); }
    @GetMapping("/{id}") public ResponseEntity<Pedido> porId(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }
    @GetMapping("/cliente/{clienteId}") public ResponseEntity<List<Pedido>> porCliente(@PathVariable Long clienteId) { return ResponseEntity.ok(svc.porCliente(clienteId)); }
    @GetMapping("/estado/{estado}") public ResponseEntity<List<Pedido>> porEstado(@PathVariable EstadoPedido estado) { return ResponseEntity.ok(svc.porEstado(estado)); }
    @PostMapping public ResponseEntity<Pedido> crear(@Valid @RequestBody PedidoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(svc.crear(dto)); }
    @PatchMapping("/{id}/estado") public ResponseEntity<Pedido> estado(@PathVariable Long id,@RequestParam EstadoPedido estado) { return ResponseEntity.ok(svc.cambiarEstado(id,estado)); }
    @GetMapping("/health") public ResponseEntity<?> health() { return ResponseEntity.ok(Map.of("status","UP","servicio","pedidos")); }
}
