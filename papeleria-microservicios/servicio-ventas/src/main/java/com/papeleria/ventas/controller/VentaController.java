package com.papeleria.ventas.controller;
import com.papeleria.ventas.dto.*;
import com.papeleria.ventas.model.*;
import com.papeleria.ventas.model.Venta.EstadoVenta;
import com.papeleria.ventas.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController @RequestMapping("/api/ventas") @RequiredArgsConstructor
public class VentaController {
    private final VentaService svc;

    @GetMapping
    public ResponseEntity<List<Venta>> listar() { return ResponseEntity.ok(svc.listar()); }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> porId(@PathVariable Long id) { return ResponseEntity.ok(svc.buscarPorId(id)); }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venta>> porCliente(@PathVariable Long clienteId) { return ResponseEntity.ok(svc.porCliente(clienteId)); }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Venta>> porEstado(@PathVariable EstadoVenta estado) { return ResponseEntity.ok(svc.porEstado(estado)); }

    @GetMapping("/rango")
    public ResponseEntity<List<Venta>> porFechas(
        @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
        @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(svc.porRangoFechas(desde, hasta));
    }

    @PostMapping
    public ResponseEntity<Venta> crear(@Valid @RequestBody VentaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(svc.crear(dto));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Venta> cambiarEstado(@PathVariable Long id, @RequestParam EstadoVenta estado) {
        return ResponseEntity.ok(svc.cambiarEstado(id, estado));
    }

    @PostMapping("/facturas")
    public ResponseEntity<Factura> emitirFactura(@Valid @RequestBody FacturaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(svc.emitirFactura(dto));
    }

    @GetMapping("/{id}/facturas")
    public ResponseEntity<List<Factura>> facturas(@PathVariable Long id) {
        return ResponseEntity.ok(svc.facturasPorVenta(id));
    }

    @GetMapping("/health")
    public ResponseEntity<?> health() { return ResponseEntity.ok(Map.of("status","UP","servicio","ventas")); }
}
