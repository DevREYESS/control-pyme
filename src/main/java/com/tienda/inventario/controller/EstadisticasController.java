package com.tienda.inventario.controller;

import com.tienda.inventario.dto.EstadisticasDTO;
import com.tienda.inventario.service.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadisticas")
@CrossOrigin(origins = "*")
public class EstadisticasController {
    
    @Autowired
    private EstadisticasService estadisticasService;
    
    @GetMapping
    public ResponseEntity<EstadisticasDTO> obtenerEstadisticas() {
        EstadisticasDTO stats = estadisticasService.obtenerEstadisticas();
        return ResponseEntity.ok(stats);
    }
}