package com.tienda.inventario.dto;

import java.math.BigDecimal;
import java.util.List;

public class EstadisticasDTO {
    
    private Long totalProductos;
    private BigDecimal valorTotalInventario;
    private Integer productosStockBajo;
    private Long totalMovimientos;
    private Long entradasMes;
    private Long salidasMes;
    private List<ProductoTopDTO> productosTopVendidos;
    private List<CategoriaStatsDTO> productosPorCategoria;
    private List<MovimientoMensualDTO> movimientosPorMes;
    
    // Constructor vacío
    public EstadisticasDTO() {
    }
    
    // Getters y Setters
    public Long getTotalProductos() {
        return totalProductos;
    }
    
    public void setTotalProductos(Long totalProductos) {
        this.totalProductos = totalProductos;
    }
    
    public BigDecimal getValorTotalInventario() {
        return valorTotalInventario;
    }
    
    public void setValorTotalInventario(BigDecimal valorTotalInventario) {
        this.valorTotalInventario = valorTotalInventario;
    }
    
    public Integer getProductosStockBajo() {
        return productosStockBajo;
    }
    
    public void setProductosStockBajo(Integer productosStockBajo) {
        this.productosStockBajo = productosStockBajo;
    }
    
    public Long getTotalMovimientos() {
        return totalMovimientos;
    }
    
    public void setTotalMovimientos(Long totalMovimientos) {
        this.totalMovimientos = totalMovimientos;
    }
    
    public Long getEntradasMes() {
        return entradasMes;
    }
    
    public void setEntradasMes(Long entradasMes) {
        this.entradasMes = entradasMes;
    }
    
    public Long getSalidasMes() {
        return salidasMes;
    }
    
    public void setSalidasMes(Long salidasMes) {
        this.salidasMes = salidasMes;
    }
    
    public List<ProductoTopDTO> getProductosTopVendidos() {
        return productosTopVendidos;
    }
    
    public void setProductosTopVendidos(List<ProductoTopDTO> productosTopVendidos) {
        this.productosTopVendidos = productosTopVendidos;
    }
    
    public List<CategoriaStatsDTO> getProductosPorCategoria() {
        return productosPorCategoria;
    }
    
    public void setProductosPorCategoria(List<CategoriaStatsDTO> productosPorCategoria) {
        this.productosPorCategoria = productosPorCategoria;
    }
    
    public List<MovimientoMensualDTO> getMovimientosPorMes() {
        return movimientosPorMes;
    }
    
    public void setMovimientosPorMes(List<MovimientoMensualDTO> movimientosPorMes) {
        this.movimientosPorMes = movimientosPorMes;
    }
}