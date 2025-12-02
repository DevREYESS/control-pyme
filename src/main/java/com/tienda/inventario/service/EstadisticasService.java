package com.tienda.inventario.service;

import com.tienda.inventario.dto.*;
import com.tienda.inventario.entity.Movimiento;
import com.tienda.inventario.repository.MovimientoRepository;
import com.tienda.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import com.tienda.inventario.entity.Producto;
import com.tienda.inventario.repository.ProductoRepository;

@Service
public class EstadisticasService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private MovimientoRepository movimientoRepository;
    
    public EstadisticasDTO obtenerEstadisticas() {
        EstadisticasDTO stats = new EstadisticasDTO();
        
        // Estadísticas básicas
        stats.setTotalProductos(productoRepository.count());
        stats.setValorTotalInventario(productoRepository.calcularValorTotalInventario() != null 
            ? productoRepository.calcularValorTotalInventario() 
            : BigDecimal.ZERO);
        stats.setProductosStockBajo(productoRepository.contarProductosStockBajo());
        stats.setTotalMovimientos(movimientoRepository.count());
        stats.setEntradasMes(movimientoRepository.contarEntradasMesActual());
        stats.setSalidasMes(movimientoRepository.contarSalidasMesActual());
        
        // Top 5 productos más vendidos
        stats.setProductosTopVendidos(
            movimientoRepository.findTopProductosVendidos(PageRequest.of(0, 5))
        );
        
        // Productos por categoría
        stats.setProductosPorCategoria(obtenerProductosPorCategoria());        
        // Movimientos por mes (últimos 6 meses)
        stats.setMovimientosPorMes(obtenerMovimientosPorMes());
        
        return stats;
    }
    
    private List<MovimientoMensualDTO> obtenerMovimientosPorMes() {
        LocalDateTime hace6Meses = LocalDateTime.now().minusMonths(6);
        List<Movimiento> movimientos = movimientoRepository.filtrarMovimientos(null, hace6Meses, null);
        
        Map<String, MovimientoMensualDTO> movimientosPorMes = new LinkedHashMap<>();
        
        // Inicializar últimos 6 meses
        for (int i = 5; i >= 0; i--) {
            LocalDateTime fecha = LocalDateTime.now().minusMonths(i);
            String mes = fecha.getMonth().getDisplayName(TextStyle.SHORT, new Locale("es", "MX")) 
                       + " " + fecha.getYear();
            movimientosPorMes.put(mes, new MovimientoMensualDTO(mes, 0L, 0L));
        }
        
        // Contar movimientos
        for (Movimiento m : movimientos) {
            String mes = m.getFecha().getMonth().getDisplayName(TextStyle.SHORT, new Locale("es", "MX")) 
                       + " " + m.getFecha().getYear();
            
            MovimientoMensualDTO dto = movimientosPorMes.get(mes);
            if (dto != null) {
                if (m.getTipo() == Movimiento.TipoMovimiento.ENTRADA) {
                    dto.setEntradas(dto.getEntradas() + m.getCantidad());
                } else {
                    dto.setSalidas(dto.getSalidas() + m.getCantidad());
                }
            }
        }
        
        return new ArrayList<>(movimientosPorMes.values());
    }
    @Autowired

    private List<CategoriaStatsDTO> obtenerProductosPorCategoria() {
        List<Producto> productos = productoRepository.findAll();
        Map<String, Long> categoriaCount = new HashMap<>();
        
        for (Producto p : productos) {
            String categoria = (p.getCategoria() != null) ? p.getCategoria().getNombre() : "Sin categoría";
            categoriaCount.put(categoria, categoriaCount.getOrDefault(categoria, 0L) + 1);
        }
        
        List<CategoriaStatsDTO> resultado = new ArrayList<>();
        for (Map.Entry<String, Long> entry : categoriaCount.entrySet()) {
            resultado.add(new CategoriaStatsDTO(entry.getKey(), entry.getValue()));
        }
        
        return resultado;
    }
}