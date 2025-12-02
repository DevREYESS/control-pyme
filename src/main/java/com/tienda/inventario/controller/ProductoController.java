package com.tienda.inventario.controller;

import com.tienda.inventario.dto.ApiResponse;
import com.tienda.inventario.dto.ProductoDTO;
import com.tienda.inventario.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerTodos() {
        List<ProductoDTO> productos = productoService.obtenerTodos();
        return ResponseEntity.ok(productos);
    }
    
    // Obtener un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        try {
            ProductoDTO producto = productoService.obtenerPorId(id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    // Buscar productos
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscar(@RequestParam String q) {
        List<ProductoDTO> productos = productoService.buscarProductos(q);
        return ResponseEntity.ok(productos);
    }
    
    // Obtener productos con stock bajo
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<ProductoDTO>> stockBajo() {
        List<ProductoDTO> productos = productoService.obtenerStockBajo();
        return ResponseEntity.ok(productos);
    }
    
    // Crear producto
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO creado = productoService.crear(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Producto creado correctamente", creado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, 
                                       @Valid @RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO actualizado = productoService.actualizar(id, productoDTO);
            return ResponseEntity.ok(new ApiResponse(true, "Producto actualizado correctamente", actualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
    
    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            productoService.eliminar(id);
            return ResponseEntity.ok(new ApiResponse(true, "Producto eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}