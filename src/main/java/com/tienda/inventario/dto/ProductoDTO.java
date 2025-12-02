package com.tienda.inventario.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductoDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;
    
    @Min(value = 0, message = "El stock actual no puede ser negativo")
    private Integer stockActual;
    
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo;
    
    private Long categoriaId;
    private String categoriaNombre;
    private String codigoBarras;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private boolean stockBajo;
    
    // Constructor vacío
    public ProductoDTO() {
    }
    
    // Constructor con parámetros
    public ProductoDTO(Long id, String nombre, String descripcion, BigDecimal precio, 
                       Integer stockActual, Integer stockMinimo, Long categoriaId,
                       String categoriaNombre, String codigoBarras, LocalDateTime fechaCreacion, 
                       LocalDateTime fechaActualizacion, boolean stockBajo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.categoriaId = categoriaId;
        this.categoriaNombre = categoriaNombre;
        this.codigoBarras = codigoBarras;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.stockBajo = stockBajo;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public BigDecimal getPrecio() {
        return precio;
    }
    
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    
    public Integer getStockActual() {
        return stockActual;
    }
    
    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }
    
    public Integer getStockMinimo() {
        return stockMinimo;
    }
    
    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
    
    public Long getCategoriaId() {
        return categoriaId;
    }
    
    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
    
    public String getCategoriaNombre() {
        return categoriaNombre;
    }
    
    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }
    
    public String getCodigoBarras() {
        return codigoBarras;
    }
    
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    public boolean isStockBajo() {
        return stockBajo;
    }
    
    public void setStockBajo(boolean stockBajo) {
        this.stockBajo = stockBajo;
    }
}