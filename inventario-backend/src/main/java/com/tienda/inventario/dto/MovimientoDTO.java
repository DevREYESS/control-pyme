package com.tienda.inventario.dto;

import com.tienda.inventario.entity.Movimiento.TipoMovimiento;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class MovimientoDTO {
    
    private Long id;
    
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;
    
    private String productoNombre;
    
    @NotNull(message = "El tipo de movimiento es obligatorio")
    private TipoMovimiento tipo;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer cantidad;
    
    private String motivo;
    private String usuario;
    private LocalDateTime fecha;
    
    // Constructor vacío
    public MovimientoDTO() {
    }
    
    // Constructor con parámetros
    public MovimientoDTO(Long id, Long productoId, String productoNombre, 
                        TipoMovimiento tipo, Integer cantidad, String motivo, 
                        String usuario, LocalDateTime fecha) {
        this.id = id;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.usuario = usuario;
        this.fecha = fecha;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProductoId() {
        return productoId;
    }
    
    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }
    
    public String getProductoNombre() {
        return productoNombre;
    }
    
    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }
    
    public TipoMovimiento getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }
    
    public Integer getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public String getMotivo() {
        return motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}