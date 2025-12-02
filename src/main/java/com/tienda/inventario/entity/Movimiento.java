package com.tienda.inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
public class Movimiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
    
    @NotNull(message = "El tipo de movimiento es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMovimiento tipo;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    private Integer cantidad;
    
    @Size(max = 200)
    @Column(length = 200)
    private String motivo;
    
    @Size(max = 50)
    @Column(length = 50)
    private String usuario;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    // Enum para tipo de movimiento
    public enum TipoMovimiento {
        ENTRADA,
        SALIDA
    }
    
    // Constructor vacío
    public Movimiento() {
    }
    
    // Constructor con parámetros
    public Movimiento(Long id, Producto producto, TipoMovimiento tipo, 
                      Integer cantidad, String motivo, String usuario, 
                      LocalDateTime fecha) {
        this.id = id;
        this.producto = producto;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.motivo = motivo;
        this.usuario = usuario;
        this.fecha = fecha;
    }
    
    @PrePersist
    protected void onCreate() {
        fecha = LocalDateTime.now();
        if (usuario == null || usuario.isEmpty()) {
            usuario = "Sistema";
        }
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Producto getProducto() {
        return producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
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