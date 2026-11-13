package com.example.demoCrud.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LibroRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título no puede pasar de 150 caracteres")
    private String titulo;

    @Min(value = 0, message = "El año no puede ser negativo")
    @Max(value = 2100, message = "El año no puede ser mayor a 2100")
    private Integer anioPublicacion;

    @NotNull(message = "El autorId es obligatorio")
    private Long autorId;

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(Integer anioPublicacion) { this.anioPublicacion = anioPublicacion; }

    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }
}
