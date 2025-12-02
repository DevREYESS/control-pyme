package com.tienda.inventario.controller;

import com.tienda.inventario.dto.ApiResponse;
import com.tienda.inventario.dto.MovimientoDTO;
import com.tienda.inventario.service.MovimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@CrossOrigin(origins = "*")
public class MovimientoController {
    
    @Autowired
    private MovimientoService movimientoService;
    
    // Obtener todos los movimientos
    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> obtenerTodos(
            @RequestParam(required = false) Long productoId,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        List<MovimientoDTO> movimientos;
        
        if (productoId != null) {
            movimientos = movimientoService.obtenerPorProducto(productoId);
        } else if (tipo != null || fechaInicio != null || fechaFin != null) {
            movimientos = movimientoService.filtrarMovimientos(tipo, fechaInicio, fechaFin);
        } else {
            movimientos = movimientoService.obtenerTodos();
        }
        
        return ResponseEntity.ok(movimientos);
    }
    
    // Obtener últimos movimientos
    @GetMapping("/ultimos")
    public ResponseEntity<List<MovimientoDTO>> obtenerUltimos() {
        List<MovimientoDTO> movimientos = movimientoService.obtenerUltimos();
        return ResponseEntity.ok(movimientos);
    }
    
    // Crear movimiento (entrada o salida)
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody MovimientoDTO movimientoDTO) {
        try {
            MovimientoDTO creado = movimientoService.crear(movimientoDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse(true, "Movimiento registrado correctamente", creado));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage()));
        }
    }
}
