package com.example.demoCrud.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AutorRequest {

    @NotBlank(message = "El nombre del autor es obligatorio")
    @Size(max = 100, message = "El nombre no puede pasar de 100 caracteres")
    private String nombre;

    @Size(max = 60, message = "La nacionalidad no puede pasar de 60 caracteres")
    private String nacionalidad;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
}
