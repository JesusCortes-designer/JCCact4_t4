package com.example.demoCrud.controllers;

import com.example.demoCrud.dto.LibroRequest;
import com.example.demoCrud.dto.LibroResponse;
import com.example.demoCrud.dto.PageResponse;
import com.example.demoCrud.service.LibroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<LibroResponse>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(libroService.listar(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.obtener(id));
    }

    @PostMapping
    public ResponseEntity<LibroResponse> crear(@Valid @RequestBody LibroRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(libroService.crear(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponse> actualizar(@PathVariable Long id,
                                                    @Valid @RequestBody LibroRequest req) {
        return ResponseEntity.ok(libroService.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
