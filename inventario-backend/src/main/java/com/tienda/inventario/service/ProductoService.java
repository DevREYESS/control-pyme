package com.tienda.inventario.service;

import com.tienda.inventario.dto.ProductoDTO;
import com.tienda.inventario.repository.CategoriaRepository;
import com.tienda.inventario.entity.Categoria;
import com.tienda.inventario.entity.Producto;
import com.tienda.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    // Obtener todos los productos
    public List<ProductoDTO> obtenerTodos() {
        return productoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // Obtener un producto por ID
    public ProductoDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return convertirADTO(producto);
    }
    
    // Buscar productos
    public List<ProductoDTO> buscarProductos(String termino) {
        return productoRepository.buscarProductos(termino).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // Obtener productos con stock bajo
    public List<ProductoDTO> obtenerStockBajo() {
        return productoRepository.findProductosConStockBajo().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // Crear producto
    public ProductoDTO crear(ProductoDTO productoDTO) {
        // Validar código de barras único
        if (productoDTO.getCodigoBarras() != null && 
            !productoDTO.getCodigoBarras().isEmpty()) {
            productoRepository.findByCodigoBarras(productoDTO.getCodigoBarras())
                    .ifPresent(p -> {
                        throw new RuntimeException("Ya existe un producto con ese código de barras");
                    });
        }
        
        Producto producto = convertirAEntidad(productoDTO);
        
        // Establecer categoría si se proporcionó
        if (productoDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        }
        
        Producto guardado = productoRepository.save(producto);
        return convertirADTO(guardado);
    }
    
    // Actualizar producto
    public ProductoDTO actualizar(Long id, ProductoDTO productoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        // Validar código de barras único (excluyendo el producto actual)
        if (productoDTO.getCodigoBarras() != null && 
            !productoDTO.getCodigoBarras().isEmpty() &&
            productoRepository.existsByCodigoBarrasAndIdNot(productoDTO.getCodigoBarras(), id)) {
            throw new RuntimeException("Ya existe otro producto con ese código de barras");
        }
        
        producto.setNombre(productoDTO.getNombre());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setStockMinimo(productoDTO.getStockMinimo());
        producto.setCodigoBarras(productoDTO.getCodigoBarras());
        
        // Actualizar categoría si se proporcionó
        if (productoDTO.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(productoDTO.getCategoriaId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            producto.setCategoria(categoria);
        } else {
            producto.setCategoria(null);
        }
        
        Producto actualizado = productoRepository.save(producto);
        return convertirADTO(actualizado);
    }
    
    // Eliminar producto
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productoRepository.deleteById(id);
    }
    
    // Actualizar stock (uso interno)
    public void actualizarStock(Long id, Integer cantidad, boolean esEntrada) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        
        if (esEntrada) {
            producto.setStockActual(producto.getStockActual() + cantidad);
        } else {
            if (producto.getStockActual() < cantidad) {
                throw new RuntimeException("Stock insuficiente. Disponible: " + producto.getStockActual());
            }
            producto.setStockActual(producto.getStockActual() - cantidad);
        }
        
        productoRepository.save(producto);
    }
    
    // Convertir entidad a DTO
    private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setStockActual(producto.getStockActual());
        dto.setStockMinimo(producto.getStockMinimo());
        if (producto.getCategoria() != null) {
            dto.setCategoriaId(producto.getCategoria().getId());
            dto.setCategoriaNombre(producto.getCategoria().getNombre());
        }
        dto.setCodigoBarras(producto.getCodigoBarras());
        dto.setFechaCreacion(producto.getFechaCreacion());
        dto.setFechaActualizacion(producto.getFechaActualizacion());
        dto.setStockBajo(producto.isStockBajo());
        return dto;
    }
    
    // Convertir DTO a entidad
    private Producto convertirAEntidad(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStockActual(dto.getStockActual() != null ? dto.getStockActual() : 0);
        producto.setStockMinimo(dto.getStockMinimo() != null ? dto.getStockMinimo() : 5);
        // La categoría se establece después en crear/actualizar
        producto.setCodigoBarras(dto.getCodigoBarras());
        return producto;
    }
}