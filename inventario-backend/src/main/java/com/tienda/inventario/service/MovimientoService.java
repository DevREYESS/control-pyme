package com.tienda.inventario.service;

import com.tienda.inventario.dto.MovimientoDTO;
import com.tienda.inventario.entity.Movimiento;
import com.tienda.inventario.entity.Movimiento.TipoMovimiento;
import com.tienda.inventario.entity.Producto;
import com.tienda.inventario.repository.MovimientoRepository;
import com.tienda.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovimientoService {
    
    @Autowired
    private MovimientoRepository movimientoRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private ProductoService productoService;
    
    // Obtener todos los movimientos
    public List<MovimientoDTO> obtenerTodos() {
        return movimientoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // Obtener movimientos por producto
    public List<MovimientoDTO> obtenerPorProducto(Long productoId) {
        return movimientoRepository.findByProductoIdOrderByFechaDesc(productoId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // Filtrar movimientos
    public List<MovimientoDTO> filtrarMovimientos(String tipo, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        TipoMovimiento tipoMovimiento = null;
        if (tipo != null && !tipo.isEmpty()) {
            tipoMovimiento = TipoMovimiento.valueOf(tipo.toUpperCase());
        }
        
        return movimientoRepository.filtrarMovimientos(tipoMovimiento, fechaInicio, fechaFin).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // Crear movimiento (registra y actualiza stock)
    public MovimientoDTO crear(MovimientoDTO movimientoDTO) {
        // Verificar que el producto existe
        Producto producto = productoRepository.findById(movimientoDTO.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + movimientoDTO.getProductoId()));
        
        // Actualizar el stock del producto
        boolean esEntrada = movimientoDTO.getTipo() == TipoMovimiento.ENTRADA;
        productoService.actualizarStock(producto.getId(), movimientoDTO.getCantidad(), esEntrada);
        
        // Crear el movimiento
        Movimiento movimiento = new Movimiento();
        movimiento.setProducto(producto);
        movimiento.setTipo(movimientoDTO.getTipo());
        movimiento.setCantidad(movimientoDTO.getCantidad());
        movimiento.setMotivo(movimientoDTO.getMotivo());
        movimiento.setUsuario(movimientoDTO.getUsuario());
        
        Movimiento guardado = movimientoRepository.save(movimiento);
        return convertirADTO(guardado);
    }
    
    // Obtener últimos movimientos
    public List<MovimientoDTO> obtenerUltimos() {
        return movimientoRepository.findTop10ByOrderByFechaDesc().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    // Convertir entidad a DTO
    private MovimientoDTO convertirADTO(Movimiento movimiento) {
        MovimientoDTO dto = new MovimientoDTO();
        dto.setId(movimiento.getId());
        dto.setProductoId(movimiento.getProducto().getId());
        dto.setProductoNombre(movimiento.getProducto().getNombre());
        dto.setTipo(movimiento.getTipo());
        dto.setCantidad(movimiento.getCantidad());
        dto.setMotivo(movimiento.getMotivo());
        dto.setUsuario(movimiento.getUsuario());
        dto.setFecha(movimiento.getFecha());
        return dto;
    }
}