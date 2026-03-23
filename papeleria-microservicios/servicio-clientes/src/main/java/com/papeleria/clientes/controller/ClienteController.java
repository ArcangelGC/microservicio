package com.papeleria.clientes.controller;
import com.papeleria.clientes.dto.*;
import com.papeleria.clientes.model.*;
import com.papeleria.clientes.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/clientes") @RequiredArgsConstructor
public class ClienteController {
    private final ClienteService svc;

    @GetMapping public ResponseEntity<List<Cliente>> listar() { return ResponseEntity.ok(svc.listarTodos()); }
    @GetMapping("/activos") public ResponseEntity<List<Cliente>> activos() { return ResponseEntity.ok(svc.listarActivos()); }
    @GetMapping("/{id}") public ResponseEntity<Cliente> porId(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }
    @GetMapping("/buscar") public ResponseEntity<List<Cliente>> buscar(@RequestParam String q) { return ResponseEntity.ok(svc.buscarPorNombre(q)); }
    @PostMapping public ResponseEntity<Cliente> crear(@Valid @RequestBody ClienteDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(svc.crear(dto)); }
    @PutMapping("/{id}") public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) { return ResponseEntity.ok(svc.actualizar(id, dto)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { svc.eliminar(id); return ResponseEntity.noContent().build(); }

    @PostMapping("/direcciones") public ResponseEntity<Direccion> agregarDir(@Valid @RequestBody DireccionDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(svc.agregarDireccion(dto)); }
    @GetMapping("/{id}/direcciones") public ResponseEntity<List<Direccion>> direcciones(@PathVariable Long id) { return ResponseEntity.ok(svc.direccionesPorCliente(id)); }

    @GetMapping("/health") public ResponseEntity<?> health() { return ResponseEntity.ok(java.util.Map.of("status","UP","servicio","clientes")); }
}
