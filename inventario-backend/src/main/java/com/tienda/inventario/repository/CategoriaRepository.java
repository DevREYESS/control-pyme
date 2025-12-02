package com.tienda.inventario.repository;

import com.tienda.inventario.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    // Buscar por nombre
    Optional<Categoria> findByNombre(String nombre);
    
    // Verificar si existe por nombre (excluyendo un ID específico)
    boolean existsByNombreAndIdNot(String nombre, Long id);
    
    // Verificar si existe por nombre
    boolean existsByNombre(String nombre);
    
    // Contar productos por categoría
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.categoria.id = :categoriaId")
    Long contarProductosPorCategoria(Long categoriaId);
}