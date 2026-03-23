package com.papeleria.pedidos.client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(name="servicio-inventario", url="${app.inventario-url}")
public interface InventarioClient {
    @GetMapping("/api/inventario/producto/{productoId}")
    Map<String,Object> obtenerInventario(@PathVariable Long productoId);

    @PostMapping("/api/inventario/movimiento")
    Map<String,Object> registrarMovimiento(@RequestBody Map<String,Object> dto);
}
