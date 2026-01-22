package com.example.book_advisor.services;

import java.util.List;
import java.util.Optional;

import com.example.book_advisor.model.Rol;
import com.example.book_advisor.model.Usuario;

public interface UsuarioService {
    
    Usuario registrarUsuario(String nombre, String password, Rol rol);
    
    Optional<Usuario> buscarPorNombre(String nombre);

    Optional<Usuario> getUsuarioConectado();
    
    boolean existeUsuario(String nombre);
    
    List<Usuario> obtenerTodos();
    
    Optional<Usuario> obtenerPorId(Long id);
    
    Usuario actualizar(Long id, String nombre, String password, Rol rol);
    
    void eliminar(Long id);
}
