package com.tienda.inventario.repository;

import com.tienda.inventario.dto.ProductoTopDTO;
import com.tienda.inventario.entity.Movimiento;
import com.tienda.inventario.entity.Movimiento.TipoMovimiento;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    // Obtener movimientos por producto
    List<Movimiento> findByProductoIdOrderByFechaDesc(Long productoId);

    // Obtener movimientos por tipo
    List<Movimiento> findByTipoOrderByFechaDesc(TipoMovimiento tipo);

    // Filtrar movimientos por tipo y rango de fechas - USANDO PARÁMETROS POSICIONALES
    @Query(value = "SELECT * FROM movimientos m WHERE " +
            "(?1::VARCHAR IS NULL OR m.tipo = ?1::VARCHAR) AND " +
            "(?2::TIMESTAMP IS NULL OR m.fecha >= ?2::TIMESTAMP) AND " +
            "(?3::TIMESTAMP IS NULL OR m.fecha <= ?3::TIMESTAMP) " +
            "ORDER BY m.fecha DESC",
            nativeQuery = true)
    List<Movimiento> filtrarMovimientos(
            String tipo,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin
    );

    // Obtener últimos N movimientos
    List<Movimiento> findTop10ByOrderByFechaDesc();

    // Contar entradas del mes actual
    @Query("SELECT COUNT(m) FROM Movimiento m WHERE m.tipo = 'ENTRADA' AND MONTH(m.fecha) = MONTH(CURRENT_DATE) AND YEAR(m.fecha) = YEAR(CURRENT_DATE)")
    Long contarEntradasMesActual();

    // Contar salidas del mes actual
    @Query("SELECT COUNT(m) FROM Movimiento m WHERE m.tipo = 'SALIDA' AND MONTH(m.fecha) = MONTH(CURRENT_DATE) AND YEAR(m.fecha) = YEAR(CURRENT_DATE)")
    Long contarSalidasMesActual();

    // Top productos vendidos
    @Query("SELECT new com.tienda.inventario.dto.ProductoTopDTO(p.nombre, SUM(m.cantidad)) " +
            "FROM Movimiento m JOIN m.producto p " +
            "WHERE m.tipo = 'SALIDA' " +
            "GROUP BY p.id, p.nombre " +
            "ORDER BY SUM(m.cantidad) DESC")
    List<ProductoTopDTO> findTopProductosVendidos(Pageable pageable);
}