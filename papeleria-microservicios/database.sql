-- ============================================================
-- PAPELERÍA MICROSERVICIOS - SCRIPT DE BASES DE DATOS
-- Ejecutar solo si NO usas Docker (con Docker se crean solas)
-- ============================================================

-- ── BD CLIENTES ──────────────────────────────────────────────
CREATE DATABASE IF NOT EXISTS bd_clientes CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_clientes;

CREATE TABLE IF NOT EXISTS clientes (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre          VARCHAR(100) NOT NULL,
    apellido        VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    telefono        VARCHAR(20),
    fecha_registro  DATE,
    activo          TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS direcciones (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id     BIGINT NOT NULL,
    calle          VARCHAR(200) NOT NULL,
    ciudad         VARCHAR(100) NOT NULL,
    estado         VARCHAR(100) NOT NULL,
    codigo_postal  VARCHAR(10),
    es_principal   TINYINT(1) NOT NULL DEFAULT 0,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

INSERT INTO clientes (nombre, apellido, email, telefono, fecha_registro, activo) VALUES
('Ana',    'García',    'ana.garcia@email.com',    '5551001001', CURDATE(), 1),
('Luis',   'Martínez',  'luis.martinez@email.com', '5551001002', CURDATE(), 1),
('María',  'López',     'maria.lopez@email.com',   '5551001003', CURDATE(), 1),
('Carlos', 'Hernández', 'carlos.h@email.com',      '5551001004', CURDATE(), 1);

INSERT INTO direcciones (cliente_id, calle, ciudad, estado, codigo_postal, es_principal) VALUES
(1, 'Av. Insurgentes 123', 'Ciudad de México', 'CDMX',   '06600', 1),
(2, 'Calle Hidalgo 45',    'Guadalajara',      'Jalisco', '44100', 1),
(3, 'Blvd. Díaz Ordaz 89', 'Monterrey',        'NL',      '64000', 1);


-- ── BD PRODUCTOS ─────────────────────────────────────────────
CREATE DATABASE IF NOT EXISTS bd_productos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_productos;

CREATE TABLE IF NOT EXISTS categorias (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre      VARCHAR(80)  NOT NULL UNIQUE,
    descripcion VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS productos (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(150) NOT NULL,
    descripcion   VARCHAR(300),
    precio        DOUBLE       NOT NULL,
    codigo_barras VARCHAR(50)  UNIQUE,
    unidad_medida VARCHAR(30),
    activo        TINYINT(1)   NOT NULL DEFAULT 1,
    categoria_id  BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

INSERT INTO categorias (nombre, descripcion) VALUES
('Escritura',      'Bolígrafos, lápices, marcadores'),
('Papelería',      'Cuadernos, libretas, hojas'),
('Organización',   'Carpetas, archiveros, folders'),
('Manualidades',   'Tijeras, pegamento, foamy'),
('Medición',       'Reglas, escuadras, transportadores'),
('Tecnología',     'USB, cables, cartuchos de tinta');

INSERT INTO productos (nombre, descripcion, precio, codigo_barras, unidad_medida, activo, categoria_id) VALUES
('Lápiz HB',          'Lápiz de grafito HB',             5.50,  'LAP001', 'pieza',   1, 1),
('Bolígrafo Azul',    'Bolígrafo tinta azul punta fina', 8.00,  'BOL001', 'pieza',   1, 1),
('Bolígrafo Rojo',    'Bolígrafo tinta roja punta fina', 8.00,  'BOL002', 'pieza',   1, 1),
('Marcador Permanente','Marcador negro permanente',       12.50, 'MAR001', 'pieza',   1, 1),
('Cuaderno Raya',     'Cuaderno 100 hojas rayado',       25.00, 'CUA001', 'pieza',   1, 2),
('Cuaderno Cuadro',   'Cuaderno 100 hojas cuadriculado', 25.00, 'CUA002', 'pieza',   1, 2),
('Libreta Espiral',   'Libreta A5 espiral 80 hojas',     35.00, 'LIB001', 'pieza',   1, 2),
('Resma Papel',       'Resma 500 hojas carta 75g',       95.00, 'RES001', 'paquete', 1, 2),
('Carpeta Argollas',  'Carpeta 3 argollas tamaño carta', 45.00, 'CAR001', 'pieza',   1, 3),
('Folder Manila',     'Folder manila tamaño carta x25',  30.00, 'FOL001', 'paquete', 1, 3),
('Tijeras Escolares', 'Tijeras punta redonda 6 pulg',    18.50, 'TIJ001', 'pieza',   1, 4),
('Pegamento Barra',   'Pegamento barra 20g',              9.00, 'PEG001', 'pieza',   1, 4),
('Regla 30cm',        'Regla plástico transparente',      7.00, 'REG001', 'pieza',   1, 5),
('Escuadra 45°',      'Escuadra plástico 25cm',          12.00, 'ESC001', 'pieza',   1, 5),
('USB 32GB',          'Memoria USB 32GB 3.0',            85.00, 'USB001', 'pieza',   1, 6);


-- ── BD INVENTARIO ────────────────────────────────────────────
CREATE DATABASE IF NOT EXISTS bd_inventario CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_inventario;

CREATE TABLE IF NOT EXISTS inventario (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    producto_id       BIGINT  NOT NULL UNIQUE,
    stock_actual      INT     NOT NULL DEFAULT 0,
    stock_minimo      INT     NOT NULL DEFAULT 5,
    stock_maximo      INT,
    ubicacion_almacen VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS movimientos_stock (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    inventario_id   BIGINT       NOT NULL,
    tipo            ENUM('ENTRADA','SALIDA','AJUSTE') NOT NULL,
    cantidad        INT          NOT NULL,
    stock_anterior  INT,
    stock_nuevo     INT,
    motivo          VARCHAR(200),
    fecha_movimiento DATETIME,
    referencia_id   BIGINT,
    FOREIGN KEY (inventario_id) REFERENCES inventario(id)
);

INSERT INTO inventario (producto_id, stock_actual, stock_minimo, stock_maximo, ubicacion_almacen) VALUES
(1,  200, 20, 500, 'A-01'), (2,  150, 20, 400, 'A-02'),
(3,  150, 20, 400, 'A-03'), (4,   80, 10, 200, 'A-04'),
(5,   90, 10, 200, 'B-01'), (6,   90, 10, 200, 'B-02'),
(7,   60, 10, 150, 'B-03'), (8,   40,  5, 100, 'B-04'),
(9,   50, 10, 150, 'C-01'), (10,  70, 10, 200, 'C-02'),
(11,  60, 10, 150, 'C-03'), (12, 120, 15, 300, 'C-04'),
(13, 100, 15, 300, 'D-01'), (14,  75, 10, 200, 'D-02'),
(15,  30,  5,  80, 'D-03');


-- ── BD PEDIDOS ───────────────────────────────────────────────
CREATE DATABASE IF NOT EXISTS bd_pedidos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_pedidos;

CREATE TABLE IF NOT EXISTS pedidos (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id    BIGINT  NOT NULL,
    estado        ENUM('PENDIENTE','CONFIRMADO','EN_PROCESO','ENVIADO','ENTREGADO','CANCELADO') NOT NULL,
    fecha_pedido  DATETIME NOT NULL,
    fecha_entrega DATETIME,
    total         DOUBLE  NOT NULL,
    observaciones VARCHAR(300)
);

CREATE TABLE IF NOT EXISTS detalle_pedidos (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id       BIGINT  NOT NULL,
    producto_id     BIGINT  NOT NULL,
    cantidad        INT     NOT NULL,
    precio_unitario DOUBLE  NOT NULL,
    subtotal        DOUBLE  NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);


-- ── BD PROVEEDORES ───────────────────────────────────────────
CREATE DATABASE IF NOT EXISTS bd_proveedores CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_proveedores;

CREATE TABLE IF NOT EXISTS proveedores (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre    VARCHAR(150) NOT NULL,
    rfc       VARCHAR(20)  UNIQUE,
    email     VARCHAR(150) UNIQUE,
    telefono  VARCHAR(20),
    direccion VARCHAR(200),
    activo    TINYINT(1)   NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS compras (
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    proveedor_id    BIGINT  NOT NULL,
    fecha_compra    DATETIME NOT NULL,
    total           DOUBLE  NOT NULL,
    estado          ENUM('PENDIENTE','RECIBIDA','CANCELADA') NOT NULL,
    numero_factura  VARCHAR(50),
    FOREIGN KEY (proveedor_id) REFERENCES proveedores(id)
);

CREATE TABLE IF NOT EXISTS detalle_compras (
    compra_id       BIGINT  NOT NULL,
    producto_id     BIGINT  NOT NULL,
    cantidad        INT     NOT NULL,
    precio_unitario DOUBLE  NOT NULL,
    subtotal        DOUBLE  NOT NULL,
    FOREIGN KEY (compra_id) REFERENCES compras(id)
);

INSERT INTO proveedores (nombre, rfc, email, telefono, direccion, activo) VALUES
('Distribuidora Papelera S.A.',  'DPA010101AAA', 'ventas@distpapelera.com',   '5552001001', 'Av. Industrial 100, CDMX',      1),
('Oficina Total México',          'OTM020202BBB', 'pedidos@oficinaltotal.com', '5552001002', 'Blvd. Comercio 250, Monterrey', 1),
('Suministros Escolares del Norte','SEN030303CCC','contacto@suminescolar.com', '5552001003', 'Calle Norte 78, Guadalajara',   1);


-- ── BD VENTAS ────────────────────────────────────────────────
CREATE DATABASE IF NOT EXISTS bd_ventas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE bd_ventas;

CREATE TABLE IF NOT EXISTS ventas (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id   BIGINT  NOT NULL,
    pedido_id    BIGINT,
    fecha_venta  DATETIME NOT NULL,
    subtotal     DOUBLE  NOT NULL,
    impuestos    DOUBLE  NOT NULL,
    total        DOUBLE  NOT NULL,
    estado       ENUM('PENDIENTE','PAGADA','CANCELADA','REEMBOLSADA') NOT NULL,
    metodo_pago  ENUM('EFECTIVO','TARJETA','TRANSFERENCIA','CREDITO')
);

CREATE TABLE IF NOT EXISTS facturas (
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    venta_id         BIGINT       NOT NULL,
    numero_factura   VARCHAR(50)  NOT NULL UNIQUE,
    fecha_emision    DATETIME     NOT NULL,
    rfc_cliente      VARCHAR(20),
    razon_social     VARCHAR(200),
    total            DOUBLE       NOT NULL,
    xml_sat          TEXT,
    FOREIGN KEY (venta_id) REFERENCES ventas(id)
);
