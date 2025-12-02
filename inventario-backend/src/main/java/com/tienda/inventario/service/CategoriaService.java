package com.tienda.inventario.service;

import com.tienda.inventario.dto.CategoriaDTO;
import com.tienda.inventario.entity.Categoria;
import com.tienda.inventario.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    // Obtener todas las categorías
    public List<CategoriaDTO> obtenerTodas() {
        return categoriaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // Obtener una categoría por ID
    public CategoriaDTO obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        return convertirADTO(categoria);
    }
    
    // Crear categoría
    public CategoriaDTO crear(CategoriaDTO categoriaDTO) {
        // Validar nombre único
        if (categoriaRepository.existsByNombre(categoriaDTO.getNombre())) {
            throw new RuntimeException("Ya existe una categoría con ese nombre");
        }
        
        Categoria categoria = convertirAEntidad(categoriaDTO);
        Categoria guardada = categoriaRepository.save(categoria);
        return convertirADTO(guardada);
    }
    
    // Actualizar categoría
    public CategoriaDTO actualizar(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + id));
        
        // Validar nombre único (excluyendo la categoría actual)
        if (categoriaRepository.existsByNombreAndIdNot(categoriaDTO.getNombre(), id)) {
            throw new RuntimeException("Ya existe otra categoría con ese nombre");
        }
        
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setIcono(categoriaDTO.getIcono());
        categoria.setColor(categoriaDTO.getColor());
        
        Categoria actualizada = categoriaRepository.save(categoria);
        return convertirADTO(actualizada);
    }
    
    // Eliminar categoría
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoría no encontrada con ID: " + id);
        }
        
        // Verificar si tiene productos asociados
        Long totalProductos = categoriaRepository.contarProductosPorCategoria(id);
        if (totalProductos > 0) {
            throw new RuntimeException("No se puede eliminar la categoría porque tiene " + totalProductos + " producto(s) asociado(s)");
        }
        
        categoriaRepository.deleteById(id);
    }
    
    // Convertir entidad a DTO
    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setIcono(categoria.getIcono());
        dto.setColor(categoria.getColor());
        dto.setFechaCreacion(categoria.getFechaCreacion());
        dto.setTotalProductos(categoria.getProductos().size());
        return dto;
    }
    
    // Convertir DTO a entidad
    private Categoria convertirAEntidad(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setIcono(dto.getIcono());
        categoria.setColor(dto.getColor());
        return categoria;
    }
}