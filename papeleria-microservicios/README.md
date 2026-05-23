# Papelería Microservicios

Proyecto de microservicios para la administración de una papelería. Está desarrollado con **Java 17**, **Spring Boot 3.2.3**, **Spring Cloud 2023.0.0**, **Maven**, **MySQL 8** y **Docker Compose**.

El sistema está dividido en servicios independientes, cada uno con su propia base de datos MySQL. El acceso centralizado a las APIs se realiza mediante un **API Gateway**.

## Arquitectura general

```text
Cliente / Postman / Frontend
          |
          v
    API Gateway
          |
          +--> servicio-clientes      -> bd_clientes
          +--> servicio-productos     -> bd_productos
          +--> servicio-inventario    -> bd_inventario
          +--> servicio-pedidos       -> bd_pedidos
          +--> servicio-proveedores   -> bd_proveedores
          +--> servicio-ventas        -> bd_ventas
```

## Servicios

| Servicio | Puerto | Base de datos | Responsabilidad |
| --- | ---: | --- | --- |
| api-gateway | 8090 en `application.yml` | No aplica | Enrutamiento hacia los microservicios |
| servicio-clientes | 8081 | bd_clientes | Clientes y direcciones |
| servicio-productos | 8082 | bd_productos | Productos y categorías |
| servicio-inventario | 8083 | bd_inventario | Existencias y movimientos de stock |
| servicio-pedidos | 8084 | bd_pedidos | Pedidos y detalle de pedidos |
| servicio-proveedores | 8085 | bd_proveedores | Proveedores, compras y detalle de compras |
| servicio-ventas | 8086 | bd_ventas | Ventas y facturas |

> Nota: en `docker-compose.yml` el gateway está publicado como `8080:8080`, pero en `api-gateway/src/main/resources/application.yml` el puerto configurado es `8090`. Para Docker, estos valores deberían alinearse antes de levantar el gateway.

## Estructura del proyecto

```text
papeleria-microservicios/
├── README.md
├── .gitignore
├── database.sql
├── docker-compose.yml
├── api-gateway/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/papeleria/gateway/
│           │       └── ApiGatewayApplication.java
│           └── resources/
│               └── application.yml
├── servicio-clientes/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/papeleria/clientes/
│       │   │       ├── ClientesApplication.java
│       │   │       ├── config/
│       │   │       │   └── CorsConfig.java
│       │   │       ├── controller/
│       │   │       │   └── ClienteController.java
│       │   │       ├── dto/
│       │   │       │   ├── ClienteDTO.java
│       │   │       │   └── DireccionDTO.java
│       │   │       ├── exception/
│       │   │       │   ├── GlobalExceptionHandler.java
│       │   │       │   └── ResourceNotFoundException.java
│       │   │       ├── model/
│       │   │       │   ├── Cliente.java
│       │   │       │   └── Direccion.java
│       │   │       ├── repository/
│       │   │       │   ├── ClienteRepository.java
│       │   │       │   └── DireccionRepository.java
│       │   │       └── service/
│       │   │           └── ClienteService.java
│       │   └── resources/
│       │       └── application.properties
│       └── test/
│           └── java/com/papeleria/clientes/service/
│               ├── ClienteServiceTest.java
│               └── DireccionTest.java
├── servicio-productos/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/
│       │   │   └── com/papeleria/productos/
│       │   │       ├── ProductosApplication.java
│       │   │       ├── config/
│       │   │       │   └── CorsConfig.java
│       │   │       ├── controller/
│       │   │       │   └── ProductoController.java
│       │   │       ├── dto/
│       │   │       │   ├── CategoriaDTO.java
│       │   │       │   └── ProductoDTO.java
│       │   │       ├── exception/
│       │   │       │   ├── GlobalExceptionHandler.java
│       │   │       │   └── ResourceNotFoundException.java
│       │   │       ├── model/
│       │   │       │   ├── Categoria.java
│       │   │       │   └── Producto.java
│       │   │       ├── repository/
│       │   │       │   ├── CategoriaRepository.java
│       │   │       │   └── ProductoRepository.java
│       │   │       └── service/
│       │   │           └── ProductoService.java
│       │   └── resources/
│       │       └── application.properties
│       └── test/
│           └── java/com/papeleria/productos/service/
│               ├── CategoriaTest.java
│               └── ProductoServiceTest.java
├── servicio-inventario/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/papeleria/inventario/
│           │       ├── InventarioApplication.java
│           │       ├── config/
│           │       │   └── CorsConfig.java
│           │       ├── controller/
│           │       │   └── InventarioController.java
│           │       ├── dto/
│           │       │   ├── InventarioDTO.java
│           │       │   └── MovimientoDTO.java
│           │       ├── exception/
│           │       │   ├── GlobalExceptionHandler.java
│           │       │   └── ResourceNotFoundException.java
│           │       ├── model/
│           │       │   ├── Inventario.java
│           │       │   └── MovimientoStock.java
│           │       ├── repository/
│           │       │   ├── InventarioRepository.java
│           │       │   └── MovimientoRepository.java
│           │       └── service/
│           │           └── InventarioService.java
│           └── resources/
│               └── application.properties
├── servicio-pedidos/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/papeleria/pedidos/
│           │       ├── PedidosApplication.java
│           │       ├── client/
│           │       │   └── InventarioClient.java
│           │       ├── config/
│           │       │   └── CorsConfig.java
│           │       ├── controller/
│           │       │   └── PedidoController.java
│           │       ├── dto/
│           │       │   ├── DetallePedidoDTO.java
│           │       │   └── PedidoDTO.java
│           │       ├── exception/
│           │       │   ├── GlobalExceptionHandler.java
│           │       │   └── ResourceNotFoundException.java
│           │       ├── model/
│           │       │   ├── DetallePedido.java
│           │       │   └── Pedido.java
│           │       ├── repository/
│           │       │   ├── DetallePedidoRepository.java
│           │       │   └── PedidoRepository.java
│           │       └── service/
│           │           └── PedidoService.java
│           └── resources/
│               └── application.properties
├── servicio-proveedores/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/papeleria/proveedores/
│           │       ├── ProveedoresApplication.java
│           │       ├── config/
│           │       │   └── CorsConfig.java
│           │       ├── controller/
│           │       │   └── ProveedorController.java
│           │       ├── dto/
│           │       │   ├── CompraDTO.java
│           │       │   ├── DetalleCompraDTO.java
│           │       │   └── ProveedorDTO.java
│           │       ├── exception/
│           │       │   ├── GlobalExceptionHandler.java
│           │       │   └── ResourceNotFoundException.java
│           │       ├── model/
│           │       │   ├── Compra.java
│           │       │   ├── DetalleCompra.java
│           │       │   └── Proveedor.java
│           │       ├── repository/
│           │       │   ├── CompraRepository.java
│           │       │   └── ProveedorRepository.java
│           │       └── service/
│           │           └── ProveedorService.java
│           └── resources/
│               └── application.properties
└── servicio-ventas/
    ├── Dockerfile
    ├── pom.xml
    └── src/
        └── main/
            ├── java/
            │   └── com/papeleria/ventas/
            │       ├── VentasApplication.java
            │       ├── config/
            │       │   └── CorsConfig.java
            │       ├── controller/
            │       │   └── VentaController.java
            │       ├── dto/
            │       │   ├── FacturaDTO.java
            │       │   └── VentaDTO.java
            │       ├── exception/
            │       │   ├── GlobalExceptionHandler.java
            │       │   └── ResourceNotFoundException.java
            │       ├── model/
            │       │   ├── Factura.java
            │       │   └── Venta.java
            │       ├── repository/
            │       │   ├── FacturaRepository.java
            │       │   └── VentaRepository.java
            │       └── service/
            │           └── VentaService.java
            └── resources/
                └── application.properties
```

Las carpetas `target/`, `.idea/`, `.git/` y `node_modules/` no forman parte de la estructura fuente del microservicio y no se incluyen como parte funcional del proyecto.

## Patrón interno de cada microservicio

Cada servicio de negocio sigue una estructura similar:

| Carpeta | Función |
| --- | --- |
| `config/` | Configuración transversal, como CORS |
| `controller/` | Endpoints REST expuestos por el servicio |
| `dto/` | Objetos de transferencia de datos |
| `exception/` | Manejo de errores y excepciones personalizadas |
| `model/` | Entidades JPA persistidas en MySQL |
| `repository/` | Interfaces Spring Data JPA |
| `service/` | Lógica de negocio |
| `resources/` | Configuración del servicio |

## Bases de datos

El archivo `database.sql` crea las bases y tablas necesarias para ejecución local sin Docker.

| Base de datos | Tablas principales |
| --- | --- |
| `bd_clientes` | `clientes`, `direcciones` |
| `bd_productos` | `productos`, `categorias` |
| `bd_inventario` | `inventario`, `movimientos_stock` |
| `bd_pedidos` | `pedidos`, `detalle_pedidos` |
| `bd_proveedores` | `proveedores`, `compras`, `detalle_compras` |
| `bd_ventas` | `ventas`, `facturas` |

## Requisitos

- Java 17
- Maven
- Docker Desktop, si se ejecuta con contenedores
- MySQL 8, si se ejecuta localmente sin Docker
- Postman, Insomnia o cliente HTTP equivalente para probar APIs

## Ejecución con Docker Compose

Desde la carpeta raíz del proyecto:

```bash
cd papeleria-microservicios
docker-compose up --build
```

Para detener los contenedores:

```bash
docker-compose down
```

Para detener y eliminar volúmenes de datos:

```bash
docker-compose down -v
```

## Ejecución local sin Docker

1. Crear las bases de datos ejecutando `database.sql` en MySQL.
2. Verificar que MySQL esté disponible en `127.0.0.1:3306`.
3. Configurar usuario y contraseña en cada archivo `application.properties`.
4. Ejecutar cada servicio Maven desde su carpeta:

```bash
mvn spring-boot:run
```

Orden recomendado de arranque:

```text
1. servicio-clientes
2. servicio-productos
3. servicio-inventario
4. servicio-proveedores
5. servicio-pedidos
6. servicio-ventas
7. api-gateway
```

## Endpoints principales

### Clientes

Base: `/api/clientes`

| Método | Ruta | Descripción |
| --- | --- | --- |
| GET | `/api/clientes` | Lista clientes |
| GET | `/api/clientes/activos` | Lista clientes activos |
| GET | `/api/clientes/{id}` | Busca un cliente por ID |
| GET | `/api/clientes/buscar?q=ana` | Busca clientes por nombre |
| POST | `/api/clientes` | Crea un cliente |
| PUT | `/api/clientes/{id}` | Actualiza un cliente |
| DELETE | `/api/clientes/{id}` | Elimina o desactiva un cliente |
| POST | `/api/clientes/direcciones` | Agrega una dirección |
| GET | `/api/clientes/{id}/direcciones` | Lista direcciones del cliente |

### Productos

Base: `/api/productos`

| Método | Ruta | Descripción |
| --- | --- | --- |
| GET | `/api/productos` | Lista productos activos |
| GET | `/api/productos/{id}` | Busca un producto por ID |
| GET | `/api/productos/buscar?nombre=lapiz` | Busca productos por nombre |
| GET | `/api/productos/categoria/{catId}` | Filtra por categoría |
| GET | `/api/productos/categorias` | Lista categorías |
| POST | `/api/productos/categorias` | Crea una categoría |
| POST | `/api/productos` | Crea un producto |
| PUT | `/api/productos/{id}` | Actualiza un producto |
| DELETE | `/api/productos/{id}` | Desactiva un producto |

### Inventario

Base: `/api/inventario`

| Método | Ruta | Descripción |
| --- | --- | --- |
| GET | `/api/inventario` | Lista inventario |
| GET | `/api/inventario/{id}` | Busca inventario por ID |
| GET | `/api/inventario/producto/{pid}` | Busca inventario por producto |
| GET | `/api/inventario/stock-bajo` | Lista productos con stock bajo |
| POST | `/api/inventario` | Registra inventario de producto |
| POST | `/api/inventario/movimiento` | Registra entrada, salida o ajuste |
| GET | `/api/inventario/{id}/historial` | Lista movimientos del inventario |

### Pedidos

Base: `/api/pedidos`

| Método | Ruta | Descripción |
| --- | --- | --- |
| GET | `/api/pedidos` | Lista pedidos |
| GET | `/api/pedidos/{id}` | Busca un pedido por ID |
| GET | `/api/pedidos/cliente/{clienteId}` | Lista pedidos por cliente |
| GET | `/api/pedidos/estado/PENDIENTE` | Lista pedidos por estado |
| POST | `/api/pedidos` | Crea un pedido |
| PATCH | `/api/pedidos/{id}/estado?estado=CONFIRMADO` | Cambia el estado del pedido |

### Proveedores

Base: `/api/proveedores`

| Método | Ruta | Descripción |
| --- | --- | --- |
| GET | `/api/proveedores` | Lista proveedores activos |
| GET | `/api/proveedores/{id}` | Busca proveedor por ID |
| POST | `/api/proveedores` | Crea proveedor |
| PUT | `/api/proveedores/{id}` | Actualiza proveedor |
| DELETE | `/api/proveedores/{id}` | Desactiva proveedor |
| POST | `/api/proveedores/compras` | Registra compra |
| GET | `/api/proveedores/{id}/compras` | Lista compras del proveedor |

### Ventas

Base: `/api/ventas`

| Método | Ruta | Descripción |
| --- | --- | --- |
| GET | `/api/ventas` | Lista ventas |
| GET | `/api/ventas/{id}` | Busca venta por ID |
| GET | `/api/ventas/cliente/{clienteId}` | Lista ventas por cliente |
| GET | `/api/ventas/estado/PAGADA` | Lista ventas por estado |
| POST | `/api/ventas` | Crea venta |
| PATCH | `/api/ventas/{id}/estado?estado=PAGADA` | Cambia estado de venta |
| POST | `/api/ventas/facturas` | Emite factura |
| GET | `/api/ventas/{id}/facturas` | Lista facturas de una venta |

## Health checks

Endpoints esperados para verificar disponibilidad:

```text
GET /api/clientes/health
GET /api/productos/health
GET /api/inventario/health
GET /api/pedidos/health
GET /api/proveedores/health
GET /api/ventas/health
```

## Comunicación entre servicios

- `servicio-pedidos` usa `InventarioClient` para comunicarse con `servicio-inventario`.
- Al crear pedidos, el sistema puede consultar inventario y registrar movimientos de salida.
- `docker-compose.yml` define variables como `APP_INVENTARIO_URL`, `APP_CLIENTES_URL` y `APP_PEDIDOS_URL` para comunicación interna entre contenedores.

## Archivos principales

| Archivo | Descripción |
| --- | --- |
| `docker-compose.yml` | Define contenedores de MySQL, microservicios, gateway y volúmenes |
| `database.sql` | Script de creación de bases, tablas y datos iniciales |
| `*/pom.xml` | Configuración Maven de cada servicio |
| `*/Dockerfile` | Imagen Docker de cada servicio |
| `*/src/main/resources/application.properties` | Configuración local de cada microservicio |
| `api-gateway/src/main/resources/application.yml` | Rutas del gateway |

## link de REPOSITORIO: https://github.com/ArcangelGC/microservicio
