package com.example.book_advisor.services;

import java.util.Optional;

import com.example.book_advisor.model.Usuario;

public interface UsuarioService {
    
    Usuario registrarUsuario(String nombre);
    
    Optional<Usuario> buscarPorNombre(String nombre);
    
    boolean existeUsuario(String nombre);
}
