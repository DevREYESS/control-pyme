package com.tienda.inventario.repository;

import com.tienda.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Buscar productos con stock bajo
    @Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo")
    List<Producto> findProductosConStockBajo();
    
    // Buscar por nombre, código de barras o categoría
    @Query("SELECT p FROM Producto p LEFT JOIN p.categoria c WHERE " +
    	       "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
    	       "LOWER(p.codigoBarras) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
    	       "LOWER(c.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))")
    	List<Producto> buscarProductos(@Param("termino") String termino);
    
    // Buscar por código de barras
    Optional<Producto> findByCodigoBarras(String codigoBarras);
    
    // Buscar por categoría
    List<Producto> findByCategoriaId(Long categoriaId);    
    // Verificar si existe un código de barras (excluyendo un ID específico)
    boolean existsByCodigoBarrasAndIdNot(String codigoBarras, Long id);
    
 // Al final de la interfaz ProductoRepository, antes del último }

    @Query("SELECT SUM(p.precio * p.stockActual) FROM Producto p")
    BigDecimal calcularValorTotalInventario();

    @Query("SELECT COUNT(p) FROM Producto p WHERE p.stockActual <= p.stockMinimo")
    Integer contarProductosStockBajo();
}