package com.example.demoCrud.dto;

import com.example.demoCrud.models.Usuario;

// No incluye el password
public record UserResponse(Long id, String username, String email) {

    public static UserResponse from(Usuario u) {
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail());
    }
}
