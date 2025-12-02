package com.tienda.inventario.dto;

public class MovimientoMensualDTO {
    private String mes;
    private Long entradas;
    private Long salidas;
    
    public MovimientoMensualDTO() {
    }
    
    public MovimientoMensualDTO(String mes, Long entradas, Long salidas) {
        this.mes = mes;
        this.entradas = entradas;
        this.salidas = salidas;
    }
    
    public String getMes() {
        return mes;
    }
    
    public void setMes(String mes) {
        this.mes = mes;
    }
    
    public Long getEntradas() {
        return entradas;
    }
    
    public void setEntradas(Long entradas) {
        this.entradas = entradas;
    }
    
    public Long getSalidas() {
        return salidas;
    }
    
    public void setSalidas(Long salidas) {
        this.salidas = salidas;
    }
}