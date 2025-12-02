package com.tienda.inventario.dto;

public class ProductoTopDTO {
    private String nombre;
    private Long totalVendido;
    
    public ProductoTopDTO() {
    }
    
    public ProductoTopDTO(String nombre, Long totalVendido) {
        this.nombre = nombre;
        this.totalVendido = totalVendido;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Long getTotalVendido() {
        return totalVendido;
    }
    
    public void setTotalVendido(Long totalVendido) {
        this.totalVendido = totalVendido;
    }
}