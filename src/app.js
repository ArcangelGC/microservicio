const express = require('express');
const app = express();

app.use(express.json());

app.get('/hola', (req, res) => {
    res.send("Hola desde mi microservicio de papelería  en Node.js!");
});

app.listen(3000, () => {
    console.log("Servidor corriendo en http://localhost:3000");
});

let productos = [];

app.get('/productos', (req, res) => {
    res.json(productos);
});

app.post('/productos', (req, res) => {
    const producto = req.body;
    productos.push(producto);
    res.send("Producto agregado");
});

app.delete('/productos/:id', (req, res) => {
const id = Number.parseInt(req.params.id);
    productos = productos.filter(p => p.id !== id);
    res.send("Producto eliminado");
});

app.put('/productos/:id', (req, res) => {
const id = Number.parseInt(req.params.id);
    const { nombre, precio, stock } = req.body;

    let producto = productos.find(p => p.id === id);

    if (producto) {
        producto.nombre = nombre;
        producto.precio = precio;
        producto.stock = stock;
        res.send("Producto actualizado");
    } else {
        res.send("Producto no encontrado");
    }
});