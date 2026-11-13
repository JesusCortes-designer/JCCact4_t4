package com.example.demoCrud.controllers;

import com.example.demoCrud.dto.AutorRequest;
import com.example.demoCrud.dto.AutorResponse;
import com.example.demoCrud.dto.PageResponse;
import com.example.demoCrud.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<AutorResponse>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(autorService.listar(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(autorService.obtener(id));
    }

    @PostMapping
    public ResponseEntity<AutorResponse> crear(@Valid @RequestBody AutorRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(autorService.crear(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponse> actualizar(@PathVariable Long id,
                                                    @Valid @RequestBody AutorRequest req) {
        return ResponseEntity.ok(autorService.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        autorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
