package com.papeleria.productos.service;
import com.papeleria.productos.dto.*;
import com.papeleria.productos.exception.ResourceNotFoundException;
import com.papeleria.productos.model.*;
import com.papeleria.productos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service @RequiredArgsConstructor
public class ProductoService {
    private final ProductoRepository productoRepo;
    private final CategoriaRepository categoriaRepo;

    public List<Producto> listar() { return productoRepo.findByActivoTrue(); }
    public Producto buscarPorId(Long id) {
        return productoRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Producto no encontrado: "+id));
    }
    public List<Producto> buscarPorNombre(String n) { return productoRepo.findByNombreContainingIgnoreCase(n); }
    public List<Producto> porCategoria(Long catId) { return productoRepo.findByCategoriaId(catId); }

    public Producto crear(ProductoDTO dto) {
        Categoria cat = categoriaRepo.findById(dto.getCategoriaId())
            .orElseThrow(()->new ResourceNotFoundException("Categoria no encontrada: "+dto.getCategoriaId()));
        return productoRepo.save(Producto.builder()
            .nombre(dto.getNombre()).descripcion(dto.getDescripcion())
            .precio(dto.getPrecio()).codigoBarras(dto.getCodigoBarras())
            .unidadMedida(dto.getUnidadMedida()).activo(true).categoria(cat).build());
    }
    public Producto actualizar(Long id, ProductoDTO dto) {
        Producto p = buscarPorId(id);
        Categoria cat = categoriaRepo.findById(dto.getCategoriaId())
            .orElseThrow(()->new ResourceNotFoundException("Categoria no encontrada: "+dto.getCategoriaId()));
        p.setNombre(dto.getNombre()); p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio()); p.setCodigoBarras(dto.getCodigoBarras());
        p.setUnidadMedida(dto.getUnidadMedida()); p.setCategoria(cat);
        return productoRepo.save(p);
    }
    public void eliminar(Long id) { Producto p=buscarPorId(id); p.setActivo(false); productoRepo.save(p); }

    public List<Categoria> listarCategorias() { return categoriaRepo.findAll(); }
    public Categoria crearCategoria(CategoriaDTO dto) {
        return categoriaRepo.save(Categoria.builder().nombre(dto.getNombre()).descripcion(dto.getDescripcion()).build());
    }
}
