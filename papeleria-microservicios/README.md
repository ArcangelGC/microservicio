# Papelería Microservicios
Sistema completo de microservicios para una papelería desarrollado con **Java 17 + Spring Boot 3 + MySQL**.

---

## Arquitectura

```
                        ┌─────────────────┐
        Postman ──────► │   API Gateway   │  :8080
                        │   (enrutador)   │
                        └────────┬────────┘
               ┌─────────────────┼─────────────────┐
               │         ┌───────┤       ┌──────────┤
               ▼         ▼       ▼       ▼          ▼
          Clientes  Productos Inventario Pedidos  Proveedores  Ventas
           :8081     :8082     :8083     :8084     :8085       :8086
             │         │         │         │         │           │
           bd_cli   bd_prod   bd_inv    bd_ped    bd_prov     bd_ven
```

| Servicio      | Puerto | Base de datos   | Tablas                         |
|---------------|--------|-----------------|--------------------------------|
| API Gateway   | 8080   | —               | Solo enruta peticiones         |
| Clientes      | 8081   | bd_clientes     | clientes, direcciones          |
| Productos     | 8082   | bd_productos    | productos, categorias          |
| Inventario    | 8083   | bd_inventario   | inventario, movimientos_stock  |
| Pedidos       | 8084   | bd_pedidos      | pedidos, detalle_pedidos       |
| Proveedores   | 8085   | bd_proveedores  | proveedores, compras           |
| Ventas        | 8086   | bd_ventas       | ventas, facturas               |

---

## Opción A — Levantar con Docker (recomendado)

### Requisitos
- Docker Desktop instalado y corriendo

### Pasos
```bash
# 1. Entrar a la carpeta del proyecto
cd papeleria-microservicios

# 2. Levantar todo con un solo comando
docker-compose up --build

# 3. Esperar a que todos los servicios inicien (~2-3 minutos)
# 4. Verificar en Postman: GET http://localhost:8080/api/clientes/health
```

Para detener todo:
```bash
docker-compose down
```

---

## Opción B — Ejecutar en IntelliJ (sin Docker)

### Requisitos
- Java 17
- MySQL corriendo en localhost:3306
- IntelliJ IDEA

### Paso 1 — Crear las bases de datos
Abre MySQL Workbench y ejecuta el archivo `database.sql` completo.

### Paso 2 — Abrir los proyectos en IntelliJ
Cada servicio es un proyecto Maven independiente. Tienes dos opciones:

**Opción rápida — abrir como módulos:**
1. `File → New → Project from Existing Sources`
2. Selecciona la carpeta raíz `papeleria-microservicios`
3. IntelliJ detectará los 7 `pom.xml`

**Opción individual — abrir uno por uno:**
1. `File → Open` → selecciona la carpeta de cada servicio
2. Confirma "Load Maven Project"

### Paso 3 — Configurar contraseña MySQL
En cada servicio edita `src/main/resources/application.properties`:
```properties
spring.datasource.password=TU_PASSWORD_AQUI
```

### Paso 4 — Orden de arranque
Es importante arrancar en este orden para que las dependencias funcionen:

```
1. servicio-clientes    (no depende de nadie)
2. servicio-productos   (no depende de nadie)
3. servicio-inventario  (no depende de nadie)
4. servicio-proveedores (no depende de nadie)
5. servicio-pedidos     (llama a inventario)
6. servicio-ventas      (llama a pedidos)
7. api-gateway          (enruta a todos)
```

En IntelliJ: clic derecho sobre `*Application.java` → **Run**.

---

## Endpoints completos — Postman

### URL BASE (con Gateway): `http://localhost:8080`
### URL DIRECTA (sin Gateway): `http://localhost:808X`

---

### CLIENTES  `/api/clientes`

| Método | URL                          | Descripción              |
|--------|------------------------------|--------------------------|
| GET    | /api/clientes                | Listar todos             |
| GET    | /api/clientes/activos        | Solo activos             |
| GET    | /api/clientes/{id}           | Buscar por ID            |
| GET    | /api/clientes/buscar?q=ana   | Buscar por nombre        |
| POST   | /api/clientes                | Crear cliente            |
| PUT    | /api/clientes/{id}           | Actualizar               |
| DELETE | /api/clientes/{id}           | Eliminar                 |
| POST   | /api/clientes/direcciones    | Agregar dirección        |
| GET    | /api/clientes/{id}/direcciones | Ver direcciones        |

**Ejemplo POST /api/clientes:**
```json
{
  "nombre": "Pedro",
  "apellido": "Ramírez",
  "email": "pedro.ramirez@email.com",
  "telefono": "5551234567"
}
```

---

### PRODUCTOS  `/api/productos`

| Método | URL                               | Descripción          |
|--------|-----------------------------------|----------------------|
| GET    | /api/productos                    | Listar activos       |
| GET    | /api/productos/{id}               | Buscar por ID        |
| GET    | /api/productos/buscar?nombre=lapiz| Buscar por nombre    |
| GET    | /api/productos/categoria/{catId}  | Filtrar categoría    |
| GET    | /api/productos/categorias         | Listar categorías    |
| POST   | /api/productos/categorias         | Crear categoría      |
| POST   | /api/productos                    | Crear producto       |
| PUT    | /api/productos/{id}               | Actualizar           |
| DELETE | /api/productos/{id}               | Desactivar           |

**Ejemplo POST /api/productos:**
```json
{
  "nombre": "Crayones 12 colores",
  "descripcion": "Caja de crayones 12 colores jumbo",
  "precio": 35.00,
  "codigoBarras": "CRA001",
  "unidadMedida": "caja",
  "categoriaId": 4
}
```

---

### INVENTARIO  `/api/inventario`

| Método | URL                              | Descripción             |
|--------|----------------------------------|-------------------------|
| GET    | /api/inventario                  | Listar todo             |
| GET    | /api/inventario/{id}             | Por ID                  |
| GET    | /api/inventario/producto/{pid}   | Por producto            |
| GET    | /api/inventario/stock-bajo       | Alertas stock mínimo    |
| POST   | /api/inventario                  | Registrar producto      |
| POST   | /api/inventario/movimiento       | Entrada / Salida        |
| GET    | /api/inventario/{id}/historial   | Historial movimientos   |

**Ejemplo POST /api/inventario/movimiento (entrada de mercancía):**
```json
{
  "inventarioId": 1,
  "tipo": "ENTRADA",
  "cantidad": 50,
  "motivo": "Compra proveedor #3"
}
```

---

### PEDIDOS  `/api/pedidos`

| Método | URL                              | Descripción          |
|--------|----------------------------------|----------------------|
| GET    | /api/pedidos                     | Listar todos         |
| GET    | /api/pedidos/{id}                | Por ID               |
| GET    | /api/pedidos/cliente/{clienteId} | Por cliente          |
| GET    | /api/pedidos/estado/PENDIENTE    | Por estado           |
| POST   | /api/pedidos                     | Crear pedido         |
| PATCH  | /api/pedidos/{id}/estado?estado=CONFIRMADO | Cambiar estado |

**Ejemplo POST /api/pedidos:**
```json
{
  "clienteId": 1,
  "observaciones": "Entregar en oficina",
  "detalles": [
    { "productoId": 1, "cantidad": 5,  "precioUnitario": 5.50 },
    { "productoId": 5, "cantidad": 2,  "precioUnitario": 25.00 }
  ]
}
```

---

### PROVEEDORES  `/api/proveedores`

| Método | URL                           | Descripción        |
|--------|-------------------------------|--------------------|
| GET    | /api/proveedores              | Listar activos     |
| GET    | /api/proveedores/{id}         | Por ID             |
| POST   | /api/proveedores              | Crear              |
| PUT    | /api/proveedores/{id}         | Actualizar         |
| DELETE | /api/proveedores/{id}         | Desactivar         |
| POST   | /api/proveedores/compras      | Registrar compra   |
| GET    | /api/proveedores/{id}/compras | Ver compras        |

**Ejemplo POST /api/proveedores/compras:**
```json
{
  "proveedorId": 1,
  "numeroFactura": "FAC-2024-001",
  "detalles": [
    { "productoId": 1, "cantidad": 200, "precioUnitario": 3.50 },
    { "productoId": 2, "cantidad": 150, "precioUnitario": 5.00 }
  ]
}
```

---

### VENTAS  `/api/ventas`

| Método | URL                             | Descripción        |
|--------|---------------------------------|--------------------|
| GET    | /api/ventas                     | Listar todas       |
| GET    | /api/ventas/{id}                | Por ID             |
| GET    | /api/ventas/cliente/{clienteId} | Por cliente        |
| GET    | /api/ventas/estado/PAGADA       | Por estado         |
| POST   | /api/ventas                     | Crear venta        |
| PATCH  | /api/ventas/{id}/estado?estado=PAGADA | Cambiar estado |
| POST   | /api/ventas/facturas            | Emitir factura     |
| GET    | /api/ventas/{id}/facturas       | Ver facturas       |

**Ejemplo POST /api/ventas:**
```json
{
  "clienteId": 1,
  "pedidoId": 1,
  "subtotal": 77.50,
  "metodoPago": "EFECTIVO"
}
```

**Ejemplo POST /api/ventas/facturas (la venta debe estar PAGADA):**
```json
{
  "ventaId": 1,
  "rfcCliente": "GARA900101XXX",
  "razonSocial": "Ana García"
}
```

---

## Health checks

Verifica que todos los servicios están vivos:
```
GET http://localhost:8080/api/clientes/health
GET http://localhost:8080/api/productos/health
GET http://localhost:8080/api/inventario/health
GET http://localhost:8080/api/pedidos/health
GET http://localhost:8080/api/proveedores/health
GET http://localhost:8080/api/ventas/health
```

Respuesta esperada:
```json
{ "status": "UP", "servicio": "clientes" }
```

---

## Comunicación entre servicios

Cuando se crea un **pedido**, el servicio de pedidos llama automáticamente al servicio de inventario para descontar el stock de cada producto. Esta llamada se hace vía **Feign Client** (HTTP/REST).

```
POST /api/pedidos
       │
       ├─► servicio-inventario: GET /api/inventario/producto/{id}
       └─► servicio-inventario: POST /api/inventario/movimiento (SALIDA)
```

Si el servicio de inventario no responde, el pedido se crea de igual forma (resiliencia básica).
